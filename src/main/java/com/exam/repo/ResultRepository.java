package com.exam.repo;

import com.exam.model.exam.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    @Query(value = "SELECT * FROM EXAM.RESULTS R WHERE R.quiz_id= :quizId ORDER BY R.:sortBy :sortDir",nativeQuery = true)
    List<Result> findAllByQuizId(Long quizId, String sortBy, String sortDir);

    @Query(value = "Select * from exam.results r where r.student_id = :student_id AND r.quiz_id = :quiz_id",nativeQuery = true)
    List<Result> findAllByQuizIdAndUserId(Long quiz_id, Long student_id);

    @Query(value = "SELECT * FROM EXAM.RESULTS R WHERE R.quiz_id= :quizId ORDER BY R.marks_obtained DESC LIMIT 3", nativeQuery = true)
    List<Result> findTop3(Long quizId);

    @Query(value = "SELECT * FROM EXAM.RESULTS R WHERE R.quiz_id= :quizId", nativeQuery = true)
    List<Result> findAllResults(Long quizId);
}
