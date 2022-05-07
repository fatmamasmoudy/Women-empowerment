package tn.esprit.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Partner;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.PartnerRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class PartnerDetailsServiceImpl implements UserDetailsService {
	@Autowired
	PartnerRepository partnerRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Partner user = partnerRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		return PartnerDetailsImpl.build(user);
	}
}
