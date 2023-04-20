package com.sun.Azit.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

import com.sun.Azit.constant.Role;
import org.springframework.security.crypto.password.PasswordEncoder;


@Getter
@ToString
@Table(name="member")
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity{
    @Id @Column(name="member_id") @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Setter @Column(nullable = false, length = 20) private String name;
    @Setter @Column(nullable = false, unique = true) private String email;
    @Setter @Column(nullable = false) private String password;
    @Setter @Enumerated(EnumType.STRING) private Role role;

    protected Member(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static Member of(String name, String email, String password , Role role) {
        return new Member(name, email, password, role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return id.equals(member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
