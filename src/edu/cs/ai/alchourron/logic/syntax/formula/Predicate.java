package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.Collections;
import java.util.List;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.structure.RelationLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.VariableTermLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.terms.Term;

/***
 * Represents predicates, for instance relations in first order logic. However,
 * they are not predicate variables.
 * 
 * @author Kai Sauerwald
 *
 * @param <R> The type for predicate symbols
 * @param <S> The signature type
 */
public class Predicate<R extends Enum<R>, F extends Enum<F>, V, S extends VariableTermLogicSignature<V> & RelationLogicSignature<R, F>>
		extends Formula<S> {
	
	protected R symbol;
	protected List<Term<F, V>> terms;

	/***
	 * Constructs a new atomar predicate
	 * @param sig
	 * @param symbol
	 * @param terms
	 */
	public Predicate(S signature, R symbol, List<Term<F, V>> terms) {
		super(signature);
		this.symbol = symbol;
		this.terms = Collections.unmodifiableList(terms);
	}

	/***
	 * Gets the symbol of this predicate.
	 * 
	 * @author Kai Sauerwald
	 */
	public R getSymbol() {
		return symbol;
	}

	/***
	 * Returns the terms of this predicate
	 * 
	 * @author Kai Sauerwald
	 */
	public List<Term<F, V>> getTerms() {
		return terms;
	}

	/***
	 * Returns the arity of this predicate
	 * 
	 * @author Kai Sauerwald
	 */
	public int getArity() {
		return terms.size();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S getSignature() {
		return signature;
	}
}
