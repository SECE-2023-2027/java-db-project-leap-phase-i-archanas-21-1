package com.org.bank.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.org.bank.model.Account;
import com.org.bank.utils.DatabaseConnection;

public class AccountDaoImpl implements AccountDAO{

	@Override
	public void createAccount(Account account) throws Exception {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO Account(account_holder,account_type,balance,address,contact_number) VALUES (?,?,?,?,?)";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)){
			stmt.setString(1,account.getAccountholder());
			stmt.setString(2,account.getAccountType());
			stmt.setDouble(3,account.getBalance());
			stmt.setString(4,account.getAddress());
			stmt.setString(5,account.getContactNumber());
			int affectedRows = stmt.executeUpdate();
			if(affectedRows > 0)
			{
				ResultSet generatedKeys = stmt.getGeneratedKeys();
				if(generatedKeys.next())
				{
					int accountId = generatedKeys.getInt(1);
					if(account.getAccountType().equals("Savings"))
					{
						String sqlsavingAccount = "INSERT INTO SavingsAccount (account_id,interest_rate) VALUES (?,?) ";
						PreparedStatement stmtSaving = conn.prepareStatement(sqlsavingAccount);
						stmtSaving.setInt(1, accountId);
						stmtSaving.setDouble(2, 0.4);
						stmtSaving.executeUpdate();
					}
					else if(account.getAccountType().equals("Current"))
					{
						String sqlCurrentAccount = "Insert INTO CurrentAccount (account_id,overdraft_limit) VALUES (?,?)";
						PreparedStatement stmtCurrent = conn.prepareStatement(sqlCurrentAccount);
						stmtCurrent.setInt(1, accountId);
						stmtCurrent.setDouble(2,5000.00);
						stmtCurrent.executeUpdate();
					}
				}
			}
			System.out.println("Account Created Successfully");
		}
		
	}

	@Override
	public Account viewAccount(int accountId) throws Exception {
		String sql = "Select * from Account where account_id = ?";
		Account account = null;
		
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setInt(1, accountId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
			{
				account = new Account(
						rs.getInt("account_id"),
						rs.getString("account_holder"),
						rs.getString("account_type"),
						rs.getDouble("balance"),
						rs.getString("address"),
						rs.getString("contact_number")
						);
			}
			if(account!=null)
			{
				System.out.println("Account details retrieved successfully");
			}
			else
			{
				System.out.println("Account not found");
			}
		}
		return account;
	}

	@Override
	public void updateAccountInfo(int accountId, String newAddress, String newContact) throws Exception {
		// TODO Auto-generated method stub
		String sql = "update Account set address = ? , contact_number = ? where account_id = ? ";
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setString(1, newAddress);
			stmt.setString(2, newContact);
			stmt.setInt(3, accountId);
			int rowsAffected = stmt.executeUpdate();
			if(rowsAffected>0)
			{
				System.out.println("Account information updated successfully.");
			}
			else
			{
				System.out.println("Account not found or update failed.");
			}
		}
		
		
	}

	@Override
	public void getBalance(int accountId) throws Exception {
		// TODO Auto-generated method stub
		
	}

}