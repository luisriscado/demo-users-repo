/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer;

import pt.lros.demo.userscontainer.errors.ValidationException;

/**
 *
 * @author risca
 */
public interface UpdateUser {

    User updateUser(UpdateUserData updateData) throws ValidationException;

}
