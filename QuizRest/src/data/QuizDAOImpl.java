package data;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import entities.Question;
import entities.Quiz;

@Transactional
public class QuizDAOImpl implements QuizDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Quiz> index() {
		String query = "SELECT q FROM Quiz q";
		return em.createQuery(query, Quiz.class).getResultList();
	}

	@Override
	public Quiz show(int id) {
		String query = "SELECT q FROM Quiz q WHERE q.id = :id";
		return em.createQuery(query, Quiz.class).setParameter("id", id).getResultList().get(0);
	}

	@Override
	public Quiz create(Quiz quiz) {
		em.persist(quiz);
		em.flush();
		quiz = em.find(Quiz.class, quiz.getId());
		return quiz;
	}

	@Override
	public Quiz update(int id, Quiz quiz) {
		Quiz managed = em.find(Quiz.class, id);
		managed.setName(quiz.getName());
		return managed;
	}

	@Override
	public boolean destroy(int id) {
		Quiz managed = em.find(Quiz.class, id);
		try {
			em.remove(managed);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Set<Question> showQuestions(int id) {
		String query = "SELECT q FROM Quiz q WHERE q.id = :id";
		Quiz q = em.createQuery(query, Quiz.class)
				   .setParameter("id", id)
				   .getResultList()
				   .get(0);
		return q.getQuestions();
	}

	@Override
	public Question createQuestion(int id, Question question) {
		Quiz quiz = em.find(Quiz.class, id);
		question.setQuiz(quiz);
		em.persist(question);
		em.flush();
		Set<Question> questions = quiz.getQuestions();
		questions.add(question);
		quiz.setQuestions(questions);
		return question;
	}

	@Override
	public boolean destroyQuestion(int id, int qid) {
		Quiz q = em.find(Quiz.class, id);
		Question question = em.find(Question.class, qid);
		Set<Question> questions = q.getQuestions();
		questions.remove(question);
		q.setQuestions(questions);
		try {
			em.remove(question);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
