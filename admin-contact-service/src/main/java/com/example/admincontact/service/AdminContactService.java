package com.example.admincontact.service;

import java.util.List;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.admincontact.model.Admin;
import com.example.admincontact.repositry.AdminContactRepository;


@Service
public class AdminContactService {

	@Autowired
	private AdminContactRepository contactRepository;
	

	
	public Admin createUser(Admin user) {	
			return contactRepository.save(user);
	}
	
	public List<Admin> getAllUser() {
		return contactRepository.findAll();
	}
	
	public Admin getUser(String id) {
		Admin ad= contactRepository.findById(id).get();
		return ad;
	}
	public Admin findUserByName(String name) {
		Admin result=contactRepository.findByuserName(name);
		return result;
	}
}
