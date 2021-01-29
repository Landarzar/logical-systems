package edu.cs.ai.alchourron.logic.syntax.terms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.cs.ai.alchourron.logic.SyntacticElement;

public class FunctionTerm<F extends Enum<F>,V>  implements Term<F,V> {
	
	F symbol;
	int arity;
	List<Term<F,V>> children;
	
	public FunctionTerm(F symbol, int arity, Term<F,V>... args) {
		this.symbol = symbol;
		this.arity = arity;
		this.children = List.of(args);
	}
	
	public FunctionTerm(F symbol, int arity, List<Term<F,V>> args) {
		this.symbol = symbol;
		this.arity = arity;
		this.children = Collections.unmodifiableList(args);
	}
	
	/***
	 * Gets the symbol of this function.
	 * @author Kai Sauerwald
	 */
	public F getSymbol() {
		return symbol;
	}
	

	/***
	 * Returns the arity of this function.
	 * @author Kai Sauerwald
	 */
	public int getArity() {
		return arity;
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	public boolean isLogical() {
		return false;
	}

	@Override
	public boolean isAtom() {
		return arity == 0;
	}

	@Override
	public String toString() {
		return symbol.toString() + getSubTerms().toString();
	}

	public List<Term<F, V>> getSubTerms() {
		return children;
	}
	
	
	
	@Override
	public List<SyntacticElement> getSyntacticChildren() {
		return new ArrayList<SyntacticElement>(children);
	}

}
