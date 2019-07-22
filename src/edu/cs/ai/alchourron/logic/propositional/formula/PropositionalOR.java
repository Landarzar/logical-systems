package edu.cs.ai.alchourron.logic.propositional.formula;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import edu.cs.ai.alchourron.logic.propositional.PropositionalFormula;
import edu.cs.ai.alchourron.logic.propositional.PropositionalSignature;
import edu.cs.ai.alchourron.logic.syntax.LogicalOR;
import edu.cs.ai.alchourron.logic.syntax.SyntacticElement;

public class PropositionalOR<PSym> extends PropositionalFormula<PSym>
		implements LogicalOR<PropositionalSignature<PSym>> {

	protected PropositionalSignature<PSym> signature;
	ArrayList<PropositionalFormula<PSym>> operands;

	/**
	 * Constructs a new instance of this class
	 * 
	 * @author Kai Sauerwald
	 */
	public PropositionalOR(PropositionalSignature<PSym> signature,
			Collection<PropositionalFormula<PSym>> operands) {
		this.signature = signature;
		this.operands = new ArrayList<>(operands);
	}

	/**
	 * Constructs a new instance of this class
	 * 
	 * @author Kai Sauerwald
	 */
	@SafeVarargs
	public PropositionalOR(PropositionalSignature<PSym> signature, PropositionalFormula<PSym>... operands) {
		this.signature = signature;
		this.operands = new ArrayList<>();
		for (PropositionalFormula<PSym> op : operands) {
			this.operands.add(op);
		}
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
	public List<PropositionalFormula<PSym>> getOperands() {
		return Collections.unmodifiableList(operands);
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
		StringBuilder builder = new StringBuilder();
		builder.append("(");

		boolean first = true;

		for (PropositionalFormula<PSym> propositionalFormula : operands) {
			if (first) {
				builder.append(propositionalFormula.toString());
				first = false;
				continue;
			}
			builder.append(" OR ");
			builder.append(propositionalFormula.toString());
		}

		builder.append(")");
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.LaTeX#toLaTeX()
	 */
	@Override
	public String toLaTeX() {
		StringBuilder builder = new StringBuilder();

		boolean first = true;

		for (PropositionalFormula<PSym> propositionalFormula : operands) {
			if (first) {
				builder.append(propositionalFormula.toLaTeX());
				first = false;
				continue;
			}
			builder.append(" \\lor ");
			builder.append(propositionalFormula.toLaTeX());
		}
		return builder.toString();
	}
}