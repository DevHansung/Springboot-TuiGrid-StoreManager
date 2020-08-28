package com.hansung.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hansung.web.mapper.UserMapper;
import com.hansung.web.vo.UserPrincipalVo;
import com.hansung.web.vo.UserVo;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// DB에서 유저정보를 불러온다. Custom한 Userdetails를 반환.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ArrayList<UserVo> userAuthes = userMapper.findByUsername(username);
		if (userAuthes.size() == 0) {
			throw new UsernameNotFoundException("User " + username + " Not Found!");
		}
		return new UserPrincipalVo(userAuthes);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public boolean insertUser(UserVo userVo) {
		boolean result = false;
		userVo.setPassword(bCryptPasswordEncoder.encode(userVo.getPassword()));
		if (!userVo.getUsername().equals("admin")) {
			userVo.setRoleName("ROLE_MANAGER");
		} else if (userVo.getUsername().equals("admin")) {
			userVo.setRoleName("ROLE_ADMIN");
		}
		boolean successStatus = userMapper.insertUser(userVo);
		if (successStatus == true) {
			int userNo = userMapper.findUserId(userVo.getUsername());
			int roleNo = userMapper.findRoleId(userVo.getRoleName());
			userMapper.insertUserRole(userNo, roleNo);
			result = true;
			return result;
		}
		return result;
	}

	// username, email 유효성 검사
	public Map<String, String> validateHandling(UserVo userVo) {
		Map<String, String> validatorResult = new HashMap<>();
		if (userMapper.findUserByUsername(userVo.getUsername()) != null) {
			validatorResult.put("username", "이미 존재하는 username 입니다");
		}
		if (userMapper.findUserByEmail(userVo.getEmail()) != null) {
			validatorResult.put("email", "이미 존재하는 email 입니다");
		}
		return validatorResult;
	}
	public UserVo findUserInfoByUsername(String username) {
		return userMapper.findUserInfoByUsername(username);
	}
	

}
