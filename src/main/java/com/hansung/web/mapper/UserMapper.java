package com.hansung.web.mapper;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.hansung.web.vo.UserVo;

@Repository
public interface UserMapper {
	ArrayList<UserVo> findByUsername(String username);

	boolean insertUser(UserVo userVo);

	int insertUserRole(int userId, int roleId);

	UserVo findUserInfoByUsername(String username);

	String findUserByUsername(String username);

	String findUserByEmail(String email);

	int findUserId(String username);

	int findRoleId(String username);
}
