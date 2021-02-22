package edu.cs.ai.alchourron.logic.syntax.terms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import edu.cs.ai.alchourron.logic.Signature;
import edu.cs.ai.alchourron.logic.SyntacticElement;
import edu.cs.ai.alchourron.logic.syntax.structure.FunctionTermLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.VariableTermLogicSignature;

public class FunctionTerm<K,S extends FunctionTermLogicSignature<K>> implements Term<S> {

	K symbol;
	int arity;
	List<Term<S>> children;

	public FunctionTerm(K symbol, int arity, Term<S>... args) {
		this.symbol = symbol;
		this.arity = arity;
		this.children = List.of(args);
	}

	public FunctionTerm(K symbol, int arity, List<Term<S>> args) {
		this.symbol = symbol;
		this.arity = arity;
		this.children = Collections.unmodifiableList(args);
	}

	/***
	 * Gets the symbol of this function.
	 * 
	 * @author Kai Sauerwald
	 */
	public K getSymbol() {
		return symbol;
	}

	/***
	 * Returns the arity of this function.
	 * 
	 * @author Kai Sauerwald
	 */
	public int getArity() {
		return arity;
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

	@Override
	public boolean isAtom() {
		return arity == 0;
	}

	@Override
	public String toString() {
		return symbol.toString() + getSubTerms().toString();
	}

	public List<Term<S>> getSubTerms() {
		return children;
	}

	@Override
	public List<SyntacticElement> getSyntacticChildren() {
		return new ArrayList<SyntacticElement>(children);
	}
	
	@Override
	public boolean isSignatureMatching(
			S signature) {
		return signature.getFunctionSymbols().contains(this.getSymbol()) && signature.getFunctionArity(getSymbol()) == getArity();
	}


	@Override
	public int hashCode() {
		return Objects.hash(arity, children, symbol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FunctionTerm))
			return false;
		FunctionTerm other = (FunctionTerm) obj;
		return arity == other.arity && Objects.equals(children, other.children) && Objects.equals(symbol, other.symbol);
	}
}
