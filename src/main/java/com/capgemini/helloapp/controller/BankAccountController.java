package com.capgemini.helloapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.helloapp.entities.Customer;
import com.capgemini.helloapp.service.BankAccountService;

@Controller
public class BankAccountController {
	@Autowired
	private BankAccountService bankAccountService;
	
	@RequestMapping(value="/fundTransfer")
		public String loginPage() {
			
		
		return "fundTransfer";
		}
	@RequestMapping(value = "/fundtransfer")
	public String getFund(HttpServletRequest request,HttpSession session,@RequestParam long toAccount,@RequestParam double amount) {
		Customer customer = (Customer)session.getAttribute("customer");
		long fromAcc = customer.getAccount().getAccountId();
		bankAccountService.fundTransfer(fromAcc, toAccount, amount);	
		request.setAttribute("success", true);
		return "success";
		
	}
}

	
	
	


