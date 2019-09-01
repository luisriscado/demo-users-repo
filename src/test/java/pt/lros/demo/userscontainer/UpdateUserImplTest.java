/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static pt.lros.demo.userscontainer.TestUtil.assertValidationExceptionContains;
import static pt.lros.demo.userscontainer.TestUtil.assertValidationExceptionSize;
import pt.lros.demo.userscontainer.check.Check;
import pt.lros.demo.userscontainer.errors.ValidationException;
import pt.lros.demo.userscontainer.ports.PasswordHashStrategy;
import pt.lros.demo.userscontainer.ports.ReadUserPort;
import pt.lros.demo.userscontainer.ports.UpdateUserPort;

/**
 *
 * @author risca
 */
@RunWith(MockitoJUnitRunner.class)
public class UpdateUserImplTest {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserImplTest.class);

    @Mock
    private ReadUserPort readUserPort;

    @Mock
    private UpdateUserPort updateUserPort;

    private UpdateUserImpl updateUser;

    @Mock
    private PasswordHashStrategy passwordHashStrategy;
    private String username;
    private User user;

    @Before
    public void setUp() {
        this.updateUser = new UpdateUserImpl(readUserPort, updateUserPort, passwordHashStrategy, new Check());
        this.username = "usrn";
        this.user = new User();
        this.user.setCreateTimestamp(new Date());
        when(readUserPort.getUser(username)).thenReturn(Optional.of(user));
        when(passwordHashStrategy.hash(ArgumentMatchers.anyString(), any(Date.class))).thenReturn("HA$H");
    }

    @Test
    public void testUpdateUser() throws ValidationException {
        log.info("testUpdateUser");

        UpdateUserData updateUserData = new UpdateUserData("New Password", "New Name");
        User result = this.updateUser.updateUser(username, updateUserData);
        assertEquals(updateUserData.getName(), result.getName());
        verify(readUserPort).getUser(username);
    }

    @Test
    public void testUpdateNotExistant() {
        log.info("testUpdateNotExistant");

        UpdateUserData updateUserData = new UpdateUserData(null, null);
        try {
            this.updateUser.updateUser("asda", updateUserData);
        } catch (ValidationException ex) {

            assertValidationExceptionContains(ex, Arrays.asList(UserErrors.USER_DOS_NOT_EXIST));
            assertValidationExceptionSize(ex, 1);
        }

        verify(readUserPort).getUser("asda");
    }

    @Test
    public void testUpdateUserUserEmpty() {
        log.info("updateUserUsernameEmpty");

        UpdateUserData updateUserData = new UpdateUserData(null, null);
        try {
            this.updateUser.updateUser("", updateUserData);
        } catch (ValidationException ex) {

            assertValidationExceptionContains(ex, Arrays.asList(UserErrors.USER_DOS_NOT_EXIST));
            assertValidationExceptionSize(ex, 1);
        }

        verify(readUserPort, never()).getUser(any());
    }

}
