package com.sifast.dto.authority;

import java.util.List;

public class ViewAuthorityDto {

	private AuthorityDto parent;

	private List<AuthorityDto> children;

	public AuthorityDto getParent() {
		return parent;
	}

	public void setParent(AuthorityDto parent) {
		this.parent = parent;
	}

	public List<AuthorityDto> getChildren() {
		return children;
	}

	public void setChildren(List<AuthorityDto> children) {
		this.children = children;
	}

}
