package com.capgemini.helloapp.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.capgemini.helloapp.entities.BankAccount;

import com.capgemini.helloapp.entities.Customer;
import com.capgemini.helloapp.repository.CustomerRepository;
@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
	@Autowired
	private JdbcTemplate jdbctemplate;

	@Override
	public Customer authenticate(Customer customer) throws SQLException {
		customer = jdbctemplate.queryForObject("SELECT * FROM customers WHERE customerId=? AND password=?",
				new Object[] { customer.getCustomerId(), customer.getPassword() }, new CustomerRowMapper());
		if(customer.getEmail()!=null) {
			
					BankAccount baccount = jdbctemplate.queryForObject(
				"SELECT * FROM  bankaccount WHERE customerId =?",
				new Object[] { customer.getCustomerId() }, new AccountRowMapper());
		customer.setAccount(baccount);
		System.out.println(customer);
		return customer;
		}
		return null;
	}

	@Override
	public Customer updateProfile(Customer customer) {
		jdbctemplate.update(
				"UPDATE customers SET address = ?,dateOfBirth = ?,email=?,customerName=?   WHERE customerId = ?",
				new Object[] { customer.getAddress(), customer.getDateOfBirth(),
						customer.getEmail(), customer.getCustomerName(), customer.getCustomerId() });
		customer=jdbctemplate.queryForObject("SELECT * FROM customers WHERE customerId=?",new Object[] {customer.getCustomerId()},new CustomerRowMapper());
		return customer;
	}

	@Override
	public boolean updatePassword(Customer customer, String oldPassword, String newPassword) {
		int count=jdbctemplate.update("UPDATE customers SET password=? WHERE customerId=? AND password=?",new Object[] { newPassword, customer.getCustomerId(), oldPassword});
		return (count!=0)?true:false;
		
	}
	
	private class CustomerRowMapper implements RowMapper<Customer>{

		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Customer customer = new Customer();
			customer.setCustomerId(rs.getLong(1));
			customer.setCustomerName(rs.getString(2));
			customer.setPassword(rs.getString(3));
			customer.setEmail(rs.getString(4));
			customer.setAddress(rs.getString(5));
			customer.setDateOfBirth(rs.getDate(6).toLocalDate());
			
			return customer;
		}
		
	}
	
	private class AccountRowMapper implements RowMapper<BankAccount>{

		@Override
		public BankAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
			BankAccount baccount=new BankAccount();
			baccount.setAccountId(rs.getLong(2));
			baccount.setAccountType(rs.getString(3));
			baccount.setBalance(rs.getDouble(4));
			return baccount;	
		}
		
	}
	
	
}
	