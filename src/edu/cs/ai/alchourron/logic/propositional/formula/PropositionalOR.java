package edu.cs.ai.alchourron.logic.propositional.formula;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import edu.cs.ai.alchourron.logic.propositional.PropositionalFormula;
import edu.cs.ai.alchourron.logic.propositional.PropositionalSignature;
import edu.cs.ai.alchourron.logic.syntax.SyntacticElement;
import edu.cs.ai.alchourron.logic.syntax.formula.LogicalOR;

public class PropositionalOR<PSym> extends // PropositionalFormula<PSym>
		LogicalOR<PropositionalSignature<PSym>>
implements PropositionalFormula<PSym>{

	/**
	 * Constructs a new instance of this class
	 * 
	 * @author Kai Sauerwald
	 */
	public PropositionalOR(PropositionalSignature<PSym> signature, Collection<PropositionalFormula<PSym>> operands) {
		super.signature = signature;
		super.operands = new ArrayList<>(operands);
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
		return (List<PropositionalFormula<PSym>>) Collections.unmodifiableList(operands);
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

	@Override
	public int hashCode() {
		return Objects.hash(operands, signature);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PropositionalOR))
			return false;
		PropositionalOR other = (PropositionalOR) obj;
		return Objects.equals(operands, other.operands) && Objects.equals(signature, other.signature);
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

	@Override
	public boolean isLogical() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String stringify() {
		// TODO Auto-generated method stub
		return null;
	}
}