package com.example.demo.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Account;
import com.example.demo.model.Event;

public class EventDAO extends DAO{
	public List<Event> getMyEvents(int id_account){
		List<Event> events = new ArrayList<>();
		String sql = "SELECT * FROM event JOIN account ON event.id_account = account.id WHERE id_account=?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id_account);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Event event = new Event();
				event.setId(rs.getInt("id"));
				event.setName(rs.getString("name"));
				event.setStart_date(rs.getString("start_date"));
				event.setEnd_date(rs.getString("end_date"));
				event.setStart_time(rs.getString("start_time"));
				event.setEnd_time(rs.getString("end_time"));
				event.setContent(rs.getString("content"));
				
				if (LocalDate.now().toString().compareTo(event.getEnd_date()) > 0) {
					event.setStatus("finished");
				}
				else if (LocalDate.now().toString().compareTo(event.getStart_date()) < 0) {
					event.setStatus("upcoming");
				}
				else if (LocalDate.now().toString().compareTo(event.getEnd_date()) <= 0 && LocalDate.now().toString().compareTo(event.getStart_date()) >= 0) {
					event.setStatus("ongoing");
				}
				
				Account account = new Account();
				account.setId(rs.getInt("id_account"));
				account.setFullname(rs.getString("fullname"));
				account.setDob(rs.getString("dob"));
				account.setEmail(rs.getString("email"));
				account.setPhone(rs.getString("phone"));
				account.setDes(rs.getString("des"));
				
				event.setAccount(account);
				events.add(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return events;
	}
	
	public Event getEventById(int idEvent) {
		Event event = new Event();
		String sql = "SELECT * FROM event WHERE id = ?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, idEvent);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				event.setId(idEvent);
				event.setName(rs.getString("name"));
				event.setStart_date(rs.getString("start_date"));
				event.setEnd_date(rs.getString("end_date"));
				event.setStart_time(rs.getString("start_time"));
				event.setEnd_time(rs.getString("end_time"));
				event.setContent(rs.getString("content"));

				if (LocalDate.now().toString().compareTo(event.getEnd_date()) > 0) {
					event.setStatus("finished");
				}
				else if (LocalDate.now().toString().compareTo(event.getStart_date()) < 0) {
					event.setStatus("upcoming");
				}
				else if (LocalDate.now().toString().compareTo(event.getEnd_date()) <= 0 && LocalDate.now().toString().compareTo(event.getStart_date()) >= 0) {
					event.setStatus("ongoing");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return event;
	}
	
	public void addEvent(Event event) {
		String sql = "INSERT INTO event(name, start_date, end_date, start_time, end_time, id_account) VALUES(?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, event.getName());
			ps.setString(2, event.getStart_date());
			ps.setString(3, event.getEnd_date());
			ps.setString(4, event.getStart_time());
			ps.setString(5, event.getEnd_time());
			ps.setInt(6, event.getAccount().getId());
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				event.setId(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return event;
	}
	
	public void updateMyEvent(Event event) {
		String sql = "UPDATE event SET name=?, start_date=?, end_date=?, start_time=?, end_time=?, content=? WHERE id=?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, event.getName());
			ps.setString(2, event.getStart_date());
			ps.setString(3, event.getEnd_date());
			ps.setString(4, event.getStart_time());
			ps.setString(5, event.getEnd_time());
			ps.setString(6, event.getContent());
			ps.setInt(7, event.getId());
			
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteMyEvent(int id_event) {
		String sql = "DELETE FROM event WHERE id=?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id_event);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkDuplicateEvent(Event event){
		List<Event> events = new ArrayList<>();
		String sql = "SELECT * FROM event WHERE (? BETWEEN start_date AND end_date) OR (? BETWEEN start_date AND end_date)"
				+ "OR (? < start_date AND ? > end_date)";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, event.getStart_date());
			ps.setString(2, event.getEnd_date());
			ps.setString(3, event.getStart_date());
			ps.setString(4, event.getEnd_date());
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				if(event.getId() != 0) {
					if(event.getId() != rs.getInt("id")) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
