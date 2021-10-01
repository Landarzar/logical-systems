package edu.cs.ai.alchourron.logic.semantics.interpretations;

import java.util.AbstractCollection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import edu.cs.ai.math.settheory.Pair;
import edu.cs.ai.math.settheory.Tuple;
import edu.cs.ai.math.settheory.relation.RelationStatus;
import edu.cs.ai.math.settheory.relation.TotalPreorder;
import edu.cs.ai.math.settheory.relation.implementation.AbstractTotalPreorderImpl;

/***
 * 
 * TODO: Review this class!
 * @author Kai Sauerwald
 *
 * @param <T>
 */
public class RankingFunction<T> extends AbstractCollection<Pair<T, Integer>> {

	private CopyOnWriteArraySet<Pair<T, Integer>> elements = new CopyOnWriteArraySet<>();

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
		return elements.size();
	}
	
	public TotalPreorder<T> getTotalPreorder(){
		return new AbstractTotalPreorderImpl<T>() {

			@Override
			public List<Set<T>> getLayers() {
				int max = getMaxRank();
				
				LinkedList<Set<T>> result = new LinkedList<>();
				
				for (int i = 0; i <= max; i++) {
					Set<T> l = getByRank(i);
					if(l != null && !l.isEmpty())
						result.add(l);
				}
				
				return result;
			}
		};
	}

}
