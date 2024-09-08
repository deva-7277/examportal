package com.exam.controller;

import com.exam.DTO.TopQuizResult;
import com.exam.helper.QuizNotFoundException;
import com.exam.helper.UserFoundException;
import com.exam.service.TopQuizResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/top-results")
@CrossOrigin("*")
public class TopQuizResultController {

    @Autowired
    TopQuizResultService topQuizResultService;

    @GetMapping("/quiz/{quizId}")
    public List<TopQuizResult> getTopScoringStudentsOfQuiz(@PathVariable Long quizId, @RequestParam(defaultValue = "3") int pageSize, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "3") int limit) throws UserFoundException, QuizNotFoundException {
        return topQuizResultService.getTopScoringStudentsOfQuiz(pageNumber, pageSize, quizId, limit);
    }
}
