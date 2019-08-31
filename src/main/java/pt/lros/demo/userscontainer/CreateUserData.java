/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 *
 * @author risca
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class CreateUserData extends UpdateUserData {

    public CreateUserData(String username, String password, String name) {
        super(password, name);
        this.username = username;
    }

    private final String username;

}
