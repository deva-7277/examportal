package com.exam.controller;

import com.exam.DTO.TopQuizResult;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.Result;
import com.exam.repo.QuizRepository;
import com.exam.service.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/result")
public class ResultController {

    Logger log = LoggerFactory.getLogger(ResultController.class);
    @Autowired
    private ResultService resultService;

    @Autowired
    private QuizRepository quizRepository;

    @PostMapping("/save-result")
    public Result saveResult(@RequestBody Result result){
        log.info("save result from Result Controller called");
        Long quizId = result.getQuizId();
        Quiz quiz = quizRepository.findById(quizId).get();
        Long categoryId = quiz.getCategory().getCid();
        String maxMarks = quiz.getMaxMarks();
        result.setCategoryId(categoryId);
        result.setMaxMarks(maxMarks);
        return this.resultService.saveResult(result);
    }

    @GetMapping("/get-result")
    public Result getResult(@RequestParam Long id){
        log.info("getResult from ResultController called");
        return this.resultService.getResult(id);
    }
    /*
    * This API is for getting all the Results of a quiz
    * This API handles both the User and Admin
    * If the request is from user, it will show only the Results of a single quiz for that user
    * If the request is for Admin, it will show all the Results for a Quiz of all users
    * */
    @GetMapping("/get-all-results-of-a-quiz")
    public Set<Result> getAllResultsForAQuiz(@RequestParam Long quizId ,
                                             @RequestParam Long userId,
                                             @RequestParam(value = "sortBy", defaultValue ="id", required = false) String sortBy,
                                             @RequestParam(value = "sortBy", defaultValue ="desc", required = false) String sortDir){
        log.info("getAllResultsForAQuiz from RestController called");
        return this.resultService.getAllResultsForAQuiz(quizId, userId, sortBy, sortDir);
    }

    @GetMapping("/get-top-3-results-in-quiz")
    public List<TopQuizResult> getTop3Results(@RequestParam Long quizId){
        log.info("getTop3Results called from ResultController");
        List<TopQuizResult> allResults = this.resultService.getAllResults(quizId);
        return allResults;
    }
}
