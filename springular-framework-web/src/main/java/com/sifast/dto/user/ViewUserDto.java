package com.sifast.dto.user;

public class ViewUserDto extends UserDto {

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ViewUserDto [id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}

}
