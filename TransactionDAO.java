package com.org.bank.dao;

public interface TransactionDAO {
	void deposit(int accountId , double amount) throws Exception ;
	void withdraw(int accountId , double amount) throws Exception ;
	void transfer(int fromAccountId, int toAccountId, double amount) throws Exception ;
	void displayTransactionHistory(int accountId);
	

}