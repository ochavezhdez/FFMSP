package resource.aco;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import resource.Individual;
import resource.Island;

public class Nest extends Island {

	private static final long serialVersionUID = 1994083044456024518L;
	private Route startTour;
	private List<Route> treeNodes;
	private int value;
	private Set<Individual> bestString;

	public Nest(int arg1, char[] arg2, List<char[]> arg3) {
		super();
		for (int i = 0; i < arg3.size(); i++) {
			individuals.add(new Ant(arg3, arg1));
		}

		treeNodes = new LinkedList<>();
		Queue<Route> queue = new LinkedList<>();
		startTour = new Route('*', arg1);
		treeNodes.add(startTour);
		queue.add(startTour);
		for (int i = 0; i < arg1; i++) {
			List<Route> list = new LinkedList<>();
			for (char c : arg2) {
				Route newTree = new Route(c, arg1);
				list.add(newTree);
				treeNodes.add(newTree);
			}

			while (!queue.isEmpty()) {
				Route quaternariTree = queue.remove();
				for (Route quaternariTreeC : list) {
					quaternariTree.putChild(quaternariTreeC, arg1);
				}
			}

			for (Route quaternariTree : list) {
				queue.add(quaternariTree);
			}
		}

		Route endTour = new Route();
		while (!queue.isEmpty()) {
			queue.remove().putChild(endTour, arg1);
		}

		value = 0;
		bestString = new HashSet<>();
	}

	@Override
	public Set<Individual> getUniqueIndividuals() {
		return bestString;
	}

	@Override
	public void putInmigrantIndividuals(Set<Individual> arg0) {
		for (Individual individual : arg0) {
			Ant ant = new Ant(individual.getBaseSequences(), individual.getString());
			ant.setPheromone(startTour);
		}
	}

	@Override
	protected void compute() {
		long time = System.currentTimeMillis();
		while (System.currentTimeMillis() - time < 60000) {
			// make a tour
			for (Individual individual : individuals) {
				((Ant) individual).tour(startTour);
			}

			for (Individual individual : individuals) {
				// update the best solution
				if (individual.getEvaluation() > value) {
					value = individual.getEvaluation();
					bestString = new HashSet<>();
				}

				if (value == individual.getEvaluation()) {
					bestString.add(new Ant(individual.getBaseSequences(), individual.getString()));
				}

				// update pheromone tour
				startTour.setPheromone((Ant) individual);

				// restart ant
				((Ant) individual).restart();
			}

			// evaporate pheromones
			for (Route quaternariTree : treeNodes) {
				quaternariTree.evaporate();
			}
		}
	}

}
