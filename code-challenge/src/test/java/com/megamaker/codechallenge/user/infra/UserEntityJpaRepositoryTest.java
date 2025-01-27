package com.megamaker.codechallenge.user.infra;

import com.megamaker.codechallenge.user.infra.UserEntity;
import com.megamaker.codechallenge.user.infra.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SqlGroup({
        @Sql(value = "/sql/user-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/user-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@DataJpaTest
class UserEntityJpaRepositoryTest {
    @Autowired
    UserJpaRepository userJpaRepository;

    @Test
    void providerId로_회원_조회_성공() {
        // given
        String providerId = "1";

        // when
        UserEntity foundUserEntity = userJpaRepository.findByProviderId(providerId).orElseThrow();

        // then
        assertThat(foundUserEntity.getProviderId()).isEqualTo(providerId);
    }

    @Test
    void score_순위_상위_n명_조회_성공() {
        // given
        int n = 3;

        // when
        List<UserEntity> foundUserEntities = userJpaRepository.findTopNByOrderByScoreDesc(n);

        // then
        for (int i = 0; i < foundUserEntities.size() - 1; i++) {
            assertThat(foundUserEntities.get(i).getScore()).isGreaterThan(foundUserEntities.get(i + 1).getScore());
        }
    }

    @Test
    void score_순위_상위_n명_조회_n명보다_적을_때_해당_인원만큼_조회() {
        // given
        int n = Integer.MAX_VALUE;

        // when
        List<UserEntity> foundUserEntities = userJpaRepository.findTopNByOrderByScoreDesc(n);

        // then
        assertThat(foundUserEntities.size()).isLessThan(n);
        for (int i = 0; i < foundUserEntities.size() - 1; i++) {
            assertThat(foundUserEntities.get(i).getScore()).isGreaterThan(foundUserEntities.get(i + 1).getScore());
        }
    }

    @Test
    void providerId와_problemId로_문제_풀이_내역_조회_성공() {
        // given
        String providerId = "6";
        Long problemId = 1L;

        // when
        String answer = userJpaRepository.findAnswerByProviderIdAndProblemId(providerId, problemId).orElseThrow();

        // then
        assertThat(answer).isEqualTo("answer1");
    }
}