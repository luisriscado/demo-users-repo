/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer.app.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pt.lros.demo.userscontainer.CreateUser;
import pt.lros.demo.userscontainer.CreateUserData;
import pt.lros.demo.userscontainer.User;
import pt.lros.demo.userscontainer.errors.ValidationException;

/**
 *
 * @author risca
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Transactional
public class UsersEndpoint {

    final CreateUser createUser;

    @PostMapping
    @ResponseBody
    private User create(CreateUserData createUserData) throws ValidationException {
        return createUser.createUser(createUserData);
    }

}
