package com.exam.model.exam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name ="results")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Integer marksObtained;
    private String grade;
    private String remark;

    private String maxMarks;

    @Column(nullable = false)
    private String examDate;

    @PrePersist
    private void onCreate(){
        examDate = String.valueOf(new Date());
    }

}
