package com.saksham.employee_management.repository;

import com.saksham.employee_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

Optional<User> findByUsername(String username);

boolean existsByUsername(String username);

}
