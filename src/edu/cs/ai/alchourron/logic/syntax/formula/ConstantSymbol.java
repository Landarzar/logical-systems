package edu.cs.ai.alchourron.logic.syntax.formula;

import edu.cs.ai.alchourron.logic.Signature;
import edu.cs.ai.alchourron.logic.syntax.terms.Term;

public interface ConstantSymbol<CSym, S extends Signature>  extends Term<S> {
	

	/***
	 * Gets the symbol of this constant.
	 * @author Kai Sauerwald
	 */
	CSym getSymbol();
	
	/*
	 * (non-Javadoc)
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	default boolean isLogical() {
		return false;
	}
}
