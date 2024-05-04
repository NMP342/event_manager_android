package com.example.demo.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Message;

public class MessageDAO extends DAO {
	public List<Message> getMyMessages(int idAccount){
		List<Message> messages = new ArrayList<>();
		String sql = "SELECT * FROM message JOIN message_recipient ON message.id=message_recipient.id_message "
				+ "WHERE id_sender=? OR id_recipient=? ORDER BY message.id DESC LIMIT 30";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, idAccount);
			ps.setInt(2, idAccount);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Message message = new Message();
				message.setId(rs.getInt("id"));
				message.setContent(rs.getString("content"));
				message.setSend_time(rs.getString("send_time"));
				message.setType(rs.getInt("type"));
				message.setId_sender(rs.getInt("id_sender"));
				if (message.getId_sender() == idAccount) {
					message.setSend_or_receive(1);
				}
				else {
					message.setSend_or_receive(0);
				}
				
				messages.add(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messages;
	}
}
