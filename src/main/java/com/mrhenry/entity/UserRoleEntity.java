package com.mrhenry.entity;

import com.mrhenry.annotation.Column;
import com.mrhenry.annotation.Entity;

@Entity(name = "userrole")
public class UserRoleEntity {
	
	@Column(name = "id")
	private Long id;
	
	@Column(name = "userid")
	private Long userId;
	
	@Column(name = "roleid")
	private Long roleId;
}
