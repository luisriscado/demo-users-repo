/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer.data.adapters;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pt.lros.demo.userscontainer.User;
import pt.lros.demo.userscontainer.data.UserEntity;
import pt.lros.demo.userscontainer.data.UserRepository;
import pt.lros.demo.userscontainer.ports.ReadUserPort;

/**
 *
 * @author risca
 */
@Component
@RequiredArgsConstructor
class ReadUserPortAdapter implements ReadUserPort {

    final UserRepository userRepository;

    @Override
    public Optional<User> getUser(String username) {
        Optional<UserEntity> entity = userRepository.findById(username);

        return Optional.empty();
    }

}
