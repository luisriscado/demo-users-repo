/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer.app;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author risca
 */
@Getter
@RequiredArgsConstructor
class BadRequestResponse<T> {

    private final String type;

    private final T data;

}
