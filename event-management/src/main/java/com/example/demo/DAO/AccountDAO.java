package com.example.demo.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.example.demo.model.Account;

public class AccountDAO extends DAO{
	public void addAccount(Account account) {
		String sql = "INSERT INTO account(username, password, role, fullname, email) VALUES(?,?,?,?,?)";
		try{
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.setString(3, account.getRole());
            ps.setString(4, account.getFullname());
            ps.setString(5, account.getEmail());
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
            	account.setId(rs.getInt(1));
            }
        }catch(Exception e){
            e.printStackTrace();
        }   
	}
	
	public boolean checkAccountExist(Account account) {
		String sql = "SELECT * FROM account WHERE username = ?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, account.getUsername());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Account checkLogin(Account account){
		String sql = "SELECT * FROM account WHERE username=? AND password=?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ResultSet rs = ps.executeQuery();
 
            if(rs.next()){
            	account.setId(rs.getInt("id"));
            	account.setRole(rs.getString("role"));
            	account.setUsername(rs.getString("username"));
            	account.setPassword(rs.getString("password"));
            	account.setFullname(rs.getString("fullname"));
            	account.setDob(rs.getString("dob"));
            	account.setEmail(rs.getString("email"));
            	account.setPhone(rs.getString("phone"));
            	account.setDes(rs.getString("des"));
            }
            else {
            	account = null;
            }
        }catch(Exception e){
            e.printStackTrace();
        }   
        return account;
	}
	
	public Account getAccountByUsername(Account account) {
		String sql = "SELECT * FROM account WHERE username=?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ResultSet rs = ps.executeQuery();
 
            if(rs.next()){
            	account.setId(rs.getInt("id"));
            	account.setRole(rs.getString("role"));
            	account.setUsername(rs.getString("username"));
            	account.setPassword(rs.getString("password"));
            	account.setFullname(rs.getString("fullname"));
            	account.setDob(rs.getString("dob"));
            	account.setEmail(rs.getString("email"));
            	account.setPhone(rs.getString("phone"));
            	account.setDes(rs.getString("des"));
            }
            else {
            	account = null;
            }
        }catch(Exception e){
            e.printStackTrace();
        }   
        return account;
	}
	
	public void updateAccount(Account account) {
		String sql = "UPDATE account SET password=?, fullname=?, dob=?, email=?, phone=?, des=? WHERE id=?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, account.getPassword());
			ps.setString(2, account.getFullname());
			ps.setString(3, account.getDob());
			ps.setString(4, account.getEmail());
			ps.setString(5, account.getPhone());
			ps.setString(6, account.getDes());
			ps.setInt(7, account.getId());
			
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
