package edu.cs.ai.alchourron.logic.syntax.signature;

import edu.cs.ai.alchourron.logic.syntax.Signature;

/****
 * This is a logical language with quantifiers
 * @author Kai Sauerwald
 *
 * @param <K> The kinds of Quantifiers
 * @param <Q> The type over which is quantificated
 */
public interface QuantificationLogicSignature<K extends Enum<K>,Q> extends Signature {

}
