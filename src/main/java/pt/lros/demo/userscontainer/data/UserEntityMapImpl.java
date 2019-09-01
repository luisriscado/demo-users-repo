/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer.data;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pt.lros.demo.userscontainer.User;

@Component
@RequiredArgsConstructor
class UserEntityMapImpl implements UserEntityMap {

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserEntity fromDomain(User user) {
        return modelMapper.map(user, UserEntity.class);
    }

    @Override
    public User toDomain(UserEntity entity) {
        return modelMapper.map(entity, User.class);
    }
}
