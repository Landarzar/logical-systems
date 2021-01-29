package edu.cs.ai.alchourron.logic.logics.predicatelogics;

import java.util.List;

import edu.cs.ai.alchourron.logic.syntax.structure.SecondOrderQuantificationLogic;

/***
 * Represents a propositional signature, which uses Elements of the type Psym as
 * symbol supply.
 * 
 * @author Kai Sauerwald
 *
 * @param <R> Relation Symbols
 * @param <K> Function Symbols
 * @param <V> Type for first-order variables
 * @param <P> Type for second-order variables
 */
public class SOSignature<R extends Enum<R>, K extends Enum<K>, V, P> extends FOSignature<R, K, V>
		implements SecondOrderQuantificationLogic<P> {

	/***
	 * Constructs a new signature
	 * 
	 * @author Kai Sauerwald
	 * @param predicateSymbols The symbols of this signature
	 */
	public SOSignature(R[] symbR, Integer[] arityR, K[] symbF, Integer[] arityF) {
		super(symbR, arityR, symbF, arityF);
	}

	/***
	 * Constructs a new signature
	 * 
	 * @author Kai Sauerwald
	 * @param symbols The symbols of predicates
	 * @param arity   The arity of predicates
	 */
	public SOSignature(List<R> symbols, List<Integer> arity) {
		super(symbols, arity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "<" + predicateSymbols.toString() + "," + functionSymbols + ">";
	}

	@Override
	public String toLaTeX() {
		return predicateSymbols.toString();
	}
}
