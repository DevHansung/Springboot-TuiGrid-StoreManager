package com.hansung.web.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hansung.web.vo.UserVo;

@Repository
public interface UserManagementMapper {
	int getUserCount();

	List<UserVo> getUserListPaging(Map<String, Object> paging);

	void changeEnabled(Map<String, Object> param);

	void changeRole(Map<String, Object> param);
}
