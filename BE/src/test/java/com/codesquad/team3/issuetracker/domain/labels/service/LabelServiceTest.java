package com.codesquad.team3.issuetracker.domain.labels.service;

import com.codesquad.team3.issuetracker.domain.labels.entity.Label;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class LabelServiceTest {

//    @Autowired
//    LabelService service;
//
//    @DisplayName("라벨을 생성하고서 찾아온다.")
//    @Test
//    void test1() {
//
//        LocalDateTime time = LocalDateTime.of(2024,05,13,0,0);
//
//        Label label = new Label("abc", "aa", "blue", time);
//        service.create(label);
//
//
//        Label findLabel = service.getLabel("abc");
//
//        assertThat(findLabel.getTitle()).isEqualTo("abc");
//        assertThat(findLabel.getDescription()).isEqualTo("aa");
//        assertThat(findLabel.getColor()).isEqualTo("blue");
//        assertThat(findLabel.getCreateTime()).isEqualTo(time);
//
//    }
//
//    @DisplayName("라벨을 생성하고 삭제한다.")
//    @Test
//    void test2() {
//
//        LocalDateTime time = LocalDateTime.of(2024,05,13,0,0);
//
//
//        Label label = new Label("abc", "aa", "blue", time);
//        service.create(label);
//
//        Label findLabel = service.getLabel("abc");
//
//        service.delete("abc");
//        assertThatThrownBy(()->service.getLabel("abc")).isInstanceOf(IllegalArgumentException.class);
//
//    }
//
//
//    @DisplayName("라벨 내용을 수정한다.")
//    @Test
//    void test3() {
//        LocalDateTime time = LocalDateTime.of(2024,05,13,0,0);
//
//
//        Label label = new Label("abc", "aa", "blue", time);
//
//
//
//    }

}