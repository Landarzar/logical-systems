package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;
import java.util.Objects;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.Signature;
import edu.cs.ai.alchourron.logic.SyntacticElement;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <S> Signature Type
 * @param <B> Formulas of the base logic
 */
public class FormulaConditional<S extends Signature, B extends Formula<S>> extends Formula<S> {
	private B antecendence;
	private B consequence;

	public FormulaConditional(S signature, B antecendence, B consequence) {
		super(signature);
		this.antecendence = antecendence;
		this.consequence = consequence;
	}

	/***
	 * Returns the antecendence of this conditional
	 */
	public B getAntecendence() {
		return antecendence;
	}

	/***
	 * Returns the consequence of this conditional
	 */
	public B getConsequence() {
		return consequence;
	}

	@Override
	public String toString() {
		return "( " + consequence.toString() + " | " + antecendence.toString() + " )";
	}

	@Override
	public int hashCode() {
		return Objects.hash(antecendence, consequence);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FormulaConditional))
			return false;
		@SuppressWarnings("unchecked")
		FormulaConditional<S, B> other = (FormulaConditional<S, B>) obj;
		return Objects.equals(antecendence, other.antecendence) && Objects.equals(consequence, other.consequence);
	}

	@Override
	public String stringify() {
		// TODO Auto-generated method stub
		return toString();
	}

	@Override
	public boolean isAtom() {
		return true;
	}

	@Override
	public boolean isLogical() {
		return true;
	}

	@Override
	public List<SyntacticElement> getSyntacticChildren() {
		return List.of(this.getAntecendence(), this.getConsequence());
	}

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see edu.cs.ai.alchourron.logic.Formula#getSyntaxTree()
//	 */
//	@Override
//	public List<Formula<S>> getSyntaxTree() throws UnsupportedOperationException {
//		return List.of(this.getAntecendence(),this.getConsequence());
//	}

	@Override
	public S getSignature() {
		// TODO Auto-generated method stub
		return null;
	}
}
