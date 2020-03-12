package edu.cs.ai.alchourron.logic.propositional.formula;

import java.util.List;
import java.util.Objects;

import edu.cs.ai.alchourron.logic.propositional.PropositionalFormula;
import edu.cs.ai.alchourron.logic.propositional.PropositionalSignature;
import edu.cs.ai.alchourron.logic.syntax.SyntacticElement;
import edu.cs.ai.alchourron.logic.syntax.formula.Proposition;
import edu.cs.ai.alchourron.logic.syntax.terms.Term;

public class PropositionalVerum<PSym>  
		 implements PropositionalFormula<PSym>, Proposition<PSym, PropositionalSignature<PSym>> {

	protected PropositionalSignature<PSym> signature;

	/**
	 * Constructs a new instance of this class
	 * 
	 * @author Kai Sauerwald
	 */
	public PropositionalVerum(PropositionalSignature<PSym> signature) {
		this.signature = signature;
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
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#stringify()
	 */
	@Override
	public String stringify() {
		return "T";
	}

	@Override
	public int hashCode() {
		return Objects.hash(signature);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PropositionalVerum))
			return false;
		PropositionalVerum other = (PropositionalVerum) obj;
		return Objects.equals(signature, other.signature);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return stringify();
	}

	@Override
	public String toLaTeX() {
		return "\\top";
	}

	@Override
	public boolean isAtom() {
		return true;
	}

	@Override
	public boolean isLogical() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public PSym getSymbol() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public List<Term<PropositionalSignature<PSym>>> getTerms() {
		// TODO Auto-generated method stub
		return List.of();
	}

	@Override
	public int getArity() {
		return 0;
	}
}