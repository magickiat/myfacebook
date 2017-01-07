package com.magicalcyber.myfacebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.magicalcyber.myfacebook.model.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

}
