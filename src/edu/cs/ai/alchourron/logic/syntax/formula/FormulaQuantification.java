package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.structure.QuantificationLogicSignature;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <Q> The type which are bound by this quantifier (Variables, Relations,
 *            ...)
 */
public class FormulaQuantification<Q, V, S extends QuantificationLogicSignature<Q, V>> extends LogicalOperator<S> {

	private Q quantifiyer;
	private V variable;
	private Formula<S> quantified;

	public FormulaQuantification(S signature, Q quantifiyer, V variable, Formula<S> quantified) {
		super(signature);
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Formula<S>> getOperands() {
		// TODO Auto-generated method stub
		return List.of(this.quantified);
	}

	@Override
	public String toString() {
		return quantifiyer.toString() + " " + variable + ". " + quantified;
	}
}
