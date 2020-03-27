package edu.cs.ai.alchourron.logic.syntax.formula;

import edu.cs.ai.alchourron.logic.syntax.Formula;
import edu.cs.ai.alchourron.logic.syntax.signature.QuantificationLogicSignature;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <Q> The type which are bound by this quantifier (Variables, Relations, ...)
 */
public abstract class Quantor<K extends Enum<K>,Q,S extends QuantificationLogicSignature<K,Q>> extends LogicalOperator<S> {
	
	
	/***
	 * Gets the parameters of this quantifier
	 */
	public abstract Q getQuantifying();
	
	/***
	 * Gets the formula which is quantified
	 * @return
	 */
	public abstract Formula<S> getQuantified();

	/*
	 * (non-Javadoc)
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	public boolean isLogical() {

		return true;
	}
}
