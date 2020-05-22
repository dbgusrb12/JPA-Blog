package com.jpa.jpaBlog.jpaBlog.user.repository;

import com.jpa.jpaBlog.jpaBlog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByGithub(String github);
}
