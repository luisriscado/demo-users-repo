/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer.check;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import pt.lros.demo.userscontainer.errors.ErrorCheck;
import pt.lros.demo.userscontainer.errors.ValidationException;

/**
 *
 * @author risca
 */
@Component
public class Check {

    public boolean checkStringEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public void checkErrorList(List<ErrorCheck> errorList) throws ValidationException {
        if (null != errorList && !errorList.isEmpty()) {
            //transfor list to unmodifiable list
            throw new ValidationException(errorList.stream().collect(Collectors.toList()));
        }
    }

    public boolean checkPasswordStrenght(String password) {
        return password != null && password.trim().length() > 6;
    }

}
