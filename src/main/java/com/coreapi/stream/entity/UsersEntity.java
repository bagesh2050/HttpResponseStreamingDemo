package com.coreapi.stream.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "users")
public class UsersEntity {
	@Id
	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "MSISDN")
	private String msisdn;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMsisdn() {
		System.out.println("getting msisdn=" + msisdn);
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
		System.out.println("Setting Msisdn =" + msisdn);
	}

	@Override
	public String toString() {
		return "UsersEntity [userId=" + userId + ", email=" + email + ", msisdn=" + msisdn + "]";
	}
}
