package com.exam.service;

import com.exam.DTO.TopQuizResult;
import com.exam.model.exam.Result;

import java.util.List;
import java.util.Set;

public interface ResultService {
    public Result saveResult(Result result);

    Result getResult(Long id);

    Set<Result> getAllResultsForAQuiz(Long quizId, Long userId, String sortBy, String sortDir);

    List<TopQuizResult> getAllResults(Long quizId);
}
