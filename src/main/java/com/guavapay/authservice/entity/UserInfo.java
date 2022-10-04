package com.guavapay.authservice.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(	name = "users_information")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo extends AbstractEntity{

    @Column(name = "last_name")
    private String lastname;
    @Column(name = "first_name")
    private String firstname;
    @Column(name = "sex")
    private String sex;
    @Column(name = "age_value")
    private Integer age;

    @OneToOne(mappedBy = "userInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserInfo userInfo = (UserInfo) o;
        return getId() != null && Objects.equals(getId(), userInfo.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
