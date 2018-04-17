package resource;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class Iterator extends RecursiveAction {

	private static final long serialVersionUID = 223351043383599362L;
	private List<Ecosystem> ecosystems;
	private long time;

	public Iterator(int arg0, char[] arg1, int arg2, List<char[]> arg3) {
		ecosystems = new LinkedList<>();
		for (int i = 0; i < arg0; i++) {
			Ecosystem ecosystem = new Ecosystem(i, arg1, arg2, arg3);
			ecosystems.add(ecosystem);
		}
	}

	@Override
	protected void compute() {
		time = System.currentTimeMillis();

		invokeAll(ecosystems);

		time = System.currentTimeMillis() - time;
	}

	@Override
	public String toString() {
		String line = "";

		for (Ecosystem ecosystem : ecosystems) {
			line += ecosystem.toString() + "\n";
		}

		double sumTime = 0;
		double sumEval = 0;
		for (Ecosystem ecosystem : ecosystems) {
			sumTime += ecosystem.getTime();
			sumEval += ecosystem.getEval();
		}

		double promedioTime = sumTime / ecosystems.size();
		double promedioEval = sumEval / ecosystems.size();
		line += "Tiempo promedio: " + promedioTime + "ms" + "\n";

		double sumDesviationTime = 0;
		double sumDesviationEval = 0;
		for (Ecosystem ecosystem : ecosystems) {
			sumDesviationTime += Math.pow(ecosystem.getTime() - promedioTime, 2);
			sumDesviationEval += Math.pow(ecosystem.getEval() - promedioEval, 2);
		}

		double desviationTime = Math.sqrt(sumDesviationTime / (ecosystems.size() - 1));
		double desviationCost = Math.sqrt(sumDesviationEval / (ecosystems.size() - 1));
		line += "Desviación estándar del tiempo: " + desviationTime + "ms" + "\n\n";

		line += "Evaluación promedio: " + promedioEval + "\n";
		line += "Desviación estándar de la evaluación: " + desviationCost + "\n\n";

		line += "Tiempo total de iteraciones: " + time;

		return line;
	}
}
