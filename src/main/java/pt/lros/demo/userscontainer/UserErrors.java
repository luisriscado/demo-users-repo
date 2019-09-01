/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pt.lros.demo.userscontainer.errors.ErrorCheck;
import pt.lros.demo.userscontainer.errors.ErrorType;

/**
 *
 * @author risca
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserErrors {

    public static final ErrorCheck USERNAME_ALREADY_EXISTS = new ErrorCheck(ErrorType.ERR, "username_already_esxists");

    public static final ErrorCheck NAME_MANDATORY = new ErrorCheck(ErrorType.ERR, "name_mandatory");

    public static final ErrorCheck USERNAME_MANDATORY = new ErrorCheck(ErrorType.ERR, "username_mandatory");

    public static final ErrorCheck PASSWORD_MANDATORY = new ErrorCheck(ErrorType.ERR, "password_mandatory");

    public static final ErrorCheck INVALID_STRENGH_PASSWORD = new ErrorCheck(ErrorType.ERR, "invalid_strengh_password");

    public static final ErrorCheck USER_DOS_NOT_EXIST = new ErrorCheck(ErrorType.ERR, "user_dos_not_exist");

    public static final ErrorCheck USER_DELETE_NOT_ALLOWED = new ErrorCheck(ErrorType.ERR, "user_delete_not_allowed");
}
