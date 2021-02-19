package com.sos.service;

import com.sos.models.User;
import com.sos.generic.GenericService;

public interface UserService extends GenericService<User> {

	boolean authenticate(String email, String password);
	
	User findByEmail(String email);
	
}
