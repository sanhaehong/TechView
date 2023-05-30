package com.sanhaehong.project.techview;

import com.sanhaehong.project.techview.domain.answer.Answer;
import com.sanhaehong.project.techview.domain.mockexam.MockExam;
import com.sanhaehong.project.techview.domain.mockexam.MockExamQuestion;
import com.sanhaehong.project.techview.domain.question.Category;
import com.sanhaehong.project.techview.domain.question.Question;
import com.sanhaehong.project.techview.domain.user.Role;
import com.sanhaehong.project.techview.domain.user.User;
import com.sanhaehong.project.techview.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(value = {"local"})
@RequiredArgsConstructor
public class LocalInit {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final MockExamRepository mockExamRepository;
    private final MockExamQuestionRepository mockExamQuestionRepository;

    @PostConstruct
    public void init() {
        User admin = User.builder().name("홍산해").email("sanhaehong@gmail.com").picture("https://cdn-icons-png.flaticon.com/128/236/236831.png").role(Role.ADMIN).build();
        User user = User.builder().name("영희").email("user2@gmail.com").picture("https://cdn-icons-png.flaticon.com/128/201/201634.png").role(Role.USER).build();
        userRepository.save(admin);
        userRepository.save(user);

        Question question1 = Question.builder().category(Category.ALGORITHM).content("가장 기억에 남는 정렬 알고리즘 1개를 골라 설명해보세요").build();
        Question question2 = Question.builder().category(Category.NETWORK).content("www.google.com 에 접속할 때 일어나는 일을 설명해보세요").build();
        questionRepository.save(question1);
        questionRepository.save(question2);
        questionRepository.save(Question.builder().category(Category.DATABASE).content("ACID가 뭔지 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.DATA_STRUCTURE).content("배열과 List의 차이점을 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.SECURITY).content("OAuth가 뭔지 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.JAVA).content("equals()와 ==는 어떤 차이점이 있는지 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.JAVA).content("JAVA의 GC가 뭔지 설명하고, 동작 방식에 대해 간단히 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.OPERATION_SYSTEM).content("캐시의 지역성이 뭔지 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.ALGORITHM).content("시간 복잡도와 공간 복잡도의 차이점을 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.NETWORK).content("TCP와 UDP의 차이점을 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.DATABASE).content("NoSQL 데이터베이스의 장단점을 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.DATA_STRUCTURE).content("Stack과 Queue의 차이점을 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.SECURITY).content("XSS 공격이 무엇인지 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.ALGORITHM).content("Dynamic Programming 이란 무엇인지 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.NETWORK).content("HTTP와 HTTPS의 차이점을 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.DATABASE).content("트랜잭션이란 무엇인가요?").build());
        questionRepository.save(Question.builder().category(Category.DATA_STRUCTURE).content("해시 테이블의 작동 원리를 설명해보세요").build());
        questionRepository.save(Question.builder().category(Category.SECURITY).content("SQL 인젝션 공격이 무엇인지 설명해보세요").build());

        answerRepository.save(Answer.builder().writer(admin).question(question1)
                .content("Heap 정렬은 최대/최소 힙 트리를 사용하여 요소를 정렬하는 알고리즘입니다.\n" +
                "n개의 요소들로 힙 트리를 구성한 후, 요소를 순차적으로 제거하여 정렬된 배열을 생성합니다.\n" +
                        "힙 정렬의 시간 복잡도는 최상/최악의 경우 모두 O(n log n) 입니다.").build());
        answerRepository.save(Answer.builder().writer(user).question(question1)
                .content("Quick 정렬은 분할 정복 방식의 정렬 알고리즘입니다.\n" +
                        "배열에서 pivot 요소를 선택하고 pivot보다 작은 요소와 큰 요소로 배열을 분할합니다.\n" +
                        "이후 분할된 하위 배열에 대해 동일한 방법을 재귀적으로 적용하여 정렬합니다.\n" +
                        "Quick sort는 시간 복잡도는 평균적으로 O(n log n)이며, 최악의 경우 O(n^2) 입니다.").build());

        MockExam mockExam = MockExam.builder().maker(admin).title("기본 모의고사").information("기본 모의고사 입니다.").build();
        mockExamRepository.save(mockExam);
        MockExamQuestion mockExamQuestion1 = MockExamQuestion.builder().mockexam(mockExam).question(question1).build();
        MockExamQuestion mockExamQuestion2 = MockExamQuestion.builder().mockexam(mockExam).question(question2).build();
        mockExamQuestionRepository.save(mockExamQuestion1);
        mockExamQuestionRepository.save(mockExamQuestion2);

    }
}
