package edu.cs.ai.alchourron.logic.syntax;

import edu.cs.ai.alchourron.logic.Signature;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <T> The type which are bound by this quantifier (Variables, Relations, ...)
 */
public interface Quantor<T,S extends Signature> extends LogicalOperator<S> {
	
	
	/***
	 * Gets the parameters of this quantifier
	 */
	T getQuantifying();
	
	/***
	 * Gets the formula which is quantified
	 * @return
	 */
	SyntacticFormula<S> getQuantified();

	/*
	 * (non-Javadoc)
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	default boolean isLogical() {

		return true;
	}
}
