/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author risca
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UpdateUserData {

    private String password;

    private String name;

}
