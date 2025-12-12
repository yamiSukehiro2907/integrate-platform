package com.integrate.identity.security;

import com.integrate.identity.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        try {
            CustomUserDetails userDetails = userService.loadUserByUsername(email);

            if (!userDetails.getPassword().equals(password)) {
                throw new LockedException("Account is Locked! Please contact support");
            }

            if (!userDetails.isEnabled()) {
                throw new DisabledException("Account is Disabled! Please contact support");
            }

            if (userDetails.isAccountNonExpired()) {
                throw new AccountExpiredException("Your account has expired!");
            }

            if (!userDetails.isCredentialsNonExpired()) {
                throw new CredentialsExpiredException("Your credentials have expired!");
            }

            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("Invalid Password!");
            }

            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("Invalid Credentials");
        } catch (LockedException | DisabledException | AccountExpiredException | CredentialsExpiredException |
                 BadCredentialsException error) {
            throw error;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException("Unexpected Authentication occured: " + e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
