/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer;

import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import static pt.lros.demo.userscontainer.TestUtil.assertValidationExceptionContains;
import static pt.lros.demo.userscontainer.TestUtil.assertValidationExceptionSize;
import pt.lros.demo.userscontainer.check.Check;
import pt.lros.demo.userscontainer.errors.ValidationException;
import pt.lros.demo.userscontainer.ports.DeleteUserPort;
import pt.lros.demo.userscontainer.ports.ReadUserPort;

/**
 *
 * @author risca
 */
@RunWith(MockitoJUnitRunner.class)
public class DeleteUserImplTest {

    @Mock
    private ReadUserPort readUserPort;

    @Mock
    private DeleteUserPort deleteUserPort;
    private DeleteUserImpl deleteUser;
    private User user;
    private String userName;

    public DeleteUserImplTest() {
    }

    @Before
    public void setup() {
        this.deleteUser = new DeleteUserImpl(new Check(), readUserPort, deleteUserPort);
        this.user = new User();
        this.userName = "usrTst";
        when(readUserPort.getUser(userName)).thenReturn(Optional.of(user));
        when(readUserPort.getUser("ITuser")).thenReturn(Optional.of(user));

    }

    @Test
    public void testDeleteUser() throws ValidationException {
        deleteUser.deleteUser(userName);
        verify(readUserPort).getUser(userName);
        verify(deleteUserPort).delete(user);
    }

    @Test
    public void testDeleteUserNotAllowed() {
        try {
            deleteUser.deleteUser("ITuser");
        } catch (ValidationException ex) {
            assertValidationExceptionContains(ex, Arrays.asList(UserErrors.USER_DELETE_NOT_ALLOWED));
            assertValidationExceptionSize(ex, 1);
        }
        verify(readUserPort).getUser("ITuser");
    }

    @Test
    public void testDeleteUserNotExistant() {
        try {
            deleteUser.deleteUser("");
        } catch (ValidationException ex) {
            assertValidationExceptionContains(ex, Arrays.asList(UserErrors.USER_DOS_NOT_EXIST));
            assertValidationExceptionSize(ex, 1);
        }
        verify(readUserPort, never()).getUser(anyString());

        try {
            deleteUser.deleteUser("cenas");
        } catch (ValidationException ex) {
            assertValidationExceptionContains(ex, Arrays.asList(UserErrors.USER_DOS_NOT_EXIST));
            assertValidationExceptionSize(ex, 1);
        }
        verify(readUserPort).getUser("cenas");
    }

}
