package com.sifast.dto.Role;

import java.util.Set;

import com.sifast.dto.authority.AuthorityDto;

public class ViewRoleDto {

	private int id;

	private String designation;

	private String description;

	private Set<AuthorityDto> authorities;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<AuthorityDto> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<AuthorityDto> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ViewRoleDto [designation=");
		builder.append(designation);
		builder.append(", description=");
		builder.append(description);
		builder.append(", authorities=");
		builder.append(authorities);
		builder.append("]");
		return builder.toString();
	}

}
