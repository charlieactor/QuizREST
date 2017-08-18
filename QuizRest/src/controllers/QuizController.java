package controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import data.QuizDAO;
import entities.Question;
import entities.Quiz;

@RestController
public class QuizController {
	
	@Autowired
	QuizDAO dao;

	@RequestMapping(path = "quizzes/ping", method = RequestMethod.GET)
	public String ping() {
		return "pong";
	}
	
	@RequestMapping(path = "quizzes", method = RequestMethod.GET)
	public List<Quiz> index() {
		return dao.index();
	}
	
	@RequestMapping(path = "quizzes/{id}", method = RequestMethod.GET)
	public Quiz show(@PathVariable int id) {
		return dao.show(id);
	}
	
	@RequestMapping(path = "quizzes", method = RequestMethod.POST)
	public Quiz create(@RequestBody String json) {
		ObjectMapper mapper = new ObjectMapper();
		Quiz q = null;
		try {
			q = mapper.readValue(json, Quiz.class);
			return dao.create(q);
		} catch (Exception e) {
			return q;
		}
	}
	
	@RequestMapping(path = "quizzes/{id}", method = RequestMethod.PUT)
	public Quiz update(@RequestBody String json, @PathVariable int id) {
		ObjectMapper mapper = new ObjectMapper();
		Quiz q = null;
		try {
			q = mapper.readValue(json, Quiz.class);
			return dao.update(id, q);
		} catch (Exception e) {
			return q;
		}
	}
	
	@RequestMapping(path = "quizzes/{id}", method = RequestMethod.DELETE)
	public boolean destroy(@PathVariable int id) {
		return dao.destroy(id);
	}
	
	@RequestMapping(path = "quizzes/{id}/questions", method = RequestMethod.GET)
	public Set<Question> questionsIndex(@PathVariable int id) {
		return dao.showQuestions(id);
	}
	
	@RequestMapping(path = "quizzes/{id}/questions", method = RequestMethod.POST)
	public Question createQuestion(@PathVariable int id, @RequestBody String json) {
		ObjectMapper mapper = new ObjectMapper();
		Question question = null;
		try {
			question = mapper.readValue(json, Question.class);
			return dao.createQuestion(id, question);
		} catch (Exception e) {
			return question;
		}
	}
	
	@RequestMapping(path = "quizzes/{id}/questions/{questionId}", method = RequestMethod.DELETE)
	public boolean deleteQuestion(@PathVariable int id, @PathVariable int questionId) {
		return dao.destroyQuestion(id, questionId);
	}
	
}
