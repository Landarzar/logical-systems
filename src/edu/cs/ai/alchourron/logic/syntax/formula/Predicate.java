package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;

import edu.cs.ai.alchourron.logic.syntax.Formula;
import edu.cs.ai.alchourron.logic.syntax.signature.PredicateLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.terms.Term;

/***
 * Represents predicates, for instance relations in first order logic or propositional atoms.
 * @author Kai Sauerwald
 *
 * @param <PSym> The type for predicate symbols
 * @param <S> The signature type
 */
public abstract class Predicate<PSym, S extends PredicateLogicSignature>  extends Formula<S> {
	
	
	/***
	 * Gets the symbol of this predicate.
	 * @author Kai Sauerwald
	 */
	public abstract PSym getSymbol();

	/***
	 * Returns the terms of this predicate
	 * @author Kai Sauerwald
	 */
	public abstract List<Term<S>> getTerms();
	
	
	/***
	 * Returns the arity of this predicate
	 * @author Kai Sauerwald
	 */
	public abstract int getArity();
	
	/*
	 * (non-Javadoc)
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	public boolean isLogical() {
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	public boolean isAtom() {
		return true;
	}

	@Override
	public String stringify() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S getSignature() {
		// TODO Auto-generated method stub
		return null;
	}
}
