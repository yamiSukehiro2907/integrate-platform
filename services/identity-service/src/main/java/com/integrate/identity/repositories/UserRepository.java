package com.integrate.identity.repositories;

import com.integrate.identity.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);
}
