package com.org.bank.dao;

import com.org.bank.model.Account;

public interface AccountDAO {
	void createAccount(Account account) throws Exception ;
	Account viewAccount(int accountId) throws Exception ;
	void updateAccountInfo(int accountId, String newAddress , String newContact) throws Exception ;
	void getBalance(int accountId) throws Exception ;
	

}