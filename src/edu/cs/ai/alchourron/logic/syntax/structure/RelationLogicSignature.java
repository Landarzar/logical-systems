/**
 * 
 */
package edu.cs.ai.alchourron.logic.syntax.structure;

import edu.cs.ai.alchourron.logic.Signature;

/**
 * @author Kai Sauerwald
 * 
 * @param <R> The type for the relations
 * @param <F> The type for the functions
 */
public interface RelationLogicSignature<R extends Enum<R>,F extends Enum<F>> extends Signature {
	
	public default boolean isPredicateLogic() {
		return true;
	}
}
