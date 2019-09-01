/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer.data.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pt.lros.demo.userscontainer.User;
import pt.lros.demo.userscontainer.data.UserEntityMap;
import pt.lros.demo.userscontainer.data.UserRepository;
import pt.lros.demo.userscontainer.ports.UpdateUserPort;

/**
 *
 * @author risca
 */
@Component
@RequiredArgsConstructor
class UpdateUserAdapter implements UpdateUserPort {

    private final UserRepository userRepository;
    private final UserEntityMap userEntityMap;

    @Override
    public void save(User u) {
        userRepository.save(userEntityMap.fromDomain(u));
    }

}
