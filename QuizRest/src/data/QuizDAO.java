package data;

import java.util.List;
//import java.util.Set;
import java.util.Set;

import entities.Question;
import entities.Quiz;

public interface QuizDAO {
	public List<Quiz> index();
	public Quiz show(int id);
	public Quiz create(Quiz quiz);
	public Quiz update(int id, Quiz quiz);
	public boolean destroy(int id);
	public Set<Question> showQuestions(int id);
	public Question createQuestion(int id, Question question);
	public boolean destroyQuestion(int id, int qid);
	
}
