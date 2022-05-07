package tn.esprit.spring.services;

import java.util.Comparator;

import tn.esprit.spring.entities.Condidacy;

public class CondidacyComparator implements Comparator<Condidacy>{

	@Override
	public int compare(Condidacy c1, Condidacy c2) {
			return Integer.compare(c1.getScore(), c2.getScore());
	}

}
