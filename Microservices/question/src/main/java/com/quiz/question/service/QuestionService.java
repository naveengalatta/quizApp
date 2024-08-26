package com.quiz.question.service;

import com.quiz.question.dao.QuestionDao;
import com.quiz.question.model.Question;
import com.quiz.question.model.QuestionWrapper;
import com.quiz.question.model.Response;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestion() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getAllQuestionByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public String deleteQuestion(int id) {
        questionDao.deleteById(id);
        return "success";
    }

    public ResponseEntity<List<Integer>> getQuestionForQuiz(String categoryName, int numQuestion) {
        List<Integer> question = questionDao.findRandomQuestionByCatagory(categoryName, numQuestion);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionFormId(List<Integer> questionIds) {

        List<QuestionWrapper> wrappers = new ArrayList<>();
        for(Integer id : questionIds) {
            Question question = questionDao.findById(id).get();
            QuestionWrapper questionWrapper = new QuestionWrapper();
            questionWrapper.setId(question.getId());
            questionWrapper.setQuestionTitle(question.getQuestionTitle());
            questionWrapper.setOption1(question.getOption1());
            questionWrapper.setOption2(question.getOption2());
            questionWrapper.setOption3(question.getOption3());
            questionWrapper.setOption4(question.getOption4());
            wrappers.add(questionWrapper);
        }
        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int correctAnswer = 0;

        for (Response response : responses) {
            Question question = questionDao.findById(response.getId()).get();
            if (question.getRightAnswer().equals(response.getResponse())) {
                correctAnswer++;
            }
        }
        return new ResponseEntity<>(correctAnswer, HttpStatus.OK);
    }
}
