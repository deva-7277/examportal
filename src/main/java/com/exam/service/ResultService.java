package com.exam.service;

import com.exam.DTO.ResultResponseDto;
import com.exam.DTO.TopQuizResult;
import com.exam.model.exam.Result;

import java.util.List;
import java.util.Set;

public interface ResultService {
    public Result saveResult(Result result);

    ResultResponseDto getResult(Long id);

    List<ResultResponseDto> getAllResultsForAQuiz(Long quizId, Long userId);

    List<TopQuizResult> getAllResults(Long quizId);
}
