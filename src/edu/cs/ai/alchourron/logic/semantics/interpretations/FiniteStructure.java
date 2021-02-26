package edu.cs.ai.alchourron.logic.semantics.interpretations;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.cs.ai.alchourron.logic.Interpretation;
import edu.cs.ai.alchourron.logic.Signature;
import edu.cs.ai.alchourron.logic.syntax.structure.FunctionTermLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.PredicateLogicSignature;
import edu.cs.ai.math.settheory.Pair;
import edu.cs.ai.math.settheory.relation.AutoFunction;
import edu.cs.ai.math.settheory.relation.Relation;

/***
 * Represents finite structues where for instance used first order logic as
 * interpretations.
 * 
 * @author Kai Sauerwald
 *
 * @param <U> The type of the elements in the universe
 * @param <R> The type of relation symbols
 * @param <K> The type of function symbols
 * @param <S> The type of the signature
 */
public class FiniteStructure<U, R, K, S extends Signature>
		implements Interpretation<S> {

	Set<U> universe;
	Map<R, Relation<U>> relations;
	Map<K, AutoFunction<U>> functions;

	/***
	 * Constructs a new interpretation
	 * 
	 * @author Kai Sauerwald
	 * @param universe The universe of elements from <U>
	 * @param rel      The interpretations of predicate symbols
	 * @param func     The interpretation of function symbols
	 */
	public FiniteStructure(Set<U> universe, Collection<Pair<R, Relation<U>>> rel,
			Collection<Pair<K, AutoFunction<U>>> func) {
		this.universe = Collections.unmodifiableSet(universe);

		HashMap<R, Relation<U>> rmap = new HashMap<>();
		rel.forEach(p -> rmap.put(p.getFirst(), p.getSecond()));
		relations = Collections.unmodifiableMap(rmap);

		HashMap<K, AutoFunction<U>> fmap = new HashMap<>();
		func.forEach(p -> fmap.put(p.getFirst(), p.getSecond()));
		functions = Collections.unmodifiableMap(fmap);
	}

	public Set<U> getUniverse() {
		return universe;
	}

	public Relation<U> getRelation(R symb) {
		return relations.get(symb);
	}

	public AutoFunction<U> getFunction(K symb) {
		return functions.get(symb);
	}
	
	public int getRelationArity(R relationsymb) {
		return this.relations.get(relationsymb).getArity();
	}
	
	public int getFunctionArity(K funcsymb) {
		return this.functions.get(funcsymb).getArity();
	}
	
	public Set<R> getRelationSymbols(){
		return this.relations.keySet();
	}
	
	/***
	 * Returns all Functions Symbols which are defined here
	 */
	public Set<K> getFunctionSymbols(){
		return this.functions.keySet();
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
		if (!relations.isEmpty()) {
			relations.forEach((r, a) -> {
				builder.append(r + ": ");
				builder.append(a + " ");
			});
		}

		if (!functions.isEmpty()) {
			functions.forEach((f, a) -> {
				builder.append(f + " ");
			});
		}
		builder.append(">");

		return builder.toString();
	}

	/***
	 * Generats a LaTeX-ified version of the stringrepresentation
	 */
	@Override
	public String toLaTeX() {
		StringBuilder builder = new StringBuilder();

		builder.append("<");
		builder.append(universe.toString());
		builder.append(",");
		relations.forEach((r, a) -> {
			builder.append(r);
		});

		builder.append(">");

		return builder.toString();
	}

	@Override
	public boolean isMatchingSignature(S signature) {
		if (signature instanceof PredicateLogicSignature<?>) {
			PredicateLogicSignature<R> predSig = (PredicateLogicSignature<R>) signature;
			if(!getRelationSymbols().containsAll(predSig.getPredicateSymbols()))
				return false;
		}
		if (signature instanceof FunctionTermLogicSignature<?>) {
			FunctionTermLogicSignature<K> predSig = (FunctionTermLogicSignature<K>) signature;
			if(!getFunctionSymbols().containsAll(predSig.getFunctionSymbols()))
				return false;
		}
		
		return true;
	}
}
