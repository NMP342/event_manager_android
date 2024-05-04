package com.example.demo.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionManager {
	
	@Autowired
    private HttpSession session;

    // Constructor
    public SessionManager(HttpSession session) {
        this.session = session;
    }

    // Getter và setter cho HttpSession (nếu cần)

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }
}
