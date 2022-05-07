package tn.esprit.spring.repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.JobOffer;
import tn.esprit.spring.entities.JobPage;
import tn.esprit.spring.entities.JobSearchCriteria;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class JobCriteriaRepository {
	private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
  
   
   
    public JobCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<JobOffer> findAllWithFilters(JobPage JobPage,
                                             JobSearchCriteria JobSearchCriteria){
        CriteriaQuery<JobOffer> criteriaQuery = criteriaBuilder.createQuery(JobOffer.class);
        Root<JobOffer> JobRoot = criteriaQuery.from(JobOffer.class);
        Predicate predicate = getPredicate(JobSearchCriteria, JobRoot);
        criteriaQuery.where(predicate);
        setOrder(JobPage, criteriaQuery, JobRoot);

        TypedQuery<JobOffer> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(JobPage.getPageNumber() * JobPage.getPageSize());
        typedQuery.setMaxResults(JobPage.getPageSize());

        Pageable pageable = getPageable(JobPage);

        long JobsCount = getJobsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, JobsCount);
    }

    private Predicate getPredicate(JobSearchCriteria JobSearchCriteria,
                                   Root<JobOffer> JobRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(JobSearchCriteria.getPoste())){
            predicates.add(
                    criteriaBuilder.like(JobRoot.get("poste"),
                            "%" + JobSearchCriteria.getPoste() + "%")
            ); 
        }
        if(Objects.nonNull(JobSearchCriteria.getDescription())){
            predicates.add(
                    criteriaBuilder.like(JobRoot.get("description"),
                            "%" + JobSearchCriteria.getDescription()+ "%")
            );
        }
        if(Objects.nonNull(JobSearchCriteria.getPlace())){
            predicates.add(
                    criteriaBuilder.like(JobRoot.get("place"),
                            "%" + JobSearchCriteria.getPlace() + "%")
            );
        }
        if(Objects.nonNull(JobSearchCriteria.getSalary())){
            predicates.add(
                    criteriaBuilder.like(JobRoot.get("salary"),
                            "%" + JobSearchCriteria.getSalary() + "%")
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(JobPage JobPage,
                          CriteriaQuery<JobOffer> criteriaQuery,
                          Root<JobOffer> JobRoot) {
        if(JobPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(JobRoot.get(JobPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(JobRoot.get(JobPage.getSortBy())));
        }
    }

    
    
  

    private Pageable getPageable(JobPage JobPage) {
        Sort sort = Sort.by(JobPage.getSortDirection(), JobPage.getSortBy());
        return PageRequest.of(JobPage.getPageNumber(),JobPage.getPageSize(), sort);
    }

    private long getJobsCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<JobOffer> countRoot = countQuery.from(JobOffer.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
