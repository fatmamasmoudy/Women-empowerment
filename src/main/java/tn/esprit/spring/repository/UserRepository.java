package tn.esprit.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.User;
import tn.esprit.spring.entities.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByEmail(String email);
	Boolean existsByEmail(String email);
	public Optional<User> findByResetToken(String token);
	User findById(long id);
}
