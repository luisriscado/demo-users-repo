/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer.app.auth;

import java.nio.charset.StandardCharsets;
import pt.lros.demo.userscontainer.ports.PasswordHashStrategy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Service;

@Service
class PasswordHashStrategyImpl implements PasswordHashStrategy {

    private static final String DATE_SALT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    /**
     * Create an encrypted password from the password value and salted with
     * creation date
     *
     * @param password
     * @param createdDate
     * @return
     */
    @Override
    public String hash(String password, Date createdDate) {
        Objects.nonNull(password);
        Objects.nonNull(createdDate);

        final String salt = new SimpleDateFormat(DATE_SALT_PATTERN).format(createdDate);
        return Sha512DigestUtils.shaHex(String.format("%s/%s", password, salt));
    }

}
