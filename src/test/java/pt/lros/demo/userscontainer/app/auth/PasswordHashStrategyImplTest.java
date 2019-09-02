/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer.app.auth;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author risca
 */
public class PasswordHashStrategyImplTest {

    private static final Logger log = LoggerFactory.getLogger(PasswordHashStrategyImplTest.class);
    private PasswordHashStrategyImpl passwordHashStrategyImpl;
    private Date timestamp;
    private String password;

    public PasswordHashStrategyImplTest() {
    }

    @Before
    public void setup() {
        this.passwordHashStrategyImpl = new PasswordHashStrategyImpl();
        this.timestamp = new Date(1567279237558L);
        this.password = "cen@s";
    }

    @Test
    public void testHash() {
        String hash = this.passwordHashStrategyImpl.hash(password, timestamp);
        log.info("Hash result = {}", hash);
        assertNotNull(hash);
        assertEquals(hash, this.passwordHashStrategyImpl.hash("cen@s", timestamp));
        assertFalse(hash.contains(password));

    }

}
