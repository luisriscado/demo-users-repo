/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author risca
 */
@Getter
@Setter
@EqualsAndHashCode
public class User {

    private String username;

    private String name;

    private Date createTimestamp;

    private Date updateTimestamp;

    @JsonIgnore
    private String password;

}
