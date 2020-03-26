package com.mrhenry.repository;

import java.util.List;

import com.mrhenry.entity.UserEntity;

public interface IUserRepositoty extends GenericJDBC<UserEntity>{
	List<UserEntity> findAllStaffs();
}
