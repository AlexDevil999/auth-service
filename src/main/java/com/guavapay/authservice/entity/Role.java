package com.guavapay.authservice.entity;

import com.guavapay.authservice.enums.RoleEnum;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(	name = "roles")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Role extends AbstractEntity{

    @Enumerated(EnumType.STRING)
    @Column(name = "name_role")
    private RoleEnum name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return getId() != null && Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
