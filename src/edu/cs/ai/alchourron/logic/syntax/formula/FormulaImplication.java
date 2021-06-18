package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;
import java.util.Objects;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.structure.ImplicationLogicSignature;

public class FormulaImplication<S extends ImplicationLogicSignature> extends LogicalOperator<S> {

	Formula<S> premise;
	Formula<S> conclusion;

	public FormulaImplication(Formula<S> premise, Formula<S> conclusion) {
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
	public List<Formula<S>> getOperands() {
		return List.of(premise, conclusion);
	}

	@Override
	public String toString() {
		return premise.toLaTeX() + " => " + conclusion.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(conclusion, premise);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FormulaImplication))
			return false;
		FormulaImplication other = (FormulaImplication) obj;
		return Objects.equals(conclusion, other.conclusion) && Objects.equals(premise, other.premise);
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

		builder.append(getPremise());
		builder.append(" \\rightarrow ");
		builder.append(getConclusion());

//		builder.append(")");
		return builder.toString();
	}
}
