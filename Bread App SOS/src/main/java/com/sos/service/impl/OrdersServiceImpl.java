package com.sos.service.impl;

import com.sos.models.Orders;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sos.repository.OrderRepository;
import com.sos.service.OrdersService;

@Service
public class OrdersServiceImpl implements OrdersService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public Orders save(Orders entity) {
		return orderRepository.save(entity);
	}

	@Override
	public Orders update(Orders entity) {
		return orderRepository.save(entity);
	}

	@Override
	public void delete(Orders entity) {
		orderRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		orderRepository.delete(id);
	}

	@Override
	public Orders find(Long id) {
		return orderRepository.findOne(id);
	}

	@Override
	public List<Orders> findAll() {
		return orderRepository.findAll();
	}

	@Override
	public boolean authenticate(String username, String password){
		Orders user = this.findByEmail(username);
		if(user == null){
			return false;
		}else{
			if(password.equals(user.getPassword())) return true;
			else return false;
		}
	}

	@Override
	public Orders findByEmail(String email) {
		return orderRepository.findByEmail(email);
	}

	@Override
	public void deleteInBatch(List<Orders> users) {
		orderRepository.deleteInBatch(users);
	}
	
}
