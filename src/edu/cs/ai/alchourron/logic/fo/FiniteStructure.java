package edu.cs.ai.alchourron.logic.fo;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.Interpretation;
import edu.cs.ai.alchourron.logic.Signature;
import edu.cs.ai.alchourron.logic.semantics.interpretations.Relation;
import edu.cs.ai.alchourron.logic.syntax.formula.LogicalAND;
import edu.cs.ai.alchourron.logic.syntax.formula.LogicalNEG;
import edu.cs.ai.alchourron.logic.syntax.formula.Proposition;
import edu.cs.ai.alchourron.logic.syntax.structure.RelationLogicSignature;
import edu.cs.ai.alchourron.tools.Pair;

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
public class FiniteStructure<U, R extends Enum<R>, K extends Enum<K> , V, S extends RelationLogicSignature<R, K>>
		implements Interpretation<S> {

	S interpretation;
	Set<U> universe;
	EnumMap<R, Relation<U>> relations;
	EnumMap<K, Relation<U>> functions;

	/***
	 * Constructs a new interpretation
	 * @author Kai Sauerwald
	 * @param universe The universe of elements from <U>
	 * @param rel The interpretations of predicate symbols
	 * @param func The interpretation of function symbols
	 */
	public FiniteStructure(S interpretation,Set<U> universe, Collection<Pair<R, Relation<U>>> rel, Collection<Pair<K, Relation<U>>> func ) {
		this.interpretation = interpretation;
		this.universe = Collections.unmodifiableSet(universe);
		
		HashMap<R, Relation<U>> rmap = new HashMap<>();
		rel.forEach(p -> rmap.put(p.getFirst(), p.getSecond()));
		relations = new EnumMap<>(rmap);
		
		HashMap<K, Relation<U>> fmap = new HashMap<>();
		func.forEach(p -> fmap.put(p.getFirst(), p.getSecond()));
		functions = new EnumMap<>(fmap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		return builder.toString();
	}

	/***
	 * Generats a LaTeX-ified version of the stringrepresentation
	 */
	public String toLaTeX() {
		StringBuilder builder = new StringBuilder();

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
		return null;
	}
}
