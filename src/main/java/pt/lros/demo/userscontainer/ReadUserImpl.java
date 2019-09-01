/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.lros.demo.userscontainer.ports.ReadUserPort;

@Service
@RequiredArgsConstructor
class ReadUserImpl implements ReadUser {

    private final ReadUserPort readUserPort;

    @Override
    public Optional<User> getUser(String username) {
        return readUserPort.getUser(username);
    }

}
