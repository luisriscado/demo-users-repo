/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer.data;

import pt.lros.demo.userscontainer.User;

/**
 *
 * @author risca
 */
public interface UserEntityMap {

    UserEntity fromDomain(User user);

    User toDomain(UserEntity entity);
}
