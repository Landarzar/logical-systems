package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.SyntacticElement;
import edu.cs.ai.alchourron.logic.syntax.structure.PropositionLogicSignature;

/***
 * Represents predicates, for instance relations in first order logic or
 * propositional atoms.
 * 
 * @author Kai Sauerwald
 *
 * @param <PSym> The type for predicate symbols
 * @param <S>    The signature type
 */
public class Proposition<PSym, S extends PropositionLogicSignature<PSym>> extends Formula<S> {

	protected PSym symbol;

	public Proposition(S signature, PSym symbol) {
		super(signature);
		this.symbol = symbol;
	}

	/***
	 * Gets the symbol of this predicate.
	 * 
	 * @author Kai Sauerwald
	 */
	public PSym getSymbol() {
		return symbol;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	public boolean isLogical() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	public boolean isAtom() {
		return true;
	}

	@Override
	public String stringify() {
		return symbol.toString();
	}

	@Override
	public S getSignature() {
		return signature;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return stringify();
	}
}
