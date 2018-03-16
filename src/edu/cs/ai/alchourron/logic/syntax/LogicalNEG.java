package edu.cs.ai.alchourron.logic.syntax;

import edu.cs.ai.alchourron.logic.Signature;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <S> The Signature
 */
public interface LogicalNEG<S extends Signature>  extends LogicalOperator<S> {
	/*
	 * (non-Javadoc)
	 * @see cs.ai.logic.syntax.SyntacticElement#stringify()
	 */
	@Override
	public default String stringify() {
		return "( NOT " + getOperands().get(0) + ")";
	}
}
