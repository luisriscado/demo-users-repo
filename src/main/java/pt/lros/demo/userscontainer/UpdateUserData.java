/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author risca
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class UpdateUserData {

    private final String password;

    private final String name;

}
