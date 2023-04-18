package com.service.impl;

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.LoginDto;
import com.dto.LoginInfoDto;
import com.dto.UserAddDto;
import com.dto.UserInfoDto;
import com.dto.UserUpdateDto;
import com.entity.Commercial;
import com.entity.Role;
import com.entity.User;
import com.entity.type.UserType;
import com.exception.ResourceAlreadyExistException;
import com.exception.ResourceConflictException;
import com.exception.ResourceNotFoundException;
import com.repository.CommercialRepository;
import com.repository.LoginRepository;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import com.security.JwtTokenProvider;
import com.security.WebSecurityConfig;
import com.service.UserService;
import com.util.Utils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private WebSecurityConfig webSecurityConfig;

	@Autowired
	private CommercialRepository commercialRepository;

	@Autowired
	private LoginRepository loginRepository;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional
	public UserInfoDto add(UserAddDto userAdd) {

		/** Vérification des dates **/
		Date date = new Date();

		if (userAdd.getCreateAt() == null) {
			userAdd.setCreateAt(date);
		} else if (userAdd.getCreateAt().compareTo(date) == 1) {
			throw new ResourceConflictException("Date création d'utilisateur incorrect !");
		}

		/** Vérification de l'utilisateur **/
		if (userRepository.existsByUsername(userAdd.getUsername()))
			throw new ResourceAlreadyExistException("Nom d'utilisateur déja utilisé !");

		/** Vérification de commercial **/
		Commercial commercial = null;

		if (userAdd.getType() != UserType.admin) {
			if (userAdd.getCommercial() == null) {
				throw new ResourceNotFoundException("Commercial non trouvé !");
			}
			commercial = commercialRepository.findById(userAdd.getCommercial()).orElse(null);
			if (commercial == null) {
				throw new ResourceNotFoundException("Commercial non trouvé !");
			}
		}

		/** Vérification des utilisateur a gérer **/
		Set<User> usersToManaged = new HashSet<User>();

		switch (userAdd.getType()) {
		case admin:
			break;
		case commercial:
			break;
		case commercial_manager:
			/** Vérification des utilisateurs à gérer **/
			for (Integer userId : userAdd.getUsersToManaged()) {
				User userToManaged = this.userRepository.findById(userId).orElse(null);
				if (userToManaged == null) {
					throw new ResourceNotFoundException("Utilisateur non trouvé !");
				}
				if (userToManaged.getType() == UserType.commercial) {
					usersToManaged.add(userToManaged);
				}
			}
			break;
		case regional_manager:
			/** Vérification des utilisateurs à gérer **/
			for (Integer userId : userAdd.getUsersToManaged()) {
				User userToManaged = this.userRepository.findById(userId).orElse(null);
				if (userToManaged == null) {
					throw new ResourceNotFoundException("Utilisateur non trouvé !");
				}
				if (userToManaged.getType() == UserType.commercial_manager) {
					usersToManaged.add(userToManaged);
				}
			}
			break;
		case commercial_director:
			/** Vérification des utilisateurs à gérer **/
			for (Integer userId : userAdd.getUsersToManaged()) {
				User userToManaged = this.userRepository.findById(userId).orElse(null);
				if (userToManaged == null) {
					throw new ResourceNotFoundException("Utilisateur non trouvé !");
				}
				if (userToManaged.getType() == UserType.regional_manager) {
					usersToManaged.add(userToManaged);
				}
			}
			break;
		case director_general:
			break;
		default:
			break;
		}

		/** Vérification des rôles **/
		Set<Role> roles = new HashSet<Role>();
		for (Integer roleId : userAdd.getRoles()) {
			Role role = roleRepository.findById(roleId).orElse(null);
			if (role == null) {
				throw new ResourceNotFoundException("Rôle non trouvé !");
			}
			roles.add(role);
		}

		/** Ajouter **/
		User user = modelMapper.map(userAdd, User.class);

		user.setId(null);
		user.setPassword(passwordEncoder.encode(userAdd.getPassword()));
		user.setRoles(roles);
		user.setCommercial(commercial);
		userRepository.save(user);

		/** Définir utilisateurs a gérer **/
		usersToManaged.forEach(userToManaged -> {
			userToManaged.setManager(user);
			userRepository.save(userToManaged);
		});

		return modelMapper.map(user, UserInfoDto.class);
	}

	@Override
	@Transactional
	public UserInfoDto update(UserUpdateDto userUpdate) {

		/** Vérification des dates **/
		Date date = new Date();

		if (userUpdate.getCreateAt() == null) {
			userUpdate.setCreateAt(date);
		} else if (userUpdate.getCreateAt().compareTo(date) == 1) {
			throw new ResourceConflictException("Date création d'utilisateur incorrect !");
		}

		/** Vérification de l'utilisateur **/
		User userOld = userRepository.findById(userUpdate.getId()).orElse(null);
		if (userOld == null) {
			throw new ResourceNotFoundException("Utilisateur non trouvé !");
		}

		if (!userOld.getUsername().equals(userUpdate.getUsername())
				&& userRepository.existsByUsername(userUpdate.getUsername())) {
			throw new ResourceAlreadyExistException("Nom d'utilisateur déja utilisé !");
		}

		/** Vérification de commercial **/
		Commercial commercial = null;

		if (userUpdate.getType() != UserType.admin) {
			if (userUpdate.getCommercial() == null) {
				throw new ResourceNotFoundException("Commercial non trouvé !");
			}

			commercial = commercialRepository.findById(userUpdate.getCommercial()).orElse(null);
			if (commercial == null) {
				throw new ResourceNotFoundException("Commercial non trouvé !");
			}
		}

		/** Vérification des utilisateur a gérer **/
		Set<User> usersToManaged = new HashSet<User>();

		switch (userUpdate.getType()) {
		case admin:
			break;
		case commercial:
			break;
		case commercial_manager:
			/** Vérification des utilisateurs à gérer **/
			for (Integer userId : userUpdate.getUsersToManaged()) {
				User userToManaged = this.userRepository.findById(userId).orElse(null);
				if (userToManaged == null) {
					throw new ResourceNotFoundException("Utilisateur non trouvé !");
				}
				if (userToManaged.getType() == UserType.commercial) {
					usersToManaged.add(userToManaged);
				}
			}
			break;
		case regional_manager:
			/** Vérification des utilisateurs à gérer **/
			for (Integer userId : userUpdate.getUsersToManaged()) {
				User userToManaged = this.userRepository.findById(userId).orElse(null);
				if (userToManaged == null) {
					throw new ResourceNotFoundException("Utilisateur non trouvé !");
				}
				if (userToManaged.getType() == UserType.commercial_manager) {
					usersToManaged.add(userToManaged);
				}
			}
			break;
		case commercial_director:
			/** Vérification des utilisateurs à gérer **/
			for (Integer userId : userUpdate.getUsersToManaged()) {
				User userToManaged = this.userRepository.findById(userId).orElse(null);
				if (userToManaged == null) {
					throw new ResourceNotFoundException("Utilisateur non trouvé !");
				}
				if (userToManaged.getType() == UserType.regional_manager) {
					usersToManaged.add(userToManaged);
				}
			}
			break;
		case director_general:
			break;
		default:
			break;
		}

		/** Vérification des rôles **/
		Set<Role> roles = new HashSet<Role>();
		for (Integer roleId : userUpdate.getRoles()) {
			Role role = roleRepository.findById(roleId).orElse(null);
			if (role == null) {
				throw new ResourceNotFoundException("Rôle non trouvé !");
			}
			roles.add(role);
		}

		/** Modifier **/
		User user = modelMapper.map(userUpdate, User.class);

		if (user.getPassword() == null) {
			user.setPassword(userOld.getPassword());
		} else {
			user.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
		}

		user.setRoles(roles);
		user.setCommercial(commercial);
		user.setManager(userOld.getManager());
		
		userRepository.save(user);

		/** Définir utilisateurs a gérer **/
		List<User> usersToManagedOld = this.userRepository.getListByManager(userOld.getId());

		usersToManagedOld.forEach(userToManagerOld -> {
			if (!usersToManaged.contains(userToManagerOld)) {
				userToManagerOld.setManager(null);
				userRepository.save(userToManagerOld);
			}
		});

		usersToManaged.forEach(userToManaged -> {
			userToManaged.setManager(user);
			userRepository.save(userToManaged);
		});

		return modelMapper.map(user, UserInfoDto.class);

	}

	@Override
	@Transactional
	public UserInfoDto editState(Integer id, boolean isEnabled) {
		/** Vérification de l'utilisateur **/
		if (!userRepository.existsById(id)) {
			throw new ResourceNotFoundException("Utilisateur non trouvé !");
		}

		User user = userRepository.getOne(id);

		user.setEnabled(isEnabled);

		user = userRepository.save(user);
		return modelMapper.map(user, UserInfoDto.class);
	}

	@Override
	@Transactional
	public boolean delete(Integer id) {
		/** Vérification de l'utilisateur **/
		if (!userRepository.existsById(id))
			throw new ResourceNotFoundException("Utilisateur non trouvé !");

		List<User> usersToManaged = this.userRepository.getListByManager(id);
		usersToManaged.forEach(userToManager -> {
			userToManager.setManager(null);
			this.userRepository.save(userToManager);
		});

		try {
			userRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public UserInfoDto getOne(Integer id) {
		/** Vérification de l'utilisateur **/
		if (!userRepository.existsById(id)) {
			throw new ResourceNotFoundException("Utilisateur non trouvé !");
		}

		User user = userRepository.getOne(id);

		UserInfoDto userInfo = modelMapper.map(user, UserInfoDto.class);

		/** Obtenir le dernier login **/
		Page<LoginInfoDto> logins = loginRepository.findLoginByUser(userInfo.getId(), new PageRequest(0, 1));
		if (logins.getContent().size() > 0) {
			userInfo.setLoginLast(logins.getContent().get(0));
		}

		/**
		 * Retourne
		 */
		return userInfo;
	}

	@Override
	public UserInfoDto getOne(String userName) {
		/** Vérification de l'utilisateur **/
		if (!userRepository.existsByUsername(userName)) {
			throw new ResourceNotFoundException("Utilisateur non trouvé !");
		}

		User user = userRepository.findByUsername(userName);

		UserInfoDto userInfo = modelMapper.map(user, UserInfoDto.class);

		/** Obtenir le dernier login **/
		Page<LoginInfoDto> logins = loginRepository.findLoginByUser(userInfo.getId(), new PageRequest(0, 1));
		if (logins.getContent().size() > 0) {
			userInfo.setLoginLast(logins.getContent().get(0));
		}

		return userInfo;
	}

	@Override
	public List<UserInfoDto> getList() {

		List<UserInfoDto> usersInfo = Utils.map(modelMapper, userRepository.findAll(), UserInfoDto.class);
		usersInfo = usersInfo.stream().sorted(Comparator.comparing(UserInfoDto::getCreateAt).reversed())
				.collect(Collectors.toList());

		/** Obtenir le dernier login **/
		Page<LoginInfoDto> logins;
		for (UserInfoDto userInfo : usersInfo) {
			logins = loginRepository.findLoginByUser(userInfo.getId(), new PageRequest(0, 1));
			if (logins.getContent().size() > 0) {
				userInfo.setLoginLast(logins.getContent().get(0));
			}
		}

		return usersInfo;
	}

	@Override
	public String signin(LoginDto loginDto) {
		try {

			webSecurityConfig.authenticationManagerBean().authenticate(
					new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

			return jwtTokenProvider.createToken(loginDto, userRepository.findRolesByUsername(loginDto.getUsername()));

		} catch (AuthenticationException e) {
			if (e.getClass().isAssignableFrom(BadCredentialsException.class)) {
				throw new ResourceNotFoundException("Nom d'utilisateur ou mot de passe invalide !");
			} else if (e.getClass().isAssignableFrom(DisabledException.class)) {
				throw new ResourceNotFoundException("Votre compte est désactivé !");
			} else {
				throw new ResourceNotFoundException(e.getMessage());
			}
		}

	}
	

	@Override
	public UserInfoDto getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new ResourceNotFoundException("Utilisateur non trouvé !");
		}
		return modelMapper.map(user, UserInfoDto.class);
	}

	@Override
	public User whoami() {
		User currentUser = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		return currentUser;
	}

}
