package resource.aco;

import java.util.HashMap;
import java.util.Map;

public class Route {

	private Character root;
	private Map<Character, Route> chars;
	private Map<Route, Integer> childs;
	private float a;
	private float b;

	public Route() {
		root = null;
		childs = null;
	}

	public Route(Character arg0, int arg1) {
		root = arg0;
		childs = new HashMap<>(4);
		chars = new HashMap<>(4);
		a = 0.25f * arg1;
		b = 0.75f * arg1;
	}

	public Character getRoot() {
		return root;
	}

	public void putChild(Route arg0, int arg1) {
		chars.put(arg0.getRoot(), arg0);
		childs.put(arg0, (int) (arg1 * 0.5f));
	}

	public boolean isEmpty() {
		return root == null && childs == null;
	}

	public Map<Route, Integer> getChilds() {
		return childs;
	}

	public Route getChild(char arg0) {
		return chars.get(arg0);
	}

	public void setPheromone(Ant arg0) {
		arg0.setPheromone(this);
	}

	public void tour(Ant arg0) {
		arg0.tour(this);
	}

	public void evaporate() {
		int xMax = Integer.MIN_VALUE;
		int xMin = Integer.MAX_VALUE;
		for (Route iterable_element : childs.keySet()) {
			int value = childs.get(iterable_element);
			if (xMax < value) {
				xMax = value;
			}

			if (xMin > value) {
				xMin = value;
			}
		}

		float factor = (b - a) / (xMax + 1 - xMin);
		for (Route iterable_element : childs.keySet()) {
			int value = childs.get(iterable_element);
			int newValue = (int) (a + (value - xMin) * factor);
			childs.put(iterable_element, newValue);
		}
	}
}
