package com.rafay.quizapp.controller;

import com.rafay.quizapp.model.Question;
import com.rafay.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions()
    {
         try{
             return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
         }
         catch(Exception e)
         {
             e.printStackTrace();;
         }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/category/{category}")
    public List<Question> getQuestionsByCategory(@PathVariable String category)
    {
         return questionService.getAllQuestionsByCategory(category);
    }

    @PostMapping("/add")
    public Question addQuestion(@RequestBody Question question)
    {
       return questionService.addQuestion(question);
    }

}
