package com.exam.service.impl;

import com.exam.DTO.TopQuizResult;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.Result;
import com.exam.repo.QuizRepository;
import com.exam.repo.ResultRepository;
import com.exam.repo.UserRepository;
import com.exam.service.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ResultServiceImpl implements ResultService {

    Logger log = LoggerFactory.getLogger(ResultServiceImpl.class);
    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public Result saveResult(Result result) {
        return this.resultRepository.save(result);
    }

    @Override
    public Result getResult(Long id) {
        return this.resultRepository.findById(id).get();
    }

    /*
     * This service is for getting all the Results of a quiz
     * This Service handles both the User and Admin
     * If the request is from user, it will show only the Results of a single quiz for that user
     * If the request is for Admin, it will show all the Results for a Quiz of all users
     * */
    @Override
    @Cacheable(cacheNames = "results" , key = "'#allResultsForAQuiz'")
    public Set<Result> getAllResultsForAQuiz(Long quizId, Long userId, String sortBy, String sortDir) {
        User user = userRepository.findById(userId).get();
        log.info("User fetched successfully");
        Set<UserRole> roleSet = user.getUserRoles();
//        roleSet.stream().forEach(r -> System.out.println(r.getRole().getRoleId()));
        Boolean isAdmin = roleSet.stream().anyMatch(r-> r.getRole().getRoleId()==44);
        log.info("Is user admin? "+isAdmin);
        if(isAdmin){
            log.info("User is a Admin: "+user.getUsername());
            Set<Result> allResultsOfAQuiz = this.resultRepository.findAllByQuizId(quizId, sortBy, sortDir);
            log.info("Results fetched successfully");
//            allResultsOfAQuiz.stream().forEach(s -> System.out.println(s.getQuizId()));
            return allResultsOfAQuiz;
        }
        else{
            log.info("User is a Normal User: "+user.getUsername());
            Set<Result> allResultsOfAQuizOfAUser = this.resultRepository.findAllByQuizIdAndUserId(quizId,userId);
            log.info("Results fetched successfully");
            return allResultsOfAQuizOfAUser;
        }

    }

    @Override
    @Cacheable(cacheNames = "results" , key = "'#top3Results'")
    public List<TopQuizResult> getAllResults(Long quizId) {

        AtomicInteger rank = new AtomicInteger(1);
        log.info("getAllResults from ResultServiceImpl called");
        Quiz quiz = this.quizRepository.findById(quizId).get();
        String quizName = quiz.getTitle();
        String maxMarks = quiz.getMaxMarks();
        List<Result> top3Results =  this.resultRepository.findTop3(quizId);
        List<TopQuizResult> top3ResultsResponse=  top3Results.stream().map(entity ->new TopQuizResult(
                rank.getAndIncrement(),
                this.userRepository.findById(entity.getStudentId()).get().getFirstName(),
                quizName,
                entity.getMarksObtained(),
                maxMarks
                )).collect(Collectors.toList());

        return top3ResultsResponse;
    }
}
