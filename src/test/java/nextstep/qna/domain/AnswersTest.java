package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AnswersTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents2");
    public static final Answer A3 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents3");

    @Test
    void add() {
        Answers answers = new Answers();
        answers.add(A1);
        answers.add(A2);

        assertThat(answers.getAnswers()).containsExactly(A1, A2);
    }

    @Test
    void deleteAll_success() throws CannotDeleteException {
        Answers answers = new Answers();
        answers.add(A1);
        answers.add(A2);

        answers.deleteAll(NsUserTest.JAVAJIGI);

        assertThat(A1.isDeleted()).isTrue();
        assertThat(A2.isDeleted()).isTrue();
    }

    @Test
    void deleteAll_failure() {
        Answers answers = new Answers();
        answers.add(A1);
        answers.add(A3);

        assertThatThrownBy(() -> answers.deleteAll(NsUserTest.JAVAJIGI))
            .isInstanceOf(CannotDeleteException.class);
    }

    @Test
    void toDeleteHistories() throws CannotDeleteException {
        Answers answers = new Answers();
        answers.add(A1);
        answers.add(A2);

        answers.deleteAll(NsUserTest.JAVAJIGI);
        List<DeleteHistory> deleteHistories = answers.toDeleteHistories();

        assertThat(deleteHistories).hasSize(2);
    }
}
