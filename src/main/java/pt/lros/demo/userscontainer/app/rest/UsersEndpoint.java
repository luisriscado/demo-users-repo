/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer.app.rest;

import java.util.List;
import java.util.Optional;
import javax.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pt.lros.demo.userscontainer.CreateUser;
import pt.lros.demo.userscontainer.CreateUserData;
import pt.lros.demo.userscontainer.DeleteUser;
import pt.lros.demo.userscontainer.ListUsers;
import pt.lros.demo.userscontainer.ReadUser;
import pt.lros.demo.userscontainer.UpdateUser;
import pt.lros.demo.userscontainer.UpdateUserData;
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
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.GET,
    RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.PUT})
public class UsersEndpoint {

    private final CreateUser createUser;
    private final ReadUser readUser;
    private final UpdateUser updateUser;
    private final ListUsers listUsers;
    private final DeleteUser deleteUser;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<User> list() {
        return listUsers.users();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User create(@RequestBody CreateUserData createUserData) throws ValidationException {
        return createUser.createUser(createUserData);
    }

    @GetMapping(value = "/{user}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Optional<User> read(@PathParam("user") String user) throws ValidationException {
        return readUser.getUser(user);
    }

    @PutMapping(value = "/{user}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User update(@PathParam("user") String user,
            @RequestBody UpdateUserData updateUserData) throws ValidationException {
        return updateUser.updateUser(user, updateUserData);
    }

    @DeleteMapping("/{user}")
    public ResponseEntity delete(@PathParam("user") String user) throws ValidationException {
        deleteUser.deleteUser(user);
        return ResponseEntity.ok().build();
    }
}
