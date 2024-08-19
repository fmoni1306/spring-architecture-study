package org.example.springarchitecture.mock;

import lombok.Builder;
import org.example.springarchitecture.common.service.port.ClockHolder;
import org.example.springarchitecture.common.service.port.UuidHolder;
import org.example.springarchitecture.post.controller.PostController;
import org.example.springarchitecture.post.controller.PostCreateController;
import org.example.springarchitecture.post.controller.port.PostService;
import org.example.springarchitecture.post.service.PostServiceImpl;
import org.example.springarchitecture.post.service.port.PostRepository;
import org.example.springarchitecture.user.controller.MyInfoController;
import org.example.springarchitecture.user.controller.UserController;
import org.example.springarchitecture.user.controller.UserCreateController;
import org.example.springarchitecture.user.service.CertificationService;
import org.example.springarchitecture.user.service.UserServiceImpl;
import org.example.springarchitecture.user.service.port.MailSender;
import org.example.springarchitecture.user.service.port.UserRepository;

public class TestContainer {

    public final MailSender mailSender;
    public final UserRepository userRepository;
    public final PostRepository postRepository;
    public final PostService postService;
    public final CertificationService certificationService;
    public final UserController userController;
    public final UserCreateController userCreateController;
    public final PostController postController;
    public final PostCreateController postCreateController;
    public final MyInfoController myInfoController;

    @Builder
    public TestContainer(ClockHolder clockHolder, UuidHolder uuidHolder) {
        this.mailSender = new FakeMailSender();
        this.userRepository = new FakeUserRepository();
        this.postRepository = new FakePostRepository();
        this.postService = PostServiceImpl.builder()
                .postRepository(this.postRepository)
                .userRepository(this.userRepository)
                .clockHolder(clockHolder)
                .build();
        this.certificationService = new CertificationService(this.mailSender);
        UserServiceImpl userService = UserServiceImpl.builder()
                .uuidHolder(uuidHolder)
                .clockHolder(clockHolder)
                .userRepository(this.userRepository)
                .certificationService(this.certificationService)
                .build();
        this.userController = UserController.builder()
                .userService(userService)
                .build();
        this.myInfoController = MyInfoController.builder()
                .userService(userService)
                .build();
        this.userCreateController = UserCreateController.builder()
                .userService(userService)
                .build();
        this.postController = PostController.builder()
                .postService(postService)
                .build();
        this.postCreateController = PostCreateController.builder()
                .postService(postService)
                .build();
    }
}
