/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.lros.demo.userscontainer.ports.ReadUserPort;

/**
 *
 * @author risca
 */
@RunWith(MockitoJUnitRunner.class)
public class ReadUserImplTest {

    private static final Logger log = LoggerFactory.getLogger(ReadUserImplTest.class);
    @Mock
    private ReadUserPort readUserPort;
    private ReadUserImpl readUser;
    private Optional<User> result;
    private String username;

    public ReadUserImplTest() {
    }

    @Before
    public void setUp() {
        this.readUser = new ReadUserImpl(readUserPort);
        this.result = Optional.of(new User());
        this.username = "user";
        when(readUserPort.getUser(username)).thenReturn(result);
    }

    @Test
    public void testGetUser() {
        log.info("getUser");
        Optional<User> result = readUser.getUser(username);
        assertEquals(this.result, result);
        verify(readUserPort).getUser(username);
    }

}
