package edu.cs.ai.alchourron.logic.syntax.formula;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.signature.QuantificationLogicSignature;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <Q> The type which are bound by this quantifier (Variables, Relations, ...)
 */
public interface Quantor<Q,S extends QuantificationLogicSignature<Q>> extends LogicalOperator<S> {
	
	
	/***
	 * Gets the parameters of this quantifier
	 */
	Q getQuantifying();
	
	/***
	 * Gets the formula which is quantified
	 * @return
	 */
	Formula<S> getQuantified();

	/*
	 * (non-Javadoc)
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	default boolean isLogical() {

		return true;
	}
}
