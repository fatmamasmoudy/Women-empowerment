package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Comment;
import tn.esprit.spring.entities.Subject;
@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {


    @Query("SELECT c FROM Comment c order by  c.nbLike DESC")

   List<Comment>   commentsPlusPer();

}
/*
    @Query( "SELECT s FROM Subject s WHERE s.Description LIKE %:des% and s.Name LIKE %:nom%")
    List<Subject> exists(@Param("des") String des, @Param("nom") String name);*/
