package resource;

import java.util.List;
import java.util.Random;

public abstract class Individual implements Comparable<Individual> {

	protected int evaluation;
	protected char[] string;
	protected List<char[]> baseSequences;
	protected Random random;

	public Individual(List<char[]> arg0, int arg1) {
		string = new char[arg1];
		evaluation = -1;
		baseSequences = arg0;
		random = new Random();
	}

	public int getEvaluation() {
		if (evaluation < 0) {
			for (char[] baseSequence : baseSequences) {
				int newEval = 0;

				for (int i = 0; i < string.length; i++) {
					if (string[i] == baseSequence[i]) {
						newEval++;
					}
				}

				if (newEval > evaluation) {
					evaluation = newEval;
				}
			}

			evaluation = string.length - evaluation;
		}

		return evaluation;
	}

	public char[] getString() {
		return string;
	}

	public List<char[]> getBaseSequences() {
		return baseSequences;
	}

	@Override
	public int compareTo(Individual arg0) {
		return -(getEvaluation() - arg0.getEvaluation());
	}

	@Override
	public int hashCode() {
		int hashCode = 0;
		for (int i = 0; i < string.length; i++) {
			hashCode += (i + 1) * string[i];
		}

		return hashCode;
	}

	@Override
	public String toString() {
		return new String(string);
	}

}
