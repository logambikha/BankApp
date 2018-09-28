package com.capgemini.helloapp.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.helloapp.entities.Customer;
import com.capgemini.helloapp.service.impl.CustomerServiceImpl;

@Controller
public class HelloController {
	@Autowired
	private CustomerServiceImpl customerService;
	@RequestMapping("/")
	public String getHelloPage() {
		return "login";
	}
		@RequestMapping(value = "/login")
		public String getPage(@RequestParam Long custId,@RequestParam String password,HttpSession session ) throws SQLException {
			Customer customer = new Customer(custId, null, password, null, null, null, null);
			customer = customerService.authenticate(customer);
			if (customer.getEmail()!=null) {
				session.setAttribute("customer", customer);
				return "accountDetails";
			}	
				return "login";
		}
		
		@RequestMapping(value = "/accountDetails")
		public String getAcc() {
			return "accountDetails";
		}
		
		@RequestMapping(value = "/editCustomer")
		public String getEdit() {
			return "editCustomer";
		}
		 @RequestMapping(value = "/editprofile")
		 public String getProfile(HttpSession session,@RequestParam String custAddress,@RequestParam String custEmail,@RequestParam String custName) {
			 Customer customer = (Customer)session.getAttribute("customer");
			 customer.setAddress(custAddress);
			 customer.setEmail(custEmail);
			 customer.setCustomerName(custName);
			 
			 customerService.updateProfile(customer);
			 
			 session.setAttribute("customer", customer);
			 return "editCustomer";
		 }
		 @RequestMapping(value = "/changePassword")
		 public String getPsw() {
			 return "changePassword";
		 }
		
		@RequestMapping(value = "/editPassword")
		public String getChange(HttpServletRequest request,HttpSession session,@RequestParam String oldPassword,@RequestParam String newPassword) {
			 Customer customer = (Customer)session.getAttribute("customer");
			 customer.setPassword(newPassword);
			 customerService.updatePassword(customer, oldPassword, newPassword);
			 session.setAttribute("customer", customer);
			 request.setAttribute("success", true);
			 return "success";
		}
		
		@RequestMapping(value = "/logout")
		public String getOut(HttpSession session) {
			session.invalidate();
			return"login";
			
		}
		
	}

