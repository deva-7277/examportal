package com.exam.DTO;

import com.exam.model.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class TopQuizResult implements Serializable {

    private int rank;
    private String studentName;
    private String quizName;
    private Long marksObtained;
    private String maxMarks;

    public TopQuizResult(TopQuizResultBuilder builder){

    }

    public TopQuizResult(int rank, String studentName, String quizName, Long marksObtained, String maxMarks) {
        this.rank = rank;
        this.studentName = studentName;
        this.quizName = quizName;
        this.marksObtained = marksObtained;
        this.maxMarks = maxMarks;
    }

    @Data
    private static class TopQuizResultBuilder{
        private int rank;
        private String studentName;
        private String quizName;
        private Long marksObtained;
        private Long maxMarks;
    }
}
