package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.Collections;
import java.util.Iterator;
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
public class FormulaPredicate<R extends Enum<R>, F extends Enum<F>, V, S extends VariableTermLogicSignature<V> & RelationLogicSignature<R, F>>
		extends FormulaAtom<S> {

	protected R symbol;
	protected List<Term<F, V>> terms;

	/***
	 * Constructs a new atomar predicate
	 * 
	 * @param sig
	 * @param symbol
	 * @param terms
	 */
	public FormulaPredicate(S signature, R symbol, List<Term<F, V>> terms) {
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

	@Override
	public String toString() {

		Iterator<Term<F, V>> it = this.getTerms().iterator();
		if (!it.hasNext())
			return this.symbol.toString();

		StringBuilder sb = new StringBuilder();
		sb.append(this.symbol);
		sb.append('(');
		for (;;) {
			Term<F, V> e = it.next();
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
