package com.example.admincontact.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Contacts")

public class Admin {

	@Id
	@NotBlank(message = "Admin id should me mentioned")
	@Size(max = 6, message = "Id should not be more than 6")
	private String id;

	@NotBlank(message = "Name should me mentioned")
	private String username;

	@NotEmpty(message = "Password should be entered properly")
	@Size(min = 6, max = 20, message = "Password length should be between 6 to 20 characters")
	private String password;

	@NotBlank(message = "PhoneNumber should me mentioned")
	@Size(min = 10, max = 10, message = "Phone No. Should be of 10 digits")
	private String phoneNumber;

	public Admin() {

	}

	public Admin(String id, String username, String phoneNumber) {
		super();
		this.id = id;
		this.username = username;
		this.phoneNumber = phoneNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Admin [id=" + id + ", username=" + username + ", password=" + password + ", phoneNumber=" + phoneNumber
				+ "]";
	}

}
