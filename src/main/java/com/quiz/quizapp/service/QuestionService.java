package com.quiz.quizapp.service;

import com.quiz.quizapp.dao.QuestionDao;
import com.quiz.quizapp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public List<Question> getAllQuestion() {
        return questionDao.findAll();
    }

    public List<Question> getAllQuestionByCategory(String category) {
        return questionDao.findByCategory(category);
    }

    public String addQuestion(Question question) {
        questionDao.save(question);
        return "success";
    }

    public String deleteQuestion(int id) {
        questionDao.deleteById(id);
        return "success";
    }
}
