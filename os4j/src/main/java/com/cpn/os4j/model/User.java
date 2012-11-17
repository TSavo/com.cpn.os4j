package com.cpn.os4j.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	String id;
	String username;
	String name;
	List<Role> roles;
	@JsonProperty("roles_links")
	List<String> roleLinks;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<String> getRoleLinks() {
		return roleLinks;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public String getUsername() {
		return username;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setRoleLinks(final List<String> roleLinks) {
		this.roleLinks = roleLinks;
	}

	public void setRoles(final List<Role> roles) {
		this.roles = roles;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

}
