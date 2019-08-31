/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.lros.demo.userscontainer.check.Check;
import pt.lros.demo.userscontainer.errors.ErrorCheck;
import pt.lros.demo.userscontainer.errors.ValidationException;
import pt.lros.demo.userscontainer.ports.CreateUserPort;
import pt.lros.demo.userscontainer.ports.PasswordHashStrategy;
import pt.lros.demo.userscontainer.ports.UserExistsPort;

@Service
@RequiredArgsConstructor
class CreateUserImpl implements CreateUser {

    private final PasswordHashStrategy hashStrategy;
    private final CreateUserPort createUserPort;
    private final UserExistsPort userExistsPort;
    private final Check check;

    @Override
    public User createUser(CreateUserData newUser) throws ValidationException {
        List<ErrorCheck> validations = new LinkedList<>();
        if (check.checkStringEmpty(newUser.getName())) {
            validations.add(UserErrors.NAME_MANDATORY);
        }
        if (check.checkStringEmpty(newUser.getUsername())) {
            validations.add(UserErrors.USERNAME_MANDATORY);
        } else if (userExistsPort.exists(newUser.getUsername())) {
            validations.add(UserErrors.USERNAME_ALREADY_EXISTS);
        }

        if (check.checkStringEmpty(newUser.getPassword())) {
            validations.add(UserErrors.PASSWORD_MANDATORY);
        } else if (check.checkPasswordStrenght(newUser.getPassword())) {
            validations.add(UserErrors.INVALID_STRENGH_PASSWORD);
        }

        check.checkErrorList(validations); //throws exception if errors exist
        final User createUser = createNewUser(newUser);
        createUserPort.create(createUser);
        return createUser;
    }

    private String getSecuredPassword(String password, Date createdDate) {
        return hashStrategy.hash(password.trim(), createdDate);
    }

    private User createNewUser(CreateUserData newUser) {
        final User createUser = new User();
        final Date timestamp = new Date();
        createUser.setUsername(newUser.getUsername());
        createUser.setCreateTimestamp(timestamp);
        createUser.setUpdateTimestamp(timestamp);
        createUser.setName(newUser.getName());
        createUser.setPassword(getSecuredPassword(newUser.getPassword(), timestamp));
        return createUser;
    }

}
