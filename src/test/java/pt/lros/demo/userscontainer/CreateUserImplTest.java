/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer;

import java.util.Arrays;
import java.util.Date;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
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
    public void testCreateUserMandatoryPassword() {
        CreateUserData c = new CreateUserData("USR", null, "NAME");
        try {
            this.createUser.createUser(c);
            fail("Validation exception not throwed");
        } catch (ValidationException ex) {
            TestUtil.assertValidationExceptionContains(ex, Arrays.asList(UserErrors.PASSWORD_MANDATORY));
        }
    }

}
