package resource;

import java.util.HashSet;
import java.util.Set;

public class Solution implements Comparable<Solution> {

	private Set<String> strings;
	private int evaluation;

	public Solution(Set<Individual> arg0) {
		evaluation = 0;
		for (Individual individual : arg0) {
			if (evaluation < individual.getEvaluation()) {
				evaluation = individual.getEvaluation();
				strings = new HashSet<>();
			}

			if (evaluation == individual.getEvaluation()) {
				strings.add(individual.toString());
			}
		}
	}

	public Set<String> getStrings() {
		return strings;
	}

	public int getEvaluation() {
		return evaluation;
	}

	@Override
	public int compareTo(Solution arg0) {
		return evaluation - arg0.getEvaluation();
	}

	@Override
	public String toString() {
		String stringResult = evaluation + "\n";
		for (String string : strings) {
			stringResult += string + "\n";
		}

		return stringResult;
	}

}
