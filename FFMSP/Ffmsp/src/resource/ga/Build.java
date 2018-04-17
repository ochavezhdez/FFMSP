package resource.ga;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import resource.Individual;
import resource.Island;

public class Build extends Island {

	private static final long serialVersionUID = -3172822313022121927L;

	public Build(int arg0, char[] arg1, List<char[]> arg2) {
		super();

		for (int i = 0; i < arg2.size(); i++) {
			individuals.add(new Person(arg0, arg1, arg2));
		}
	}

	@Override
	public Set<Individual> getUniqueIndividuals() {
		Set<Individual> setIndividuals = new HashSet<>();
		setIndividuals.addAll(individuals);

		return setIndividuals;
	}

	@Override
	public void putInmigrantIndividuals(Set<Individual> arg0) {
		Set<Individual> origen = getUniqueIndividuals();
		int individualsSize = individuals.size();
		individuals = new LinkedList<>();
		while (individuals.size() < individualsSize) {
			int i = 0;
			Iterator<Individual> iterator = origen.iterator();
			while (iterator.hasNext() && i < individualsSize) {
				individuals.add(iterator.next());
			}

			iterator = arg0.iterator();
			while (iterator.hasNext() && i < individualsSize) {
				Individual ant = iterator.next();
				individuals.add(new Person(ant.getString(), ant.getBaseSequences()));
			}
		}
	}

	@Override
	protected void compute() {
		long time = System.currentTimeMillis();
		Person best = (Person) individuals.get(0);
		while (System.currentTimeMillis() - time < 60000) {
			// crossbreeding apply
			boolean crossingFather = random.nextFloat() < 0.9;
			if (crossingFather) {
				// Select parents
				Queue<Person> fathers = new PriorityQueue<>();
				for (int j = 0; j < 5; j++) {
					fathers.add((Person) individuals.remove(random.nextInt(individuals.size())));
				}

				Person father0 = fathers.poll();
				Person father1 = fathers.poll();
				Person children0 = father0.crossWith(father1);
				Person children1 = father1.crossWith(father0);

				// Select individuals for the next generation
				fathers.add(father0);
				fathers.add(father1);
				fathers.add(children0);
				fathers.add(children1);

				Person newBest = fathers.peek();
				while (!fathers.isEmpty() && individuals.size() < newBest.getBaseSequences().size()) {
					individuals.add(fathers.poll());
				}

				if (newBest.compareTo(best) < 0) {
					best = newBest;
				}
			}
		}
	}

}
