package com.lf.chat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String status;

    @Column
    private String sessionId;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId( String sessionId ) {
        this.sessionId = sessionId;
    }

}
