/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer;

import java.util.LinkedList;
import java.util.List;
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
import pt.lros.demo.userscontainer.ports.ListUsersPort;

/**
 *
 * @author risca
 */
@RunWith(MockitoJUnitRunner.class)
public class ListUsersImplTest {

    private static final Logger log = LoggerFactory.getLogger(ListUsersImplTest.class);
    @Mock
    private ListUsersPort listUsersPort;
    private ListUsersImpl listUsers;
    private List<User> users;

    public ListUsersImplTest() {
    }

    @Before
    public void setUp() {
        this.users = new LinkedList<>();
        this.listUsers = new ListUsersImpl(listUsersPort);
        when(listUsersPort.listUsers()).thenReturn(users);
    }

    @Test
    public void testUsers() {
        log.info("users");
        assertEquals(listUsers.users(), users);

        verify(listUsersPort).listUsers();
    }

}
