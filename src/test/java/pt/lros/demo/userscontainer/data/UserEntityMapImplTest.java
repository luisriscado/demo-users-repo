/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer.data;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import pt.lros.demo.userscontainer.User;

/**
 *
 * @author risca
 */
public class UserEntityMapImplTest {

    private UserEntityMapImpl userEntityMap;

    public UserEntityMapImplTest() {
    }

    @Before
    public void setup() {
        this.userEntityMap = new UserEntityMapImpl();
    }

    @Test
    public void testFromDomain() {
        User userFrom = new User();

        userFrom.setCreateTimestamp(new Date());
        userFrom.setUpdateTimestamp(new Date());
        userFrom.setName("namE");
        userFrom.setUsername("usr");
        userFrom.setPassword("secr3t");

        UserEntity result = userEntityMap.fromDomain(userFrom);
        assertNotNull(result);
        assertEquals(userFrom.getCreateTimestamp(), result.getCreateTimestamp());
        assertEquals(userFrom.getUpdateTimestamp(), result.getUpdateTimestamp());
        assertEquals(userFrom.getUsername(), result.getUsername());
        assertEquals(userFrom.getPassword(), result.getPassword());
        assertEquals(userFrom.getName(), result.getName());
    }

    @Test
    public void testToDomain() {
        UserEntity userFrom = new UserEntity();

        userFrom.setCreateTimestamp(new Date());
        userFrom.setUpdateTimestamp(new Date());
        userFrom.setName("namE");
        userFrom.setUsername("usr");
        userFrom.setPassword("secr3t");

        User result = userEntityMap.toDomain(userFrom);
        assertNotNull(result);
        assertEquals(userFrom.getCreateTimestamp(), result.getCreateTimestamp());
        assertEquals(userFrom.getUpdateTimestamp(), result.getUpdateTimestamp());
        assertEquals(userFrom.getUsername(), result.getUsername());
        assertEquals(userFrom.getPassword(), result.getPassword());
        assertEquals(userFrom.getName(), result.getName());
    }

}
