package com.milestone.ticket.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milestone.ticket.platform.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
}
