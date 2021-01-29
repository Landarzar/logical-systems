package edu.cs.ai.alchourron.logic.logics.predicatelogics;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import edu.cs.ai.alchourron.logic.Interpretation;
import edu.cs.ai.alchourron.logic.syntax.structure.RelationLogicSignature;
import edu.cs.ai.math.setheory.objects.Pair;
import edu.cs.ai.math.setheory.objects.Relation;
import edu.cs.ai.math.setheory.objects.Tuple;

/***
 * Represents finite structues where for instance used first order logic as interpretations.
 * 
 * @author Kai Sauerwald
 *
 * @param <U> The type of the elements in the universe
 * @param <R> The type of relation symbols
 * @param <K> The type of function symbols
 * @param <S> The type of the signature
 */
public class FiniteStructure<U, R extends Enum<R>, K extends Enum<K>, S extends RelationLogicSignature<R, K>>
		implements Interpretation<S> {

	S signature;
	Set<U> universe;
	Map<R, Relation<U>> relations;
	Map<K, Function<Tuple<U>, U>> functions;

	/***
	 * Constructs a new interpretation
	 * @author Kai Sauerwald
	 * @param universe The universe of elements from <U>
	 * @param rel The interpretations of predicate symbols
	 * @param func The interpretation of function symbols
	 */
	public FiniteStructure(S signature,Set<U> universe, Collection<Pair<R, Relation<U>>> rel, Collection<Pair<K, Function<Tuple<U>, U>>> func ) {
		this.signature = signature;
		this.universe = Collections.unmodifiableSet(universe);
		
		HashMap<R, Relation<U>> rmap = new HashMap<>();
		rel.forEach(p -> rmap.put(p.getFirst(), p.getSecond()));
		relations = Collections.unmodifiableMap(rmap);
		
		HashMap<K, Function<Tuple<U>, U>> fmap = new HashMap<>();
		func.forEach(p -> fmap.put(p.getFirst(), p.getSecond()));
		functions = Collections.unmodifiableMap(fmap);
	}
	
	public Set<U> getUniverse() {
		return universe;
	}
	
	public Relation<U> getRelation(R symb){
		return relations.get(symb);
	}
	
	public Function<Tuple<U>, U> getFunction(K symb){
		return functions.get(symb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("<");
		builder.append(universe.toString());
		builder.append(",");
		if(!relations.isEmpty()) {
		relations.forEach((r,a) ->{
			builder.append(r + ": ");
			builder.append(a  + " ");
		});
		}

		if(!functions.isEmpty()) {
		functions.forEach((f,a) ->{
			builder.append(f  + " ");
		});
		}
		builder.append(">");

		return builder.toString();
	}

	/***
	 * Generats a LaTeX-ified version of the stringrepresentation
	 */
	public String toLaTeX() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("<");
		builder.append(universe.toString());
		builder.append(",");
		relations.forEach((r,a) ->{
			builder.append(r);
		});

		builder.append(">");

		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FiniteStructure))
			return false;
		return true;
	}

	@Override
	public boolean isMatchingSignature(S signature) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public S getSignature() {
		// TODO Auto-generated method stub
		return signature;
	}
}
