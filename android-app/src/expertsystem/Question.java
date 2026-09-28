package expertsystem;

public class Question {

	public String question;
	public int max = 0; // per più opzioni

	public enum Type {
		YN, number, terminate, statement, SA
	}

	public Type questionType;

	public Question(String F, Type C, int maxi) {
		max = maxi;
		question = F;
		questionType = C;
	}
}
