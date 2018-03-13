package cs.ai.logic.syntax;

import cs.ai.logic.Signature;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <S> The Signature
 */
public interface LogicalAND<S extends Signature>  extends LogicalOperator<S> {

	/*
	 * (non-Javadoc)
	 * @see cs.ai.logic.syntax.SyntacticElement#stringify()
	 */
	@Override
	public default String stringify() {
		return "(" + getOperands().get(0) + " AND " + getOperands().get(1) + ")";
	}
}
