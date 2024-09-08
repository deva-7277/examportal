package com.exam.service;

import com.exam.DTO.TopQuizResult;
import com.exam.helper.QuizNotFoundException;
import com.exam.helper.UserFoundException;

import java.util.List;

public interface TopQuizResultService {
    public List<TopQuizResult> getTopScoringStudentsOfQuiz(int pageNumber, int pageSize, Long quizId, int limit) throws UserFoundException, QuizNotFoundException;
}
