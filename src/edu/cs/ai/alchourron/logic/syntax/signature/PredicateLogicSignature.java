/**
 * 
 */
package edu.cs.ai.alchourron.logic.syntax.signature;

import edu.cs.ai.alchourron.logic.syntax.Signature;

/**
 * @author Kai Sauerwald
 *
 */
public interface PredicateLogicSignature extends Signature {
	
	public default boolean isPredicateLogic() {
		return true;
	}
}
