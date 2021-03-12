/**
 * 
 */
package edu.cs.ai.alchourron.logic.logics.predicatelogics;

import java.util.List;
import java.util.Set;

import edu.cs.ai.alchourron.logic.syntax.formula.ClassicalQuantifier;

/**
 * @author Kai Sauerwald
 *
 */
public class SOSignature<R, K, V, P> extends GeneralizedSOSignature<R, K, V, ClassicalQuantifier, P, ClassicalQuantifier> {

	/**
	 * @param quantifieres
	 * @param symbR
	 * @param arityR
	 * @param symbF
	 * @param arityF
	 * @param soquantifiers
	 */
	public SOSignature(R[] symbR, Integer[] arityR, K[] symbF, Integer[] arityF) {
		super(Set.of(ClassicalQuantifier.values()), symbR, arityR, symbF, arityF, Set.of(ClassicalQuantifier.values()));
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param quantifieres
	 * @param symbols
	 * @param arity
	 * @param soquantifiers
	 */
	public SOSignature(List<R> rsymbols, List<Integer> rarity, List<K> fsymbols, List<Integer> farity) {
		super(Set.of(ClassicalQuantifier.values()), rsymbols, rarity, fsymbols, farity, Set.of(ClassicalQuantifier.values()));
		// TODO Auto-generated constructor stub
	}

}
