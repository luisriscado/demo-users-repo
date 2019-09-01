/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.lros.demo.userscontainer.ports.ListUsersPort;

@Service
@RequiredArgsConstructor
class ListUsersImpl implements ListUsers {

    private final ListUsersPort listUsersPort;

    @Override
    public List<User> users() {
        return listUsersPort.listUsers();
    }

}
