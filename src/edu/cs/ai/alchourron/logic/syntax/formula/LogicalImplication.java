package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;

import edu.cs.ai.alchourron.logic.syntax.Formula;
import edu.cs.ai.alchourron.logic.syntax.Signature;
import edu.cs.ai.alchourron.logic.syntax.signature.ImplicationLogicSignature;

public class LogicalImplication <S extends ImplicationLogicSignature> extends LogicalOperator<S> {
	/*
	 * (non-Javadoc)
	 * @see cs.ai.logic.syntax.SyntacticElement#stringify()
	 */
	@Override
	public String stringify() {
		return "(" + getOperands().get(0) + " -> " + getOperands().get(1) + ")";
	}

	@Override
	public boolean isAtom() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<? extends Formula<S>> getOperands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S getSignature() {
		// TODO Auto-generated method stub
		return null;
	}
}
