/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer.data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author risca
 */
@Entity
@Table(name = "USER")
@Getter
@Setter
public class UserEntity implements Serializable {

    @Id
    @Column(name = "USER_NAME")
    private String username;

    @Column(name = "PWD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "CREATED")
    private Date createTimestamp;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "UPDATED")
    private Date updateTimestamp;
}
