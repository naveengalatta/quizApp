package com.quiz.quiz_service.controller;

import com.quiz.quiz_service.dao.QuizDao;
import com.quiz.quiz_service.model.QuestionWrapper;
import com.quiz.quiz_service.model.QuizDto;
import com.quiz.quiz_service.model.Response;
import com.quiz.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @GetMapping("{id}")
    public ResponseEntity<List<QuestionWrapper>> getList(@PathVariable int id) {
        return quizService.getQuestions(id);
    }

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.createQuiz(quizDto.getCategoryName(), quizDto.getNumQuestions(), quizDto.getTitle());
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id, @RequestBody List<Response> responses) {
        return quizService.calculateResult(id, responses);
    }
}
