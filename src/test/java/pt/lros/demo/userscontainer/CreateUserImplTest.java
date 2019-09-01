/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer;

import java.util.Arrays;
import java.util.Date;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.notNull;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import static pt.lros.demo.userscontainer.TestUtil.assertValidationExceptionContains;
import static pt.lros.demo.userscontainer.TestUtil.assertValidationExceptionSize;
import pt.lros.demo.userscontainer.check.Check;
import pt.lros.demo.userscontainer.errors.ValidationException;
import pt.lros.demo.userscontainer.ports.CreateUserPort;
import pt.lros.demo.userscontainer.ports.PasswordHashStrategy;
import pt.lros.demo.userscontainer.ports.UserExistsPort;

/**
 *
 * @author risca
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateUserImplTest {

    private static final String HASH_MOCK = "HA$H";

    @Mock
    private PasswordHashStrategy hashStrategy;
    @Mock
    private CreateUserPort createUserPort;
    @Mock
    private UserExistsPort userExistsPort;

    private CreateUserImpl createUser;

    public CreateUserImplTest() {
    }

    @Before
    public void setup() {
        this.createUser = new CreateUserImpl(hashStrategy, createUserPort, userExistsPort, new Check());
        when(hashStrategy.hash(anyString(), any(Date.class))).thenReturn(HASH_MOCK);
    }

    @Test
    public void testCreateUser() throws ValidationException {
        CreateUserData c = new CreateUserData("USR", "Pw@Rd89", "NAME");
        User created = this.createUser.createUser(c);
        assertNotNull(created);
        assertEquals(c.getUsername(), created.getUsername());
        assertEquals(c.getName(), created.getName());
        assertNotEquals(c.getPassword(), created.getPassword());
        assertNotNull(created.getCreateTimestamp());
        assertNotNull(created.getUpdateTimestamp());
        verify(userExistsPort).exists(c.getUsername());
        verify(createUserPort).create(notNull());
        verify(hashStrategy).hash(eq(c.getPassword()), notNull());
    }

    @Test
    public void testCreateUserMandatoryPassword() {
        CreateUserData c = new CreateUserData("USR", null, "NAME");
        try {
            this.createUser.createUser(c);
            fail("Validation exception not throwed");
        } catch (ValidationException ex) {
            assertValidationExceptionContains(ex, Arrays.asList(UserErrors.PASSWORD_MANDATORY));
            assertValidationExceptionSize(ex, 1);
        }

        verify(userExistsPort).exists(c.getUsername());
        verify(createUserPort, never()).create(any());
    }

    @Test
    public void testCreateUserMandatoryUserName() {
        CreateUserData c = new CreateUserData("", "null23", "NAME");
        try {
            this.createUser.createUser(c);
            fail("Validation exception not throwed");
        } catch (ValidationException ex) {
            assertValidationExceptionContains(ex, Arrays.asList(UserErrors.USERNAME_MANDATORY));
            assertValidationExceptionSize(ex, 1);
        }
    }

    @Test
    public void testCreateUserMandatoryName() {
        CreateUserData c = new CreateUserData("USR", "null23", "");
        try {
            this.createUser.createUser(c);
            fail("Validation exception not throwed");
        } catch (ValidationException ex) {
            assertValidationExceptionContains(ex, Arrays.asList(UserErrors.NAME_MANDATORY));
            assertValidationExceptionSize(ex, 1);
        }
    }

    @Test
    public void testCreateUsernameExists() {
        CreateUserData c = new CreateUserData("USR", "null23", "LFlias");
        when(userExistsPort.exists(c.getUsername())).thenReturn(true);
        try {
            this.createUser.createUser(c);
            fail("Validation exception not throwed");
        } catch (ValidationException ex) {
            assertValidationExceptionContains(ex, Arrays.asList(UserErrors.USERNAME_ALREADY_EXISTS));
            assertValidationExceptionSize(ex, 1);
        }
    }

    @Test
    public void testCreateinvalidPasswordStrengh() {
        CreateUserData c = new CreateUserData("USR", "null", "LFlias");
        try {
            this.createUser.createUser(c);
            fail("Validation exception not throwed");
        } catch (ValidationException ex) {
            assertValidationExceptionContains(ex, Arrays.asList(UserErrors.INVALID_STRENGH_PASSWORD));
            assertValidationExceptionSize(ex, 1);
        }
    }

}
