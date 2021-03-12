/**
 * 
 */
package edu.cs.ai.alchourron.logic.logics.predicatelogics;

import java.util.List;
import java.util.Set;

import edu.cs.ai.alchourron.logic.syntax.formula.ClassicalQuantifier;

/***
 * Represents a first order like signature
 * 
 * @author Kai Sauerwald
 *
 * @param <R> Relation Symbols
 * @param <K> Function Symbols
 * @param <V> Variable Type
 */
public class FOSignature<R, K, V> extends GeneralizedFOSignature<R, K, V, ClassicalQuantifier> {

	/**
	 * @param quantifieres
	 * @param symbR
	 * @param arityR
	 * @param symbF
	 * @param arityF
	 */
	public FOSignature(R[] symbR, Integer[] arityR, K[] symbF,
			Integer[] arityF) {
		super(Set.of(ClassicalQuantifier.values()), symbR, arityR, symbF, arityF);
	}

	/**
	 * @param quantifieres
	 * @param symbols
	 * @param arity
	 */
	public FOSignature(List<R> rsymbols, List<Integer> rarity, List<K> fsymbols, List<Integer> farity) {
		super(Set.of(ClassicalQuantifier.values()), rsymbols, rarity,fsymbols,farity);
	}

}
