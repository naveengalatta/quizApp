package com.quiz.quizapp.controller;

import com.quiz.quizapp.model.Question;
import com.quiz.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allquestions")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestion();
    }

    @GetMapping("category/{category}")
    public List<Question> getAllQuestionByCategory(@PathVariable("category")  String category) {
        return  questionService.getAllQuestionByCategory(category);
    }

    @PostMapping("add")
    public String addQuestion(@RequestBody Question question) {
        questionService.addQuestion(question);
        return "Added Successfully.";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable("id") int id) {
        questionService.deleteQuestion(id);
        return "Deleted Successfully";
    }
}
