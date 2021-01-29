package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.Signature;
import edu.cs.ai.alchourron.logic.syntax.structure.BiImplicationLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.ImplicationLogicSignature;

public class LogicalBiImplication<S extends BiImplicationLogicSignature> extends LogicalOperator<S> {
	S signature;
	Formula<S> implicant1;
	Formula<S> implicant2;

	public LogicalBiImplication(S signature, Formula<S> implicant1, Formula<S> implicant2) {
		this.signature = signature;
		this.implicant1 = implicant1;
		this.implicant2 = implicant2;
	}

	public Formula<S> getFirst() {
		return implicant1;
	}

	public Formula<S> getSecond() {
		return implicant2;
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
	public List<Formula<S>> getOperands() {
		return List.of(implicant1, implicant2);
	}

	@Override
	public S getSignature() {
		return signature;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return  stringify();
	}
}
