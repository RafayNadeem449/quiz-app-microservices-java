package com.rafay.quizapp.service;

import com.rafay.quizapp.model.Question;
import com.rafay.quizapp.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;
    public List<Question> getAllQuestions() {
        return  questionDao.findAll();
    }

    public List<Question> getAllQuestionsByCategory(String category) {

        return questionDao.findByCategory(category);
    }

    public Question addQuestion(Question question) {

        return questionDao.save(question);
    }
}
