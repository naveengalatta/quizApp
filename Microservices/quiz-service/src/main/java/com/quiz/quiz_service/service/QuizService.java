package com.quiz.quiz_service.service;

import com.quiz.quiz_service.dao.QuizDao;
import com.quiz.quiz_service.feign.QuizInterface;
import com.quiz.quiz_service.model.QuestionWrapper;
import com.quiz.quiz_service.model.Quiz;
import com.quiz.quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        //call question microservice http://localhost:8080/question/generate
        List<Integer> questions = quizInterface.generate(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestions(int id) {
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionId = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questionFromDB = quizInterface.getQuestionFromId(questionId);
        return questionFromDB;
    }

    public ResponseEntity<Integer> calculateResult(int id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}