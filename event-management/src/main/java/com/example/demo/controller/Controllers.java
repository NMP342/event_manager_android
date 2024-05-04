package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DAO.AccountDAO;
import com.example.demo.DAO.EventDAO;
import com.example.demo.DAO.FAQDAO;
import com.example.demo.DAO.MessageDAO;
import com.example.demo.model.Account;
import com.example.demo.model.Event;
import com.example.demo.model.FAQ;
import com.example.demo.model.Mail;
import com.example.demo.model.Message;
import com.example.demo.model.SMS;
import com.example.demo.service.SendMail;
import com.example.demo.service.SendSMS;
import com.example.demo.session.SessionManager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin
public class Controllers {
	@Autowired
	private SendMail sender;
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private SendSMS senderSMS;
	
	private EventDAO ed = new EventDAO();
	private AccountDAO ad = new AccountDAO();
	private MessageDAO md = new MessageDAO();
	private FAQDAO fd = new FAQDAO();
	
	@GetMapping("/event/my-events")
	public List<Event> getMyEvents(){
		List<Event> events = new ArrayList<>();
		events = ed.getMyEvents((int)sessionManager.getSession().getAttribute("idAccount")); // dang fix cung, can sua lai sau
		return events;
	}
	
	@PostMapping("/event/event-information")
	public Event eventInformation(@RequestBody int idEvent) {
		Event event = ed.getEventById(idEvent);
		return event;
	}
	
	@PostMapping("/event/add-event")
	public boolean addEvent(@RequestBody Event event) {
		if (ed.checkDuplicateEvent(event)) {
			return false;
		}
		ed.addEvent(event);
		return true;
	}
	
	@PostMapping("/event/update-event")
	public boolean updateEvent(@RequestBody Event event) {
		if (ed.checkDuplicateEvent(event)) {
			return false;
		}
		ed.updateMyEvent(event);
		return true;
	}
	
	@PostMapping("/account/check-account-exist")
	public boolean checkAccountExist(@RequestBody Account account) {
		return ad.checkAccountExist(account);
	}
	
	@PostMapping("/account/check-login")
	public Account checkLogin(@RequestBody Account account, HttpServletRequest request) {
		account = ad.checkLogin(account);
		if (account != null) {
			sessionManager.setSession(request.getSession());
			sessionManager.getSession().setAttribute("idAccount", account.getId());
			sessionManager.getSession().setAttribute("fullname", account.getFullname());
			sessionManager.getSession().setAttribute("email", account.getEmail());
			sessionManager.getSession().setAttribute("phone", account.getPhone());
			System.out.println(account.toString());
			return account;
		}
		return new Account();
	}
	
	@PostMapping("/mail/register-send-mail")
	public Mail registerSendMail(@RequestBody Account account) {
		Mail mail = new Mail();
        mail.setRecipient(account.getEmail());
        mail.setSubject("Event Management System verification code");
        Random random = new Random();
        mail.setOtp(Integer.toString(random.nextInt(900000) + 100000));
        mail.setBody(mail.getOtp() + " is your verification code, valid for 1 minute. Welcome!");
        mail.setSend_time(System.currentTimeMillis());
        
		sender.sendMail(mail.getRecipient(), mail.getSubject(), mail.getBody());
		return mail;
	}
	
	@PostMapping("/account/add-account")
	public void addAccount(@RequestBody Account account) {
		System.out.println(account.getUsername() + "/" + account.getPassword());
		ad.addAccount(account);
	}
	
	@PostMapping("/mail/forget-send-mail")
	public Mail forgetSendMail(@RequestBody Account account) {
		account = ad.getAccountByUsername(account);
		if (account == null) {
			System.out.println("null");
			return new Mail();
		}
		
		Mail mail = new Mail();
        mail.setRecipient(account.getEmail());
        mail.setSubject("Event Management System verification code");
        Random random = new Random();
        mail.setOtp(Integer.toString(random.nextInt(900000) + 100000));
        mail.setBody("We have received a request of sending password via email from your account. Please confirm this is your request. " + mail.getOtp() + " is your verification code, valid for 1 minute. Welcome!");
        mail.setSend_time(System.currentTimeMillis());
        
		sender.sendMail(mail.getRecipient(), mail.getSubject(), mail.getBody());
		return mail;
	}
	
	@PostMapping("/mail/password-send-mail")
	public void passwordSendMail(@RequestBody Account account) {
		account = ad.getAccountByUsername(account);
		
		String password = "";
        
        Random random = new Random();
        for(int i = 0; i < 20; ++i) {
        	int rand = random.nextInt(75) + 48;
        	while ((rand > 58 && rand < 64) || (rand > 90 && rand < 97)) {
        		rand = random.nextInt(75) + 48;
			}
        	password += (char) rand;
        }
        
        account.setPassword(password);
        
        System.out.println(password + "/" + account.getPassword());
        
        Mail mail = new Mail();
        mail.setRecipient(account.getEmail());
        mail.setSubject("Event Management System new password");
        mail.setBody("We have received a request of sending password via email from your account. This is your new password: " + password + ". Please login and change the password as soon as possible. Welcome!");
        
		sender.sendMail(mail.getRecipient(), mail.getSubject(), mail.getBody());
		
		ad.updateAccount(account);
	}
	
	@PostMapping("/account/logout")
	public void logout() {
		sessionManager.getSession().removeAttribute("idAccount");
		sessionManager.getSession().removeAttribute("fullname");
		sessionManager.getSession().removeAttribute("email");
		sessionManager.getSession().removeAttribute("phone");
	}
	
	@PostMapping("/contact-admin-send-mail")
	public void contactAdminSendMail(@RequestBody String problems) {
		String email = (String) sessionManager.getSession().getAttribute("email");
		String fullname = (String) sessionManager.getSession().getAttribute("fullname");
		
		problems = problems.substring(1, problems.length() - 1);
		
		List<String> problemsToken = new ArrayList<>();
		for(int i = 0; i < problems.length(); ++i) {
			if(problems.charAt(i) == 92 && problems.charAt(i + 1) == 'n') {
				String tmp = problems.substring(0, i);
				problemsToken.add(tmp);
				problems = problems.substring(i + 2, problems.length());
				i = 0;
			}
		}
		problemsToken.add(problems);
		problems = "";
		
		for(String token : problemsToken) {
			problems = problems + "\n" + token;
		}
		
		Mail mail = new Mail();
        mail.setRecipient("tm425625@gmail.com");
        mail.setSubject("User " + fullname + " has problems.");
        mail.setBody("User's email: " + email + '\n'+ problems);
        
		sender.sendMail(mail.getRecipient(), mail.getSubject(), mail.getBody());
	}
	
	@PostMapping("/faq/get-faqs")
	public List<FAQ> getFAQs(){
		return fd.getFAQs();
	}
	@PostMapping("/message/my-messages")
	public List<Message> getMyMessages() {
		return md.getMyMessages((int) sessionManager.getSession().getAttribute("idAccount"));
	}
}
