package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo.model.Guest;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class GuestService {
	
	@Autowired
	private Repository repo;
	
	@Autowired
	private JavaMailSender sender;
	
	public void saveGuest(Guest guest) {
		repo.save(guest);
	}
	
	public List<Guest> getAllGuestByIdEvent(int idEvent){
		List<Guest> guests = repo.findByIdEvent(idEvent);
		return guests;
	}
	
	public Guest getGuestByEmailAndEvent(String email, int idEvent) {
		Guest guest = repo.findByEmailAndIdEvent(email, idEvent);
		return guest;
	}
	
	public void changeStatus(int id) {
		Optional<Guest> guestOp = repo.findById(id);
		Guest guest = guestOp.get();
		guest.setConfirm(1);
		
		saveGuest(guest);
	}
	
	public void sendMail(String toMail, String subject, String body) {
		MimeMessage message = sender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom("tm425625@gmail.com");
			helper.setTo(toMail);
			helper.setSubject(subject);
			helper.setText(body, true);
			
			sender.send(message);
			System.out.println("Mail send successfully.....");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
