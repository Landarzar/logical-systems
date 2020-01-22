/**
 * 
 */
package edu.cs.ai.alchourron.logic.syntax.signature;

import edu.cs.ai.alchourron.logic.Signature;

/**
 * @author Kai Sauerwald
 *
 */
public interface PredicateLogicSignature extends Signature {
	
	public default boolean isPredicateLogic() {
		return true;
	}
}
