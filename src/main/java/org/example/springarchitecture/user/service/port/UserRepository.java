package org.example.springarchitecture.user.service.port;

import org.example.springarchitecture.user.domain.User;
import org.example.springarchitecture.user.domain.UserStatus;

import java.util.Optional;

public interface UserRepository {

    User getById(Long id);

    Optional<User> findById(Long id);

    Optional<User> findByIdAndStatus(long id, UserStatus userStatus);

    Optional<User> findByEmailAndStatus(String email, UserStatus userStatus);

    User save(User user);
}
