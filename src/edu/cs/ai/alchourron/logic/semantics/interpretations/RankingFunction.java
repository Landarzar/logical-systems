package edu.cs.ai.alchourron.logic.semantics.interpretations;

import java.util.AbstractCollection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import edu.cs.ai.math.settheory.objects.Pair;

public class RankingFunction<T> extends AbstractCollection<Pair<T, Integer>> {

	private HashSet<Pair<T, Integer>> elements = new HashSet<>();

	public RankingFunction() {
	}

	public void normalize() {
		while (this.getByRank(0).isEmpty()) {
			elements.forEach(p -> p.setSecond(p.getSecond() - 1));
		}
	}

	public boolean add(T elem, Integer rank) {
		return add(new Pair<>(elem, rank));
	}

	@Override
	public boolean add(Pair<T, Integer> iPair) {
		Optional<Pair<T, Integer>> optional = elements.stream().filter(pair -> {
			return iPair.getFirst().equals(pair.getFirst());
		}).findAny();
		if (optional.isPresent())
			elements.remove(optional.get());
		elements.add(iPair);
		return true;
	}

	public Set<T> getByRank(Integer rank) {
		return this.elements.stream().filter(pair -> pair.getSecond().equals(rank)).map(pair -> pair.getFirst())
				.collect(Collectors.toUnmodifiableSet());
	}

	public Integer getMaxRank() {
		int max = 0;
		for (Pair<T, Integer> pair : elements) {
			if (pair.getSecond() > max)
				max = pair.getSecond();
		}
		return max;
	}

	public Integer getRank(T element) {
		Optional<Pair<T, Integer>> optional = this.elements.stream().filter(pair -> pair.getFirst().equals(element))
				.findFirst();
		if (optional.isEmpty())
			return null;
		return optional.get().getSecond();
	}

	@Override
	public Iterator<Pair<T, Integer>> iterator() {

		return elements.iterator();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

}
