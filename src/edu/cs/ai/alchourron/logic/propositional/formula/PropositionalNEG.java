package edu.cs.ai.alchourron.logic.propositional.formula;

import java.util.List;

import edu.cs.ai.alchourron.logic.propositional.PropositionalFormula;
import edu.cs.ai.alchourron.logic.propositional.PropositionalSignature;
import edu.cs.ai.alchourron.logic.syntax.LogicalNEG;
import edu.cs.ai.alchourron.logic.syntax.SyntacticElement;

public class PropositionalNEG<PSym> extends PropositionalFormula<PSym>
		implements LogicalNEG<PropositionalSignature<PSym>> {

	protected PropositionalSignature<PSym> signature;
	protected PropositionalFormula<PSym> operand;

	/**
	 * Constructs a new instance of this class
	 * 
	 * @author Kai Sauerwald
	 */
	public PropositionalNEG(PropositionalSignature<PSym> signature, PropositionalFormula<PSym> operand) {
		this.signature = signature;
		this.operand = operand;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.logic.Formula#getSyntaxTree()
	 */
	@Override
	public SyntacticElement<PropositionalSignature<PSym>> getSyntaxTree() throws UnsupportedOperationException {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.logic.Formula#getSignature()
	 */
	@Override
	public PropositionalSignature<PSym> getSignature() {
		return signature;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.logic.syntax.LogicalOperator#getOperands()
	 */
	@Override
	public List<? extends SyntacticElement<PropositionalSignature<PSym>>> getOperands() {
		return List.of(operand);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isAtom()
	 */
	@Override
	public boolean isAtom() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NOT " + this.operand;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.LaTeX#toLaTeX()
	 */
	@Override
	public String toLaTeX() {
		// TODO Auto-generated method stub
		return "\\negOf{" + this.operand.toLaTeX() + "}";
	}
}