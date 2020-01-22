package edu.cs.ai.alchourron.logic.syntax.terms;

import edu.cs.ai.alchourron.logic.Signature;

public interface FunctionSymbol<FSym, S extends Signature>  extends Term<S> {
	
	/***
	 * Gets the symbol of this function.
	 * @author Kai Sauerwald
	 */
	FSym getSymbol();
	

	/***
	 * Returns the arity of this function.
	 * @author Kai Sauerwald
	 */
	int getArity();
	
	/*
	 * (non-Javadoc)
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	default boolean isLogical() {
		return false;
	}

}
