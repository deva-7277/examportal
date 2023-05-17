package com.exam.service.impl;

import com.exam.DTO.ResultResponseDto;
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
    public ResultResponseDto getResult(Long id) {
        Result result = this.resultRepository.findById(id).get();
        Quiz quiz = this.quizRepository.findById(result.getQuizId()).get();
        User user = this.userRepository.findById(result.getStudentId()).get();
        String quizName = quiz.getTitle();
        String category = quiz.getCategory().getTitle();
        String studentName = user.getFirstName()+" "+user.getLastName();
        ResultResponseDto response = new ResultResponseDto();

//        Converting Result to ResultResponseDto
        response.setId(result.getId());
        response.setAttempted(result.getAttempted());
        response.setGrade(result.getGrade());
        response.setCorrect(result.getCorrect());
        response.setIncorrect(result.getIncorrect());
        response.setCategory(category);
        response.setQuiz(quizName);
        response.setRemark(result.getRemark());
        response.setExamTime(result.getExamTime());
        response.setExamDate(result.getExamDate());
        response.setMaxMarks(result.getMaxMarks());
        response.setStudentId(result.getStudentId());
        response.setStudentName(studentName);
        response.setMarksObtained(result.getMarksObtained());
        response.setStatus(result.getStatus());
        response.setPhone(user.getPhone());
        response.setEmail(user.getEmail());
        response.setTotalQuestions(quiz.getNumberOfQuestions());
        return response;
    }

    /*
     * This service is for getting all the Results of a quiz
     * This Service handles both the User and Admin
     * If the request is from user, it will show only the Results of a single quiz for that user
     * If the request is for Admin, it will show all the Results for a Quiz of all users
     * */
    @Override

    public List<ResultResponseDto> getAllResultsForAQuiz(Long quizId, Long userId) {
        User user = userRepository.findById(userId).get();
        log.info("User fetched successfully");
        Set<UserRole> roleSet = user.getUserRoles();

        Quiz quiz = this.quizRepository.findById(quizId).get();
        String quizName = quiz.getTitle();
        String category = quiz.getCategory().getTitle();

//        roleSet.stream().forEach(r -> System.out.println(r.getRole().getRoleId()));
        Boolean isAdmin = roleSet.stream().anyMatch(r-> r.getRole().getRoleId()==44);
        log.info("Is user admin? "+isAdmin);
        if(isAdmin){
            log.info("User is a Admin: "+user.getUsername());
            List<Result> allResultsOfAQuiz = this.resultRepository.findAllResults(quizId);
            log.info("Results fetched successfully");
//            allResultsOfAQuiz.stream().forEach(s -> System.out.println(s.getQuizId()));

//            Result to ResultResponseDto conversion
            List<ResultResponseDto> response = allResultsOfAQuiz.stream().map(result -> new ResultResponseDto(
                    result.getId(),
                    category,
                    quizName,
                    result.getStudentId(),
                    this.userRepository.findById(result.getStudentId()).get().getFirstName()+" "+ this.userRepository.findById(result.getStudentId()).get().getLastName(),
                    result.getAttempted(),
                    result.getCorrect(),
                    result.getIncorrect(),
                    result.getMarksObtained(),
                    result.getGrade(),
                    result.getRemark(),
                    result.getMaxMarks(),
                    result.getExamDate(),
                    result.getExamTime(),
                    result.getStatus()
            )).collect(Collectors.toList());
            return response;
        }
        else{
            log.info("User is a Normal User: "+user.getUsername());
            List<Result> allResultsOfAQuizOfAUser = this.resultRepository.findAllByQuizIdAndUserId(quizId,userId);
            log.info("Results fetched successfully");
            List<ResultResponseDto> response = allResultsOfAQuizOfAUser.stream().map(result -> new ResultResponseDto(
                    result.getId(),
                    category,
                    quizName,
                    result.getStudentId(),
                    this.userRepository.findById(result.getStudentId()).get().getFirstName()+ this.userRepository.findById(result.getStudentId()).get().getLastName(),
                    result.getAttempted(),
                    result.getCorrect(),
                    result.getIncorrect(),
                    result.getMarksObtained(),
                    result.getGrade(),
                    result.getRemark(),
                    result.getMaxMarks(),
                    result.getExamDate(),
                    result.getExamTime(),
                    result.getStatus()
            )).collect(Collectors.toList());
            return response;
        }

    }

    @Override

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
