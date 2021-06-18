package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;
import java.util.Objects;

import edu.cs.ai.alchourron.LaTeX;
import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.structure.QuantificationLogicSignature;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <Q> The possible quantifies
 * @param <V> The variable type
 */
public class FormulaQuantification<Q, V, S extends QuantificationLogicSignature<Q, V>> extends LogicalOperator<S> {

	private Q quantifiyer;
	private V variable;
	private Formula<S> quantified;

	public FormulaQuantification(Q quantifiyer, V variable, Formula<S> quantified) {
		this.quantifiyer = quantifiyer;
		this.variable = variable;
		this.quantified = quantified;
	}

	/***
	 * Gets the parameters of this quantifier
	 */
	public Q getQuantifyer() {
		return quantifiyer;
	}

	/***
	 * Gets the variable of this quantifier
	 */
	public V getVariables() {
		return variable;
	}

	/***
	 * Gets the formula which is quantified
	 * 
	 * @return
	 */
	public Formula<S> getQuantified() {
		return quantified;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	public boolean isLogical() {
		return true;
	}

	@Override
	public boolean isAtom() {
		return false;
	}

	@Override
	public List<Formula<S>> getOperands() {
		return List.of(this.quantified);
	}

	@Override
	public String toString() {
		return quantifiyer.toString() + " " + variable + ". " + quantified;
	}

	@Override
	public String toLaTeX() {
		if (!(getQuantifyer() instanceof LaTeX))
			return quantifiyer.toString() + " " + variable + ". " + quantified.toLaTeX();
		LaTeX qtex = (LaTeX) getQuantifyer();
		
		return qtex.toLaTeX() + " " + variable + ". " + quantified.toLaTeX();
	}

	@Override
	public boolean isSignatureMatching(S signature) throws UnsupportedOperationException {
		return signature.getQuantifier().contains(quantifiyer) && super.isSignatureMatching(signature);
	}

	@Override
	public int hashCode() {
		return Objects.hash(quantified, quantifiyer, variable);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FormulaQuantification))
			return false;
		FormulaQuantification other = (FormulaQuantification) obj;
		return Objects.equals(quantified, other.quantified) && Objects.equals(quantifiyer, other.quantifiyer)
				&& Objects.equals(variable, other.variable);
	}
}
