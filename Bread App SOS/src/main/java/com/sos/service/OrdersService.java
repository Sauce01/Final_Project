package com.sos.service;

import com.sos.models.Orders;
import com.sos.generic.GenericService;

public interface OrdersService extends GenericService<Orders> {

	boolean authenticate(String email, String password);
	
	Orders findByEmail(String email);
	
}
