package com.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.LoginDto;
import com.entity.Login;
import com.entity.User;
import com.exception.ResourceNotFoundException;
import com.repository.LoginRepository;
import com.repository.UserRepository;
import com.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public void add(LoginDto loginDto) {

		/** Obtenir l'utilisateur **/
		User user = userRepository.findByUsername(loginDto.getUsername());

		if (user == null) {
			throw new ResourceNotFoundException("Utilisateur non trouvé !");
		}

		/** Enregistrer **/
		Login login = new Login();

		login.setId(null);
		login.setUser(user);
		login.setLoginAt(new Date());
		loginRepository.save(login);

	}

}
