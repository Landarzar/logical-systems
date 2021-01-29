package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.structure.RelationLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.terms.Term;

/***
 * Represents predicates, for instance relations in first order logic or propositional atoms.
 * @author Kai Sauerwald
 *
 * @param <R> The type for predicate symbols
 * @param <S> The signature type
 */
public abstract class Predicate<R extends Enum<R>,F extends Enum<F>, V, S extends RelationLogicSignature<R,F>>  extends Formula<S> {
	
	
	/***
	 * Gets the symbol of this predicate.
	 * @author Kai Sauerwald
	 */
	public abstract R getSymbol();

	/***
	 * Returns the terms of this predicate
	 * @author Kai Sauerwald
	 */
	public abstract List<Term<F>> getTerms();
	
	
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
