package edu.cs.ai.alchourron.logic.syntax.formula;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.structure.QuantificationLogicSignature;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <Q> The type which are bound by this quantifier (Variables, Relations, ...)
 */
public abstract class Quantor<Q,V,S extends QuantificationLogicSignature<Q,V>> extends LogicalOperator<S> {
	
	
	/***
	 * Gets the parameters of this quantifier
	 */
	public abstract Q getQuantifyer();
	
	/***
	 * Gets the variable of this quantifier
	 */
	public abstract V getVariables();
	
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
