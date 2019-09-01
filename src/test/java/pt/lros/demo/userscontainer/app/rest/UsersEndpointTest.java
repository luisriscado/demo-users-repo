/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer.app.rest;

import java.util.LinkedList;
import java.util.List;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pt.lros.demo.userscontainer.CreateUser;
import pt.lros.demo.userscontainer.CreateUserData;
import pt.lros.demo.userscontainer.DeleteUser;
import pt.lros.demo.userscontainer.ListUsers;
import pt.lros.demo.userscontainer.ReadUser;
import pt.lros.demo.userscontainer.UpdateUser;
import pt.lros.demo.userscontainer.UpdateUserData;
import pt.lros.demo.userscontainer.User;

/**
 *
 * @author risca
 */
@RunWith(MockitoJUnitRunner.class)
public class UsersEndpointTest {

    private static final Logger log = LoggerFactory.getLogger(UsersEndpointTest.class);
    @Mock
    private CreateUser createUser;
    @Mock
    private ReadUser readUser;
    @Mock
    private UpdateUser updateUser;
    @Mock
    private ListUsers listUsers;
    @Mock
    private DeleteUser deleteUser;

    private UsersEndpoint userEndpoint;

    @Before
    public void setUp() {
        this.userEndpoint = new UsersEndpoint(createUser, readUser, updateUser, listUsers, deleteUser);
    }

    @Test
    public void testList() {
        log.info("list");
        final List<User> list = new LinkedList<>();

        when(listUsers.users()).thenReturn(list);

        List<User> result = userEndpoint.list();

        assertEquals(list, result);
        verify(listUsers).users();

    }

    @Test
    public void testCreate() throws Exception {
        log.info("create");
        final CreateUserData createUserData = new CreateUserData(null, null, null);
        final User created = new User();
        when(createUser.createUser(createUserData)).thenReturn(created);

        User result = userEndpoint.create(createUserData);

        assertEquals(created, result);
        verify(createUser).createUser(createUserData);
    }

    @Test
    public void testRead() throws Exception {
        log.info("read");
        final User user = new User();
        final String username = "nas";

        when(readUser.getUser(username)).thenReturn(Optional.of(user));

        Optional<User> result = userEndpoint.read(username);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(readUser).getUser(username);
    }

    @Test
    public void testUpdate() throws Exception {
        log.info("update");
        String username = "asd";
        User user = new User();

        UpdateUserData updateUserData = new UpdateUserData(null, null);
        when(updateUser.updateUser(username, updateUserData)).thenReturn(user);
        User result = userEndpoint.update(username, updateUserData);

        assertEquals(result, user);
        verify(updateUser).updateUser(username, updateUserData);

    }

    @Test
    public void testDelete() throws Exception {
        log.info("delete");
        final String username = "nas";

        ResponseEntity result = userEndpoint.delete(username);
        verify(deleteUser).deleteUser(username);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

}
