package com.nagarro.usermanagement.service;

import java.util.List;

import com.nagarro.usermanagement.dto.AppUserDto;

public interface UserService {

	
	AppUserDto getUserDto(String userName);

	List<AppUserDto> getUserByUserType(String userType);
	
	String authenticateUser(AppUserDto appUserDto);
}
