package com.integrate.identity.repositories;

import com.integrate.identity.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {


    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }
}
