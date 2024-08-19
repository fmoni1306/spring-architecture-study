package org.example.springarchitecture.user.service;

import org.example.springarchitecture.mock.FakeMailSender;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CertificationServiceTest {

    @Test
    void 이메일과_컨텐츠가_제대로_만들어져서_보내지는지_테스트한다() {

        FakeMailSender fakeMailSender = new FakeMailSender();
        //given
        CertificationService certificationService = new CertificationService(fakeMailSender);

        //when
        certificationService.send("fmoni1306@gmail.com", 1, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        //then
        assertThat(fakeMailSender.email).isEqualTo("fmoni1306@gmail.com");
        assertThat(fakeMailSender.title).isEqualTo("메일제목");
        assertThat(fakeMailSender.content).isEqualTo("본문 URL ==> 1 == aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }
}
