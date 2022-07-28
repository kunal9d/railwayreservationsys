package com.example.admincontact.security;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.admincontact.model.Admin;
import com.example.admincontact.repositry.AdminContactRepository;

@Service
public class UserService1 implements UserDetailsService{
	
	@Autowired
	AdminContactRepository adminRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin foundedUser=adminRepo.findByusername(username);
		if (foundedUser==null) {
			return null;
		}
		String user=foundedUser.getUsername();
		String pass=foundedUser.getPassword();
		return new User(user, pass,new ArrayList<>());
	}

}
