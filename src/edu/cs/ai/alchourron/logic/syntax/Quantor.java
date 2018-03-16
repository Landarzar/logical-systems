package edu.cs.ai.alchourron.logic.syntax;

import edu.cs.ai.alchourron.logic.Signature;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <V> The type of variables
 */
public interface Quantor<V extends Variable<S>,S extends Signature> extends SyntacticElement<S> {

	/*
	 * (non-Javadoc)
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	default boolean isLogical() {

		return true;
	}
}
