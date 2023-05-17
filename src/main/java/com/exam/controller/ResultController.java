package com.exam.controller;

import com.exam.model.exam.Result;
import com.exam.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/result")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @PostMapping("/save-result")
    public Result saveResult(@RequestBody Result result){
        return this.resultService.saveResult(result);
    }
}
