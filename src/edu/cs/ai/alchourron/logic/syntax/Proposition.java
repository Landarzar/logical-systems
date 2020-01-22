package edu.cs.ai.alchourron.logic.syntax;

import java.util.List;

import edu.cs.ai.alchourron.logic.Signature;

/***
 * Represents predicates, for instance relations in first order logic or propositional atoms.
 * @author Kai Sauerwald
 *
 * @param <PSym> The type for predicate symbols
 * @param <S> The signature type
 */
public interface Proposition<PSym, S extends Signature>  extends SyntacticFormula<S> {
	
	
	/***
	 * Gets the symbol of this predicate.
	 * @author Kai Sauerwald
	 */
	PSym getSymbol();

	/***
	 * Returns the terms of this predicate
	 * @author Kai Sauerwald
	 */
	List<Term<S>> getTerms();
	
	
	/***
	 * Returns the arity of this predicate
	 * @author Kai Sauerwald
	 */
	int getArity();
	
	/*
	 * (non-Javadoc)
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	default boolean isLogical() {
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	default boolean isAtom() {
		return true;
	}
}
