package tn.esprit.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Admin;


@Repository
public interface AdminRepository  extends JpaRepository<Admin, Long> {
	public Optional<Admin> findByEmail(String email);
	Boolean existsByEmail(String email);
}
