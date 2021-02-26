package com.nagarro.usermanagement.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.nagarro.usermanagement.component.JwtComponent;
import com.nagarro.usermanagement.constants.CommonConstants;
import com.nagarro.usermanagement.constants.ExceptionMessageConstants;
import com.nagarro.usermanagement.dto.AppUserDto;
import com.nagarro.usermanagement.enums.ServiceType;
import com.nagarro.usermanagement.enums.UserTypeEnum;
import com.nagarro.usermanagement.exception.UserManagementException;
import com.nagarro.usermanagement.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	private static final List<AppUserDto> allUsers = new ArrayList<>();

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	JwtComponent jwtComponent;

	static {
		addUsers();
	}

	private static void addUsers() {
		LOG.debug("Inside addUsers method");

		allUsers.add(new AppUserDto("gagan", "Gagan", "gdgagan696@gmail.com", "Hello", UserTypeEnum.CONSUMER.name(),
				null, CommonConstants.PASCHIM_VIHAR_PINCODE));

		allUsers.add(new AppUserDto("shubham", "Shubham", "gagan.dhand@nagarro.com", "Hello",
				UserTypeEnum.PRODUCER.name(), ServiceType.ELECTRICIAN.name(), CommonConstants.DWARKA_PINCODE));

		allUsers.add(new AppUserDto("hardik", "Hardik Dhingra", "dhingrahardik1997@gmail.com", "Hello", UserTypeEnum.PRODUCER.name(),
				ServiceType.YOGA_TRAINER.name(), CommonConstants.GURGAON_PINCODE));

		allUsers.add(new AppUserDto("gaurav", "Gaurav", "gaurav@gmail.com", "Hello", UserTypeEnum.ADMIN.name(), null,
				CommonConstants.PASCHIM_VIHAR_PINCODE));

		LOG.debug("Total users after adding {}", allUsers.size());
	}

	@Override
	public AppUserDto getUserDto(String userName) {
		LOG.debug("Inside getUserDto method,userName {}", userName);

		return allUsers.stream().filter(user -> userName.equals(user.getUserName())).findFirst()
				.orElseThrow(() -> new UserManagementException(ExceptionMessageConstants.USER_NOT_FOUND));

	}

	@Override
	public List<AppUserDto> getUserByUserType(String userType) {
		LOG.debug("Inside getUserByUserType method,userType {}", userType);

		List<AppUserDto> filteredUsers = allUsers.stream()
				.filter(user -> userType.equalsIgnoreCase(user.getServiceType())).collect(Collectors.toList());
		if (filteredUsers.isEmpty()) {
			throw new UserManagementException(ExceptionMessageConstants.NO_USERS_FOUND);
		}
		return filteredUsers;

	}

	@Override
	public String authenticateUser(AppUserDto appUserDto) {
		Authentication authentication = null;
		String jwtToken = null;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(appUserDto.getUserName(), appUserDto.getPassword()));
		} catch (BadCredentialsException e) {
			throw new UserManagementException("Bad Credentials,Invalid Password.");
		}
		if (Objects.nonNull(authentication)) {
			
			final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			jwtToken = jwtComponent.generateToken(userDetails);
		}
		return jwtToken;

	}

}
