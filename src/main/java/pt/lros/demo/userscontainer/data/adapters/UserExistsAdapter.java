/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer.data.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pt.lros.demo.userscontainer.data.UserRepository;
import pt.lros.demo.userscontainer.ports.UserExistsPort;

/**
 *
 * @author risca
 */
@RequiredArgsConstructor
@Component
class UserExistsAdapter implements UserExistsPort {

    private final UserRepository userRepository;

    @Override
    public boolean exists(String username) {
        if (username == null) {
            return false;
        }

        return userRepository.findById(username.toUpperCase()).isPresent();
    }

}
