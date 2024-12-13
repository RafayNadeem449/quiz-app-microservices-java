package com.rafay.quiz_service.service;

import com.rafay.quiz_service.dao.QuizDao;
import com.rafay.quiz_service.feign.QuizInterface;
import com.rafay.quiz_service.model.QuestionWrapper;
import com.rafay.quiz_service.model.Quiz;
import com.rafay.quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String quizTitle) {
        // Fetching the questions based on the category and number
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();

        // Check if the questions list is null or empty
        if (questions == null || questions.isEmpty()) {
            return new ResponseEntity<>("No questions found for the specified category", HttpStatus.BAD_REQUEST);
        }

        // Creating a new Quiz object
        Quiz quiz = new Quiz();
        quiz.setTitle(quizTitle);
        quiz.setQuestionIds(questions);
        // Saving the quiz to the database
        try {
            quizDao.save(quiz);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving the quiz: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Return success response
        return new ResponseEntity<>("Quiz created successfully", HttpStatus.CREATED);
    }


    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {

        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questions =  quizInterface.getQuestionsFromId(questionIds);
        return questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {

        ResponseEntity<Integer> score = quizInterface.getScore(responses);

       return score;
    }
}
