package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.structure.PredicateLogicSignature;
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
public class FormulaPredicate<R, S extends PredicateLogicSignature<R>>
		extends FormulaAtom<S> {

	protected R symbol;
	protected List<Term<S>> terms;

	/***
	 * Constructs a new atomar predicate
	 * 
	 * @param sig
	 * @param symbol
	 * @param terms
	 */
	public FormulaPredicate(R symbol, List<Term<S>> terms) {
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

	@Override
	public String toString() {

		Iterator<Term<S>> it = this.getTerms().iterator();
		if (!it.hasNext())
			return this.symbol.toString();

		StringBuilder sb = new StringBuilder();
		sb.append(this.symbol);
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
	public int hashCode() {
		return Objects.hash(symbol, terms);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FormulaPredicate))
			return false;
		FormulaPredicate other = (FormulaPredicate) obj;
		return Objects.equals(symbol, other.symbol) && Objects.equals(terms, other.terms);
	}
}
