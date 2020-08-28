package com.hansung.web.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserVo {

	private int userId;
	
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
	private String username;
	
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}",
            message = "비밀번호는 8~15자의 범위에 해당하는 영문과 특수문자의 조합")
	private String password;
	
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
	private String email;
	
    @NotBlank(message = "이름은 필수 입력 값입니다.")
	private String name;
    
    @Pattern(regexp="\\S{2,15}", message="공백이 없어야 합니다.")    
	private String branch;
	
	private int enabled;
	
	private String roleName;
	
}
