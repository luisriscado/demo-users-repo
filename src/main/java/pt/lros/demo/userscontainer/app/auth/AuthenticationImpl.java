/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer.app.auth;

import pt.lros.demo.userscontainer.ports.PasswordHashStrategy;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import pt.lros.demo.userscontainer.User;
import pt.lros.demo.userscontainer.ports.ReadUserPort;

@Service
@RequiredArgsConstructor
class AuthenticationImpl implements AuthenticationProvider {

    private final ReadUserPort readUserPort;

    private final PasswordHashStrategy passwordHash;

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        final String username = (String) a.getPrincipal();
        final String password = (String) a.getCredentials();

        final User user = readUserPort.getUser(username).orElseThrow(() -> new BadCredentialsException("Invalid user"));

        final String passwordHashValue = passwordHash.hash(password, user.getCreateTimestamp());

        final String storedPassword = user.getPassword();

        if (!Objects.equals(storedPassword, passwordHashValue)) {
            throw new BadCredentialsException("Invalid password");
        }
        a = new UsernamePasswordAuthenticationToken(user, "secured");
        a.setAuthenticated(true);
        return a;

    }

    @Override
    public boolean supports(Class<?> type) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(type);
    }

}
