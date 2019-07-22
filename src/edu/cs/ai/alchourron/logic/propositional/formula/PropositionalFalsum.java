package edu.cs.ai.alchourron.logic.propositional.formula;

import java.util.Objects;

import edu.cs.ai.alchourron.logic.propositional.PropositionalFormula;
import edu.cs.ai.alchourron.logic.propositional.PropositionalSignature;
import edu.cs.ai.alchourron.logic.syntax.SyntacticElement;
import edu.cs.ai.alchourron.logic.syntax.SyntacticFormula;

public class PropositionalFalsum<PSym> extends PropositionalFormula<PSym> implements SyntacticFormula<PropositionalSignature<PSym>> {

	protected PropositionalSignature<PSym> signature;

	/**
	 * Constructs a new instance of this class
	 * 
	 * @author Kai Sauerwald
	 */
	public PropositionalFalsum(PropositionalSignature<PSym> signature) {
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
		return "False";
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(signature);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PropositionalFalsum))
			return false;
		PropositionalFalsum other = (PropositionalFalsum) obj;
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
		return "\\bot";
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
}