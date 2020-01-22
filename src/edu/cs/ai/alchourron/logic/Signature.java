package edu.cs.ai.alchourron.logic;

import edu.cs.ai.alchourron.LaTeX;

/***
 * This Interface represent type for logical signatures
 * @author Kai Sauerwald
 *
 */
public interface Signature extends LaTeX {
	
	public default boolean isPredicateLogic() {
		return false;
	}
}
