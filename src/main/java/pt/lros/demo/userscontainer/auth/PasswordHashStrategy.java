/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer.auth;

import java.util.Date;

/**
 *
 * @author risca
 */
public interface PasswordHashStrategy {

    String hash(String password, Date createdDate);

}
