/**
 * 
 */
package edu.cs.ai.alchourron.logic.syntax.structure;

import java.util.List;
import java.util.Set;

import edu.cs.ai.alchourron.logic.Signature;

/**
 * @author Kai Sauerwald
 * 
 * @param <K> The type for the variables used in terms
 */
public interface FunctionTermLogicSignature<K> extends Signature {
	
	
	/***
	 * Provides a set of function symbols of that signature.
	 * 
	 * @author Kai Sauerwald
	 */
	public Set<K> getFunctionSymbols();
	
	/****
	 * Provides the arity of the function symbol
	 * 
	 * @throws IllegalArgumentException the function symbol is not part of that
	 *                                  signature
	 */
	public int getFunctionArity(K functionsymbol);
}
