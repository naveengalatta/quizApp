package com.quiz.question.controller;

import com.quiz.question.model.Question;
import com.quiz.question.model.QuestionWrapper;
import com.quiz.question.model.Response;
import com.quiz.question.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allquestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestion();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getAllQuestionByCategory(@PathVariable("category")  String category) {
        return  questionService.getAllQuestionByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable("id") int id) {
        questionService.deleteQuestion(id);
        return "Deleted Successfully";
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> generate(@RequestParam String categoryName,@RequestParam int numQuestion) {
        return questionService.getQuestionForQuiz(categoryName, numQuestion);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(@RequestBody List<Integer> questionIds) {
        return questionService.getQuestionFormId(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
        return questionService.getScore(responses);
    }
}
