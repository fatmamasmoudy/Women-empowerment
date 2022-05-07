package tn.esprit.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Admin;
import tn.esprit.spring.repository.AdminRepository;



@Service
public class AdminDetailsServiceImpl implements UserDetailsService {
	@Autowired
	AdminRepository adminRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin user = adminRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		return AdminDetailsImpl.build(user);
	}
}
