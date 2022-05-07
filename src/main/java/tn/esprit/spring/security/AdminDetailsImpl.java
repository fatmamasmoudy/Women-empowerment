package tn.esprit.spring.security;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tn.esprit.spring.entities.Admin;


public class AdminDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;


	private String email;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public AdminDetailsImpl(Long id,  String email, String password
			) {
		this.id = id;

		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static AdminDetailsImpl build(Admin user) {
		
		return new AdminDetailsImpl(user.getId(),  user.getEmail(), user.getPassword());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AdminDetailsImpl user = (AdminDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
	
	public AdminDetailsImpl(){
		
	}

	@Override
	public String getUsername() {
		return email;
	}
}
