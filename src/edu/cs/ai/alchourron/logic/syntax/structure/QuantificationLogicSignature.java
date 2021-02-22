package edu.cs.ai.alchourron.logic.syntax.structure;

import java.util.Set;

import edu.cs.ai.alchourron.logic.Signature;

/****
 * This is a logical language with quantifiers
 * 
 * @author Kai Sauerwald
 *
 * @param <K> The type over which is quantificated
 * @param <Q> The kinds of Quantifiers
 * @param <V> The type for variables
 */
public interface QuantificationLogicSignature<Q, V> extends Signature {

	public Set<Q> getQuantifier();
}
