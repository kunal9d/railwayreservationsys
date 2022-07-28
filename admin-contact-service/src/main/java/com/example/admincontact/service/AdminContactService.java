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
	

	public Admin addAdmin (Admin admin) {
		return contactRepository.save(admin);
	}

	public List<Admin> getAdmin() {
		List<Admin> admin = contactRepository.findAll();
		return admin;
	}

	public Optional<Admin> getAdminbyId(String id) {
		return contactRepository.findById(id);
	}

	public void deleteAdmin(String id) {
		contactRepository.deleteById(id);
	}
	public Admin findAdminByName(String name) {
		Admin result=contactRepository.findByusername(name);
		return result;
	}
}

