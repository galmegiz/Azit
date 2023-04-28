package com.sun.Azit.dto;

import com.sun.Azit.constant.Role;
import com.sun.Azit.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.LastModifiedBy;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class MemberFormDto {
    @NotBlank(message = "이름은 필수 입력 값입니다")
    private String name;

    @NotEmpty(message = "이메일은 필수 입력값입니다")
    @Email(message = "이메일 형식으로 입력해주세요")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력값입니다")
    @Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하입니다")
    private String password;

    private Role role;

    public MemberFormDto(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public MemberFormDto(String name, String email, String password, Role role){
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static MemberFormDto from(Member member) {
        return new MemberFormDto(
                member.getName(),
                member.getEmail(),
                member.getPassword(),
                member.getRole());
    }

    public Member toEntity(){
        return Member.of(
          name, email, password, Role.USER
        );
    }

}
