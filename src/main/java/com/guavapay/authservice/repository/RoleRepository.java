package com.guavapay.authservice.repository;

import com.guavapay.authservice.entity.Role;
import com.guavapay.authservice.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(RoleEnum name);
}
