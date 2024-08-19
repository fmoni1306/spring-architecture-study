package org.example.springarchitecture.user.controller.port;

import org.example.springarchitecture.user.domain.User;
import org.example.springarchitecture.user.domain.UserCreate;
import org.example.springarchitecture.user.domain.UserUpdate;

public interface UserService {
    User getByEmail(String email);

    User getById(long id);

    User create(UserCreate userCreate);

    User update(long id, UserUpdate userUpdate);

    void login(long id);

    void verify(long id, String certificationCode);
}
