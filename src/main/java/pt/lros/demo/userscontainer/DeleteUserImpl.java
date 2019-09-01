/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.lros.demo.userscontainer.check.Check;
import pt.lros.demo.userscontainer.errors.ErrorCheck;
import pt.lros.demo.userscontainer.errors.ValidationException;
import pt.lros.demo.userscontainer.ports.DeleteUserPort;
import pt.lros.demo.userscontainer.ports.ReadUserPort;

@Service
@RequiredArgsConstructor
class DeleteUserImpl implements DeleteUser {

    private final Check check;

    private final ReadUserPort readUserPort;

    private final DeleteUserPort deleteUserPort;

    private static final Set<String> DO_NOT_DELETE = Stream.of("itusr").collect(Collectors.toSet());

    @Override
    public void deleteUser(String username) throws ValidationException {
        List<ErrorCheck> validations = new LinkedList<>();
        User delete = null;
        if (check.checkStringEmpty(username)) {
            validations.add(UserErrors.USER_DOS_NOT_EXIST);
        } else {

            Optional<User> user = readUserPort.getUser(username);

            if (!user.isPresent()) {
                validations.add(UserErrors.USER_DOS_NOT_EXIST);
            } else if (DO_NOT_DELETE.contains(username.toLowerCase())) {
                validations.add(UserErrors.USER_DELETE_NOT_ALLOWED);
            } else {
                delete = user.get();
            }
        }
        check.checkErrorList(validations);

        deleteUserPort.delete(delete);

    }

}
