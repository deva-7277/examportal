package com.exam.service.impl;

import com.exam.DTO.TopQuizResult;
import com.exam.helper.QuizNotFoundException;
import com.exam.helper.UserFoundException;
import com.exam.model.User;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.Result;
import com.exam.repo.QuizRepository;
import com.exam.repo.ResultRepository;
import com.exam.repo.UserRepository;
import com.exam.service.ResultService;
import com.exam.service.TopQuizResultService;
import com.exam.utils.RankMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TopQuizResultServiceImpl implements TopQuizResultService {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private ResultServiceImpl resultService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<TopQuizResult> getTopScoringStudentsOfQuiz(int pageNumber, int pageSize, Long quizId, int limit) throws UserFoundException, QuizNotFoundException {
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        List<TopQuizResult> topQuizResults = new ArrayList<>();
        List<Result> resultList;
        if(quiz.isPresent()){
            resultList = resultService.getResultsByQuizId(quizId, "marksObtained", "DESC", limit);
            Map<Integer, Integer> markRankMap = RankMapper.getMarksToRankMap(resultList);
            markRankMap.entrySet().forEach(System.out::println);
            for(Result result : resultList){
                TopQuizResult topQuizResult = new TopQuizResult();
                topQuizResult.setQuizName(quiz.get().getTitle());
                Optional<User> user = userRepository.findById(result.getStudentId());
                if(user.isPresent()) {
                    topQuizResult.setStudentName(user.get().getFirstName() + " " + user.get().getLastName());
                }
                else {
                    throw new UserFoundException("No Student present for this Id or data is incorrect, id"+result.getStudentId());
                }
                topQuizResult.setMaxMarks(result.getMaxMarks());
                topQuizResult.setRank(markRankMap.get(result.getMarksObtained()));
                topQuizResult.setMarksObtained(result.getMarksObtained());
                topQuizResults.add(topQuizResult);
            }
        }
        else{
            throw new QuizNotFoundException("No Quiz found for this id");
        }
        return topQuizResults;
    }
}
