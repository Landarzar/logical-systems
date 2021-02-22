package edu.cs.ai.alchourron.logic.logics.predicatelogics;

import java.util.List;
import java.util.Set;

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
public class GeneralizedSOSignature<R, K, V, Q, P, QSO> extends GeneralizedFOSignature<R, K, V, Q>
		implements SecondOrderQuantificationLogic<P, QSO>  {

	private Set<QSO> soquantifiers;
	
	/***
	 * Constructs a new signature
	 * 
	 * @author Kai Sauerwald
	 * @param predicateSymbols The symbols of this signature
	 */
	public GeneralizedSOSignature(Set<Q> quantifieres, R[] symbR, Integer[] arityR, K[] symbF, Integer[] arityF, Set<QSO> soquantifiers) {
		super(quantifieres, symbR, arityR, symbF, arityF);
		this.soquantifiers = soquantifiers;
	}

	/***
	 * Constructs a new signature
	 * 
	 * @author Kai Sauerwald
	 * @param symbols The symbols of predicates
	 * @param arity   The arity of predicates
	 */
	public GeneralizedSOSignature(Set<Q> quantifieres, List<R> symbols, List<Integer> arity, Set<QSO> soquantifiers) {
		super(quantifieres, symbols, arity);
		this.soquantifiers = soquantifiers;
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

	@Override
	public Set<QSO> getSOQuantifier() {
		return soquantifiers;
	}
}
