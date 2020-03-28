package com.sifast.web.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sifast.model.Role;
import com.sifast.model.User;

public class MyUserPrincipal implements UserDetails {

	private static final long serialVersionUID = 1L;

	private User user;

	public MyUserPrincipal(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles = this.user.getRoles();
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			role.getAuthorities().stream().map(p -> new SimpleGrantedAuthority(p.getDesignation()))
					.forEach(authorities::add);
		}

		return authorities;
	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.user.getEnabled();
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.user.getEnabled();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.user.getEnabled();
	}

	@Override
	public boolean isEnabled() {
		return this.user.getEnabled();
	}
}
