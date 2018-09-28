package com.capgemini.helloapp.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.helloapp.entities.Customer;
import com.capgemini.helloapp.repository.impl.CustomerRepositoryImpl;
import com.capgemini.helloapp.service.CustomerService;
@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepositoryImpl customerRepository;
	
	@Override
	public Customer authenticate(Customer customer) throws SQLException {
		// TODO Auto-generated method stub
		return customerRepository.authenticate(customer);
	}

	@Override
	public Customer updateProfile(Customer customer) {
		// TODO Auto-generated method stub
		return customerRepository.updateProfile(customer);
	}

	@Override
	public boolean updatePassword(Customer customer, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		return customerRepository.updatePassword(customer, oldPassword, newPassword);
	}

}
