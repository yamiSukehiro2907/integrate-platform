package com.integrate.identity.helpers;

import com.integrate.identity.domain.User;
import com.integrate.identity.dto.UserDto;

public class UserHelper {

    public static UserDto createUserDto(User user) {
        return new UserDto(
                user.getId().toString(),
                user.getFullName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
