package com.hansung.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hansung.web.mapper.UserManagementMapper;
import com.hansung.web.vo.UserVo;

@Service
public class UserManagementService {

	@Autowired
	private UserManagementMapper userManagementMapper;

	public int geUserCount() {
		return userManagementMapper.getUserCount();
	}

	public List<UserVo> getUserListPaging(int displayPost, int postNum) {
		Map<String, Object> paging = new HashMap<String, Object>();
		paging.put("displayPost", displayPost);
		paging.put("postNum", postNum);
		return userManagementMapper.getUserListPaging(paging);
	}

	public void changeEnabled(Map<String, Object> param) {
		userManagementMapper.changeEnabled(param);
	}

	public void changeRole(Map<String, Object> param) {
		userManagementMapper.changeRole(param);
		
	}
}
