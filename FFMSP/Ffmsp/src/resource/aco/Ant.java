package resource.aco;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import resource.Individual;

public class Ant extends Individual {

	private int countTour;
	private int countPheromone;

	public Ant(List<char[]> arg0, int arg1) {
		super(arg0, arg1);
		countTour = 0;
		countPheromone = 0;
	}

	public Ant(List<char[]> arg0, char[] arg1) {
		this(arg0, arg1.length);
		string = arg1;
	}

	public void tour(Route arg0) {
		if (!arg0.isEmpty()) {
			if (arg0.getRoot() != '*') {
				string[countTour] = arg0.getRoot();
				countTour++;
			}

			Map<Route, Integer> childrens = arg0.getChilds();
			boolean isVisit = false;
			while (!isVisit) {
				Iterator<Route> iterator = childrens.keySet().iterator();
				while (iterator.hasNext() && !isVisit) {
					Route quaternariTreeC = (Route) iterator.next();
					int wish = random.nextInt(string.length);
					if (childrens.get(quaternariTreeC) > wish) {
						quaternariTreeC.tour(this);
						isVisit = true;
					}
				}
			}
		}
	}

	public void setPheromone(Route arg0) {
		if (countPheromone < string.length) {
			Route child = arg0.getChild(string[countPheromone]);
			countPheromone++;
			int value = arg0.getChilds().get(child) + getEvaluation();
			arg0.getChilds().put(child, value);
			child.setPheromone(this);
			arg0.evaporate();
		}
	}

	public void restart() {
		countTour = 0;
		countPheromone = 0;
		evaluation = -1;
	}

}
