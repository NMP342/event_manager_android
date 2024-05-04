package com.example.demo.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.FAQ;

public class FAQDAO extends DAO {
	public List<FAQ> getFAQs(){
		List<FAQ> FAQs = new ArrayList<>();
		String sql = "SELECT * FROM faq";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				FAQ faq = new FAQ();
				
				faq.setId(rs.getInt("id"));
				faq.setQuestion(rs.getString("question"));
				
				FAQs.add(faq);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FAQs;
	}
	
}
