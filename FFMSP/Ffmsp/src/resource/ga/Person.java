package resource.ga;

import java.util.List;

import resource.Individual;

public class Person extends Individual {

	public Person(char[] arg0, List<char[]> arg1) {
		super(arg1, arg0.length);

		string = arg0;
	}

	public Person(int arg0, char[] arg1, List<char[]> arg2) {
		super(arg2, arg0);

		for (int j = 0; j < arg0; j++) {
			string[j] = arg1[random.nextInt(arg1.length)];
		}
	}

	public Person crossWith(Person arg0) {
		char[] fatherString = arg0.getString();

		// Recombine pairs of parents
		char[] childrenString = new char[string.length];
		int origin = (int) (string.length * 0.6);
		int bond = (int) (string.length * 0.9);
		int crossoverPoint = origin + random.nextInt(bond - origin);
		System.arraycopy(string, 0, childrenString, 0, crossoverPoint);
		System.arraycopy(fatherString, crossoverPoint, childrenString, crossoverPoint, string.length - crossoverPoint);

		// Mutate the resulting offspring
		boolean mutateChildren0 = random.nextFloat() < 0.2;
		if (equals(arg0) || mutateChildren0) {
			int positionI = random.nextInt(childrenString.length);
			int positionJ = random.nextInt(childrenString.length);
			char character = childrenString[positionI];
			childrenString[positionI] = childrenString[positionJ];
			childrenString[positionJ] = character;
		}

		return new Person(childrenString, baseSequences);
	}

}
