package cs.ai.logic.propositional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import cs.ai.logic.Interpretation;

/***
 * Represents propositional interpretations.
 * @author Kai Sauerwald
 *
 * @param <V> The type of symbols used in the signature
 * @param <S> The type of the signature
 */
public class PropositionalInterpretation<V, S extends PropositionalSignature<V>> implements Interpretation<S> {

	S signature;
	Set<V> trueValued;
	
	/***
	 * Constructs a new interpretation
	 * @param sig The signature over which the interpretation is defined
	 * @param trueValued The elements which are considered to be true
	 */
	public PropositionalInterpretation(S sig, V[] trueValued) {
		this.trueValued = new HashSet<>();
		for (int i = 0; i < trueValued.length; i++) {
			V v = trueValued[i];
			this.trueValued.add(v);
		}
	}
	
	/***
	 * Constructs a new interpretation
	 * @param sig The signature over which the interpretation is defined
	 * @param trueValued The elements which are considered to be true
	 */
	public PropositionalInterpretation(S sig, Collection<V> trueValued) {
		this.trueValued = new HashSet<>(trueValued);
	}
	
	/***
	 * Checks where under this interpretation {@link symbol} is true.
	 * @author Kai Sauerwald
	 * @param symbol The symbol
	 */
	public boolean isTrue(V symbol) {
		return trueValued.contains(symbol);
	}
	
	/***
	 * Checks where under this interpretation {@link symbol} is false.
	 * @author Kai Sauerwald
	 * @param symbol The symbol
	 */
	public boolean isFalse(V symbol) {
		return !isTrue(symbol);
	}

	/*
	 * (non-Javadoc)
	 * @see cs.ai.logic.Interpretation#getSignature()
	 */
	@Override
	public S getSignature() {
		return signature;
	}

}
