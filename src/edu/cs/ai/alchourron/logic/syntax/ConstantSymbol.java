package edu.cs.ai.alchourron.logic.syntax;

import edu.cs.ai.alchourron.logic.Signature;

public interface ConstantSymbol<S extends Signature>  extends SyntacticElement<S> {
	
	/*
	 * (non-Javadoc)
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	default boolean isLogical() {
		return false;
	}
}
