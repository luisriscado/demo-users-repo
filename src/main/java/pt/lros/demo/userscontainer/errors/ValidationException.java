/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer.errors;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author risca
 */
@RequiredArgsConstructor
@Getter
public class ValidationException extends Exception {

    private final List<ErrorCheck> errors;
}
