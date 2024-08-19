package org.example.springarchitecture.post.service;

import org.example.springarchitecture.mock.FakePostRepository;
import org.example.springarchitecture.mock.FakeUserRepository;
import org.example.springarchitecture.mock.TestClockHolder;
import org.example.springarchitecture.post.domain.Post;
import org.example.springarchitecture.post.domain.PostCreate;
import org.example.springarchitecture.post.domain.PostUpdate;
import org.example.springarchitecture.user.domain.User;
import org.example.springarchitecture.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostServiceTest {

    private PostServiceImpl postService;

    @BeforeEach
    void init() {
        FakePostRepository fakePostRepository = new FakePostRepository();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();

        this.postService = PostServiceImpl.builder()
                .postRepository(fakePostRepository)
                .userRepository(fakeUserRepository)
                .clockHolder(new TestClockHolder(1678530673958L))
                .build();

        User user1 = User.builder()
                .id(1L)
                .email("fmoni1306@gmail.com")
                .nickname("fmoni1306")
                .address("Seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build();
        User user2 = User.builder()
                .id(2L)
                .email("fmoni2306@gmail.com")
                .nickname("fmoni2306")
                .address("Seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .status(UserStatus.PENDING)
                .lastLoginAt(0L)
                .build();
        fakeUserRepository.save(user1);
        fakeUserRepository.save(user2);
        fakePostRepository.save(Post.builder()
                .id(1L)
                .content("helloworld")
                .createdAt(1678530673958L)
                .modifiedAt(0L)
                .writer(user1)
                .build());
    }

    @Test
    void getById는_존재하는_게시물을_내려준다() {
        //given
        //when
        Post result = postService.getById(1);

        //then
        assertThat(result.getContent()).isEqualTo("helloworld");
        assertThat(result.getWriter().getEmail()).isEqualTo("fmoni1306@gmail.com");
    }


    @Test
    void postCreate_를_이용하여_게시물을_생성할_수_있다() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("foobar")
                .build();

        // when
        Post result = postService.create(postCreate);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("foobar");
        assertThat(result.getCreatedAt()).isGreaterThan(0);
    }

    @Test
    void postUpdate_를_이용하여_게시물을_수정할_수_있다() {
        // given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("hello world :)")
                .build();

        // when
        postService.update(1, postUpdate);

        // then
        Post result= postService.getById(1);
        assertThat(result.getContent()).isEqualTo("hello world :)");
        assertThat(result.getModifiedAt()).isGreaterThan(0);
    }
}
