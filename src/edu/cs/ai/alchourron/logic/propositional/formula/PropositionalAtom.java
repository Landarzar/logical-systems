package edu.cs.ai.alchourron.logic.propositional.formula;

import java.util.List;
import java.util.Objects;

import edu.cs.ai.alchourron.logic.propositional.PropositionalFormula;
import edu.cs.ai.alchourron.logic.propositional.PropositionalSignature;
import edu.cs.ai.alchourron.logic.syntax.Proposition;
import edu.cs.ai.alchourron.logic.syntax.SyntacticElement;
import edu.cs.ai.alchourron.logic.syntax.Term;

public class PropositionalAtom<PSym> extends PropositionalFormula<PSym>
		implements Proposition<PSym, PropositionalSignature<PSym>> {

	protected PSym symbol;
	protected PropositionalSignature<PSym> signature;

	/**
	 * Constructs a new instance of this class
	 * 
	 * @author Kai Sauerwald
	 */
	public PropositionalAtom(PropositionalSignature<PSym> signature, PSym sign) {
		this.symbol = sign;
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
		return symbol.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.logic.syntax.Predicate#getSymbol()
	 */
	@Override
	public PSym getSymbol() {
		return symbol;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.logic.syntax.Predicate#getTerms()
	 */
	@Override
	public List<Term<PropositionalSignature<PSym>>> getTerms() {
		return List.of();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.logic.syntax.Predicate#getArity()
	 */
	@Override
	public int getArity() {
		return 0;
	}

	

//	@Override
//	public boolean equals(Object obj) {
//			if (obj instanceof PropositionalAtom<?>) {
//				PropositionalAtom<PSym> other = (PropositionalAtom<PSym>) obj;
//				return other.symbol.equals(this.symbol);
//			}
//
//		return false;
//	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return symbol.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(signature, symbol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PropositionalAtom))
			return false;
		PropositionalAtom other = (PropositionalAtom) obj;
		return Objects.equals(signature, other.signature) && Objects.equals(symbol, other.symbol);
	}

	@Override
	public String toLaTeX() {
		return symbol.toString();
	}
}