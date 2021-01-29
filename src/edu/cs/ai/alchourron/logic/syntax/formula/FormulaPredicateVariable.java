package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.structure.RelationLogicSignature;
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
public class FormulaPredicateVariable<R extends Enum<R>, K extends Enum<K>, V, S extends SecondOrderQuantificationLogic<V> & RelationLogicSignature<R, K>>
		extends Formula<S> {

	protected V variable;
	protected List<Term<K, V>> terms;

	/***
	 * Constructs a new atomar predicate
	 * 
	 * @param sig
	 * @param symbol
	 * @param terms
	 */
	public FormulaPredicateVariable(S signature, V symbol, List<Term<K, V>> terms) {
		super(signature);
		this.variable = variable;
		this.terms = Collections.unmodifiableList(terms);
	}

	/***
	 * Gets the symbol of this predicate.
	 * 
	 * @author Kai Sauerwald
	 */
	public V getVariable() {
		return variable;
	}

	/***
	 * Gets the symbol of this predicate.
	 * 
	 * @author Kai Sauerwald
	 */
	public V getSymbol() {
		return variable;
	}

	/***
	 * Returns the terms of this predicate
	 * 
	 * @author Kai Sauerwald
	 */
	public List<Term<K, V>> getTerms() {
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

		Iterator<Term<K, V>> it = this.getTerms().iterator();
		if (!it.hasNext())
			return this.variable.toString();

		StringBuilder sb = new StringBuilder();
		sb.append(this.variable);
		sb.append('(');
		for (;;) {
			Term<K, V> e = it.next();
			sb.append(e == this ? "(this Collection)" : e);
			if (!it.hasNext())
				return sb.append(')').toString();
			sb.append(',').append(' ');
		}
	}

	@Override
	public S getSignature() {
		return signature;
	}
}
