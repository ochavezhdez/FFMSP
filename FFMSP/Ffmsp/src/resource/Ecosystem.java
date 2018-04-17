package resource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveAction;

import resource.aco.Nest;
import resource.ga.Build;

public class Ecosystem extends RecursiveAction {

	private static final long serialVersionUID = -4367318038443369736L;
	private int id;
	private long time;
	private Island build;
	private Island nest;
	private Solution solution;

	public Ecosystem(int arg0, char[] arg1, int arg2, List<char[]> arg3) {
		id = arg0;
		build = new Build(arg2, arg1, arg3);
		nest = new Nest(arg2, arg1, arg3);
	}

	@Override
	protected void compute() {
		time = System.currentTimeMillis();
		while (System.currentTimeMillis() - time < 540000) {
			invokeAll(build, nest);

			nest.putInmigrantIndividuals(build.getUniqueIndividuals());
			build.putInmigrantIndividuals(nest.getUniqueIndividuals());

			build.reinitialize();
			nest.reinitialize();
		}
		
		time = System.currentTimeMillis() - time;

		Set<Individual> individuals = new HashSet<>();
		individuals.addAll(build.getUniqueIndividuals());
		individuals.addAll(nest.getUniqueIndividuals());

		solution = new Solution(individuals);
	}

	public long getTime() {
		return time;
	}

	public int getEval() {
		return solution.getEvaluation();
	}

	@Override
	public String toString() {
		String line = "Iteración: " + (id + 1) + "\t" + "Tiempo: " + time + "ms" + "\n";
		line += solution.toString() + "\n";
		return line;
	}

}
