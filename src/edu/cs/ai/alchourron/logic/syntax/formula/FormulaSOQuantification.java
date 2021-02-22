package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.structure.SecondOrderQuantificationLogic;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <Q> The type of quantifiers
 */
public class FormulaSOQuantification<QSO, P, S extends SecondOrderQuantificationLogic<P, QSO>> extends LogicalOperator<S> {

	private QSO quantifiyer;
	private P variable;
	private Formula<S> quantified;

	public FormulaSOQuantification(QSO quantifiyer, P variable, Formula<S> quantified) {
		this.quantifiyer = quantifiyer;
		this.variable = variable;
		this.quantified = quantified;
	}

	/***
	 * Gets the parameters of this quantifier
	 */
	public QSO getQuantifyer() {
		return quantifiyer;
	}

	/***
	 * Gets the variable of this quantifier
	 */
	public P getVariables() {
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
		// TODO Auto-generated method stub
		return List.of(this.quantified);
	}

	@Override
	public String toString() {
		return quantifiyer.toString() + " " + variable + ". " + quantified;
	}
	
	@Override
	public boolean isSignatureMatching(S signature) throws UnsupportedOperationException {
		return signature.getSOQuantifier().contains(quantifiyer) && super.isSignatureMatching(signature);
	}
}
