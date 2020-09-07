package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;

import edu.cs.ai.alchourron.logic.syntax.Formula;
import edu.cs.ai.alchourron.logic.syntax.Signature;
import edu.cs.ai.alchourron.logic.syntax.signature.ImplicationLogicSignature;

public class LogicalImplication<S extends ImplicationLogicSignature> extends LogicalOperator<S> {

	S signature;
	Formula<S> premise;
	Formula<S> conclusion;

	public LogicalImplication(S signature, Formula<S> premise, Formula<S> conclusion) {
		this.signature = signature;
		this.premise = premise;
		this.conclusion = conclusion;
	}

	public Formula<S> getPremise() {
		return premise;
	}

	public Formula<S> getConclusion() {
		return conclusion;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		return List.of(premise, conclusion);
	}

	@Override
	public S getSignature() {
		return signature;
	}
}
