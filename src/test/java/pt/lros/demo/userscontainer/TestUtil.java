/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer;

import java.util.Collection;
import org.junit.Assert;
import pt.lros.demo.userscontainer.errors.ErrorCheck;
import pt.lros.demo.userscontainer.errors.ValidationException;

/**
 *
 * @author risca
 */
public class TestUtil {

    public static void assertValidationExceptionContains(ValidationException vEx, Collection<ErrorCheck> errors) {
        Assert.assertTrue(vEx != null
                && errors != null
                && vEx.getErrors().containsAll(errors));
    }

}
