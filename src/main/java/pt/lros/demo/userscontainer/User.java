/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer;

import java.util.Date;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author risca
 */
@Getter
@Setter
public class User {

    @Id
    private String username;

    private String password;

    private String name;

    private Date createTimestamp;

    private Date updateTimestamp;

}
