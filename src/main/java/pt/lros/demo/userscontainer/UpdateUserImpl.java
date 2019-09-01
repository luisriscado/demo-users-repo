/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pt.lros.demo.userscontainer.check.Check;
import pt.lros.demo.userscontainer.errors.ErrorCheck;
import pt.lros.demo.userscontainer.errors.ValidationException;
import pt.lros.demo.userscontainer.ports.PasswordHashStrategy;
import pt.lros.demo.userscontainer.ports.ReadUserPort;
import pt.lros.demo.userscontainer.ports.UpdateUserPort;

@Service
@RequiredArgsConstructor
class UpdateUserImpl implements UpdateUser {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserImpl.class);

    private final ReadUserPort readUserPort;

    private final UpdateUserPort updateUserPort;

    private final PasswordHashStrategy passwordHashStrategy;

    private final Check check;

    @Override
    public User updateUser(String username, UpdateUserData updateData) throws ValidationException {
        final List<ErrorCheck> validations = new LinkedList<>();

        User update = null;
        if (check.checkStringEmpty(username)) {
            validations.add(UserErrors.USER_DOS_NOT_EXIST);
        } else {
            Optional<User> user = readUserPort.getUser(username);
            if (!user.isPresent()) {
                validations.add(UserErrors.USER_DOS_NOT_EXIST);
            } else {
                update = user.get();
                if (check.checkStringEmpty(updateData.getName())) {
                    validations.add(UserErrors.NAME_MANDATORY);
                } else {
                    update.setName(updateData.getName());
                }

                if (!check.checkStringEmpty(updateData.getPassword())) {
                    log.info("New password provided");
                    if (!check.checkPasswordStrenghtValid(updateData.getPassword())) {
                        validations.add(UserErrors.INVALID_STRENGH_PASSWORD);
                    } else {
                        final String newPassword = passwordHashStrategy.hash(updateData.getPassword().trim(),
                                update.getCreateTimestamp());
                        log.info("Changing password");
                        update.setPassword(newPassword);
                    }
                }
            }
        }

        check.checkErrorList(validations);

        updateUserPort.save(update);

        return update;
    }

}
