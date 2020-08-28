package com.hansung.web.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipalVo implements UserDetails {
	private static final long serialVersionUID = 1L;
	private ArrayList<UserVo> userVO;

	public UserPrincipalVo(ArrayList<UserVo> userAuthes) {
		this.userVO = userAuthes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { // 해당 유저 권한 목록
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (int i = 0; i < userVO.size(); i++) {
			authorities.add(new SimpleGrantedAuthority(userVO.get(i).getRoleName()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return userVO.get(0).getPassword();
	}

	@Override
	public String getUsername() {
		return userVO.get(0).getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		if (userVO.get(0).getEnabled() == 0) {
			return false;
		} else
			return true;

	}

}
