/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer;

import java.util.Optional;

/**
 *
 * @author risca
 */
public interface ReadUser {

    Optional<User> getUser(String username);
}
