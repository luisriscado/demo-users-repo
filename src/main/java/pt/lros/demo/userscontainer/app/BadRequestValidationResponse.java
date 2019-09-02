/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer.app;

import java.util.List;
import pt.lros.demo.userscontainer.errors.ErrorCheck;

/**
 *
 * @author risca
 */
class BadRequestValidationResponse extends BadRequestResponse<List<ErrorCheck>> {

    public BadRequestValidationResponse(List<ErrorCheck> data) {
        super("validation", data);
    }

}
