package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import edu.cs.ai.alchourron.logic.syntax.Formula;
import edu.cs.ai.alchourron.logic.syntax.Signature;
import edu.cs.ai.alchourron.logic.syntax.SyntacticElement;
import edu.cs.ai.alchourron.logic.syntax.signature.DisjunctionLogicSignature;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <S> The Signature
 */
public class LogicalOR<S extends DisjunctionLogicSignature>  implements LogicalOperator<S> {
	protected S signature;
	ArrayList<Formula<S>> operands;

	/**
	 * Constructs a new instance of this class
	 * 
	 * @author Kai Sauerwald
	 */
	public LogicalOR(S signature,
			Collection<Formula<S>> operands) {
		this.signature = signature;
		this.operands = new ArrayList<>(operands);
	}

	/**
	 * Constructs a new instance of this class
	 * 
	 * @author Kai Sauerwald
	 */
	@SafeVarargs
	public LogicalOR(S signature, Formula<S>... operands) {
		this.signature = signature;
		this.operands = new ArrayList<>();
		for (Formula<S> op : operands) {
			this.operands.add(op);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.logic.Formula#getSyntaxTree()
	 */
	@Override
	public SyntacticElement<S> getSyntaxTree() throws UnsupportedOperationException {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.logic.Formula#getSignature()
	 */
	@Override
	public S getSignature() {
		return signature;
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
		return Objects.hash(operands, signature);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof LogicalAND<?>))
			return false;
		LogicalOR<S> other = (LogicalOR<S>) obj;
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

		for (Formula<S> propositionalFormula : operands) {
			if (first) {
				builder.append(propositionalFormula.toString());
				continue;
			}
			builder.append(" AND ");
			first = false;
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
//		builder.append("(");

		boolean first = true;

		for (Formula<S> propositionalFormula : operands) {
			if (first) {
				builder.append(propositionalFormula.toLaTeX());
				first = false;
				continue;
			}
//			builder.append(" \\land "); // Short form
			builder.append(propositionalFormula.toLaTeX());
		}

//		builder.append(")");
		return builder.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * @see cs.ai.logic.syntax.SyntacticElement#stringify()
	 */
	@Override
	public String stringify() {
		return toString();
	}
}
