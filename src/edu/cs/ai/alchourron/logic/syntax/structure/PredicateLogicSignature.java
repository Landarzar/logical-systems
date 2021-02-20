/**
 * 
 */
package edu.cs.ai.alchourron.logic.syntax.structure;

import java.util.List;
import java.util.Set;

/**
 * @author Kai Sauerwald
 * 
 * @param <R> The type for the predicate symbols
 * @param <K> The type for the functions symbols
 */
public interface PredicateLogicSignature<R extends Enum<R>, K extends Enum<K>> extends AtomLogicSignature {

	/***
	 * Provides a set of predicate symbols of that signature.
	 * 
	 * @author Kai Sauerwald
	 */
	public List<R> getPredicateSymbols();

	/***
	 * Provides a set of function symbols of that signature.
	 * 
	 * @author Kai Sauerwald
	 */
	public List<K> getFunctionSymbols();

	/****
	 * Provides the arity of the predicate symbol
	 * 
	 * @throws IllegalArgumentException the predicate symbol is not part of that
	 *                                  signature
	 */
	public int getPredicateArity(R predicatesymbol);

	/****
	 * Provides the arity of the function symbol
	 * 
	 * @throws IllegalArgumentException the function symbol is not part of that
	 *                                  signature
	 */
	public int getFunctionArity(K functionsymbol);

	@Override
	public default boolean isPredicateLogic() {
		return true;
	}
}
