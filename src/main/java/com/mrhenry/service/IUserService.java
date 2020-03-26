package com.mrhenry.service;

import java.util.List;

import com.mrhenry.dto.UserDTO;

public interface IUserService {
	List<UserDTO> findAllStaffs();
}
