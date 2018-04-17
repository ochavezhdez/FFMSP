package resource;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.RecursiveAction;

public abstract class Island extends RecursiveAction {

	private static final long serialVersionUID = -6665587282396071866L;
	protected List<Individual> individuals;
	protected Random random;

	public Island() {
		individuals = new LinkedList<>();
		random = new Random();
	}

	public abstract Set<Individual> getUniqueIndividuals();

	public abstract void putInmigrantIndividuals(Set<Individual> arg0);

}
