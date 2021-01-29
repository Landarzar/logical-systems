package edu.cs.ai.alchourron.logic.syntax.terms;

import java.util.Collections;
import java.util.List;

import edu.cs.ai.alchourron.logic.SyntacticElement;

public class FunctionSymbol<F extends Enum<F>>  implements Term<F> {
	
	F symbol;
	int arity;
	List<Term<F>> children;
	
	public FunctionSymbol(F symbol, int arity, Term<F>... args) {
		this.symbol = symbol;
		this.arity = arity;
		this.children = List.of(args);
	}
	
	public FunctionSymbol(F symbol, int arity, List<Term<F>> args) {
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
	public String stringify() {
		return symbol.toString();
	}

	
	@Override
	public List<SyntacticElement> getSyntacticChildren() {
		return null;
	}

}
