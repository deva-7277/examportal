package com.exam.DTO;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
public class ResultResponseDto implements Serializable {

    private Long id;
    private String category;
    private String quiz;
    private Long studentId;
    private String studentName;
    private Long attempted;
    private Long correct;
    private Long incorrect;
    private Long marksObtained;
    private String grade;
    private String remark;
    private String maxMarks;

    @Temporal(TemporalType.DATE)
    private Date examDate;

    @Temporal(TemporalType.TIME)
    private Date examTime;

    private String status;

    private String email;

    private String phone;

    private String totalQuestions;


    public ResultResponseDto() {
    }

    public ResultResponseDto(Long id, String category, String quiz, Long studentId, String studentName, Long attempted, Long correct, Long incorrect, Long marksObtained, String grade, String remark, String maxMarks, Date examDate, Date examTime, String status) {
        this.id = id;
        this.category = category;
        this.quiz = quiz;
        this.studentId = studentId;
        this.studentName = studentName;
        this.attempted = attempted;
        this.correct = correct;
        this.incorrect = incorrect;
        this.marksObtained = marksObtained;
        this.grade = grade;
        this.remark = remark;
        this.maxMarks = maxMarks;
        this.examDate = examDate;
        this.examTime = examTime;
        this.status = status;
    }
}
