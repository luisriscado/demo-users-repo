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
import pt.lros.demo.userscontainer.data.UserEntityMap;
import pt.lros.demo.userscontainer.data.UserRepository;
import pt.lros.demo.userscontainer.ports.ReadUserPort;

/**
 *
 * @author risca
 */
@Component
@RequiredArgsConstructor
class ReadUserPortAdapter implements ReadUserPort {

    private final UserRepository userRepository;

    private final UserEntityMap userEntityMap;

    @Override
    public Optional<User> getUser(String username) {
        Optional<UserEntity> entity = userRepository.findById(username.toUpperCase());

        return entity.map(userEntityMap::toDomain);
    }

}
