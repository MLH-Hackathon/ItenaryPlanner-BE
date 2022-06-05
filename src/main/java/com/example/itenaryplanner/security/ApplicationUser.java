package com.example.itenaryplanner.security;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.itenaryplanner.model.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApplicationUser implements UserDetails {

	private static final long serialVersionUID = 8106495968995311212L;

	private User user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Set.of();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
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

	public User getUser() {
		return user;
	}

	public User getUser(User user) {
		return new User(user.getId(), getUsername(), null, user.getFullName(), user.getProfilePicture());
	}
}
