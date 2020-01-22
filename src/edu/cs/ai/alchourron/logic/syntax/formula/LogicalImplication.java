package edu.cs.ai.alchourron.logic.syntax.formula;

import edu.cs.ai.alchourron.logic.Signature;

public interface LogicalImplication <S extends Signature> extends LogicalOperator<S> {
	/*
	 * (non-Javadoc)
	 * @see cs.ai.logic.syntax.SyntacticElement#stringify()
	 */
	@Override
	public default String stringify() {
		return "(" + getOperands().get(0) + " -> " + getOperands().get(1) + ")";
	}
}
