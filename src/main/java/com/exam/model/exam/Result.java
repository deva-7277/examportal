package com.exam.model.exam;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name ="results")
public class Result implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String status;
    private Long categoryId;
    private Long quizId;
    private Long examId;
    private Long studentId;
    private Long attempted;
    private Long correct;
    private Long incorrect;
    private Long marksObtained;
    private String grade;
    private String remark;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date examDate;

    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    private Date examTime;

    @PrePersist
    private void onCreate(){
        examDate = new Date();
        examTime = new Date();
    }



    public Result() {
    }

    public Result(String status, Long categoryId, Long quizId, Long examId, Long studentId, Long attempted, Long correct, Long incorrect, Long marksObtained, String grade, String remark) {
        this.status = status;
        this.categoryId = categoryId;
        this.quizId = quizId;
        this.examId = examId;
        this.studentId = studentId;
        this.attempted = attempted;
        this.correct = correct;
        this.incorrect = incorrect;
        this.marksObtained = marksObtained;
        this.grade = grade;
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getAttempted() {
        return attempted;
    }

    public void setAttempted(Long attempted) {
        this.attempted = attempted;
    }

    public Long getCorrect() {
        return correct;
    }

    public void setCorrect(Long correct) {
        this.correct = correct;
    }

    public Long getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(Long incorrect) {
        this.incorrect = incorrect;
    }

    public Long getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(Long marksObtained) {
        this.marksObtained = marksObtained;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", categoryId=" + categoryId +
                ", quizId=" + quizId +
                ", examId=" + examId +
                ", studentId=" + studentId +
                ", attempted=" + attempted +
                ", correct=" + correct +
                ", incorrect=" + incorrect +
                ", marksObtained=" + marksObtained +
                ", grade='" + grade + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
