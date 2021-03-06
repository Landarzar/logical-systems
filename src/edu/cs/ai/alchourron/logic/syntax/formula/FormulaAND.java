package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.structure.ConjunctionLogicSignature;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <S> The Signature
 */
public class FormulaAND<S extends ConjunctionLogicSignature> extends LogicalOperator<S> {

	ArrayList<Formula<S>> operands;

	/**
	 * Constructs a new instance of this class
	 * 
	 * @author Kai Sauerwald
	 */
	public FormulaAND(Collection<Formula<S>> operands) { 
		this.operands = new ArrayList<>(operands);
	}

	/**
	 * Constructs a new instance of this class
	 * 
	 * @author Kai Sauerwald
	 */
	@SafeVarargs
	public FormulaAND(Formula<S>... operands) {
		this.operands = new ArrayList<>();
		for (Formula<S> op : operands) {
			this.operands.add(op);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.logic.syntax.LogicalOperator#getOperands()
	 */
	@Override
	public List<Formula<S>> getOperands() {
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

	@Override
	public int hashCode() {
		return Objects.hash(operands);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FormulaAND<?>))
			return false;
		@SuppressWarnings("unchecked")
		FormulaAND<S> other = (FormulaAND<S>) obj;
		return Objects.equals(operands, other.operands);
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

		for (Formula<S> propositionalFormula : operands) {
			if (first) {
				builder.append(propositionalFormula.toString());
				first = false;
				continue;
			}
			builder.append(" AND ");
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
		return super.toLaTeX("\\land");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs.ai.logic.syntax.SyntacticElement#stringify()
	 */
	@Override
	public String stringify() {
		return toString();
	}
}
