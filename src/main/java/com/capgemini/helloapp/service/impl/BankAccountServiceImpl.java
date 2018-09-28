package com.capgemini.helloapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.helloapp.repository.BankAccountRepository;
import com.capgemini.helloapp.service.BankAccountService;

@Service
public class BankAccountServiceImpl implements BankAccountService {
	@Autowired
	private BankAccountRepository bankaccountrepositoryimpl;

	
	@Override
	public double getBalance(long accountId) {
		return bankaccountrepositoryimpl.getBalance(accountId);
	}

	@Override
	public double withdraw(long accountId, double amount) {
		double accountBalance = bankaccountrepositoryimpl.getBalance(accountId);
		bankaccountrepositoryimpl.updateBalance(accountId, accountBalance - amount);
		return accountBalance - amount;
	}

	@Override
	public double deposit(long accountId, double amount) {
		double accountBalance = bankaccountrepositoryimpl.getBalance(accountId);
		bankaccountrepositoryimpl.updateBalance(accountId, accountBalance + amount);
		return accountBalance + amount;
	}

	@Override
	public boolean fundTransfer(long fromAcc, long toAcc, double balance) {
		getBalance(toAcc);
		withdraw(fromAcc, balance);
		deposit(toAcc, balance);
		return true;
		
	}
	
}
		