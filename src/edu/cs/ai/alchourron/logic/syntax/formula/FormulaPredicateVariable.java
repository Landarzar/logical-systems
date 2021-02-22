package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.structure.PredicateLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.SecondOrderQuantificationLogic;
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
public class FormulaPredicateVariable<QSO, P, S extends SecondOrderQuantificationLogic<P, QSO>>
		implements Formula<S> {

	protected P variable;
	protected List<Term<S>> terms;

	/***
	 * Constructs a new atomar predicate
	 * 
	 * @param sig
	 * @param symbol
	 * @param terms
	 */
	public FormulaPredicateVariable(P symbol, List<Term<S>> terms) {
		this.variable = symbol;
		this.terms = Collections.unmodifiableList(terms);
	}

	/***
	 * Gets the symbol of this predicate.
	 * 
	 * @author Kai Sauerwald
	 */
	public P getVariable() {
		return variable;
	}

	/***
	 * Gets the symbol of this predicate.
	 * 
	 * @author Kai Sauerwald
	 */
	public P getSymbol() {
		return variable;
	}

	/***
	 * Returns the terms of this predicate
	 * 
	 * @author Kai Sauerwald
	 */
	public List<Term<S>> getTerms() {
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
	public String toString() {

		Iterator<Term<S>> it = this.getTerms().iterator();
		if (!it.hasNext())
			return this.variable.toString();

		StringBuilder sb = new StringBuilder();
		sb.append(this.variable);
		sb.append('(');
		for (;;) {
			Term<S> e = it.next();
			sb.append(e == this ? "(this Collection)" : e);
			if (!it.hasNext())
				return sb.append(')').toString();
			sb.append(',').append(' ');
		}
	}
	
	@Override
	public boolean isSignatureMatching(S signature) throws UnsupportedOperationException {
		return Formula.super.isSignatureMatching(signature);
	}

	@Override
	public int hashCode() {
		return Objects.hash(terms, variable);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FormulaPredicateVariable))
			return false;
		FormulaPredicateVariable other = (FormulaPredicateVariable) obj;
		return Objects.equals(terms, other.terms) && Objects.equals(variable, other.variable);
	}
}
