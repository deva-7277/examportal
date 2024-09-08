package com.exam.utils;

import com.exam.model.exam.Result;

import java.util.*;
import java.util.stream.Collectors;

public class RankMapper {

    public static Map<Integer, Integer> getMarksToRankMap(List<Result> resultList) {
        // Sort results by marksObtained in descending order
        List<Result> sortedResults = resultList.stream()
                .sorted((r1, r2) -> Integer.compare(r2.getMarksObtained(), r1.getMarksObtained()))
                .collect(Collectors.toList());

        sortedResults.stream().forEach(r->System.out.println(r.getStudentId() + " got marks : "+r.getMarksObtained()));

        // Create a Map to store marksObtained as key and rank as value
        Map<Integer, Integer> markRankMap = new HashMap<>();
        int previousMarks = Integer.MAX_VALUE;
        int rank = 0;

        for (Result result : sortedResults) {
            int currentMarks = result.getMarksObtained();
            System.out.println("currentMarks :"+currentMarks+" current rank : "+rank);
            if(currentMarks<previousMarks){
                System.out.println("previousMarks: "+previousMarks + "currentMarks: "+currentMarks);
                rank++;
                System.out.println("rank: "+rank);
                previousMarks = currentMarks;
                System.out.println("previousMarks: "+previousMarks);
            }
            markRankMap.put(currentMarks, rank);
        }

        return markRankMap;
    }
}

