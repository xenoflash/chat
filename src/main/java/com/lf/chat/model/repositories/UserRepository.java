package com.lf.chat.model.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lf.chat.model.User;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}