package com.exam.repo;

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Set<Question> findByQuiz(Quiz quiz);
}
