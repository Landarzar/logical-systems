package edu.cs.ai.alchourron.logic.propositional;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import edu.cs.ai.alchourron.logic.Interpretation;

/***
 * Represents propositional interpretations.
 * 
 * @author Kai Sauerwald
 *
 * @param <PSym>
 *            The type of symbols used in the signature
 * @param <S>
 *            The type of the signature
 */
public class PropositionalInterpretation<PSym, S extends PropositionalSignature<PSym>> implements Interpretation<S> {

	S signature;
	Set<PSym> trueValued;

	/***
	 * Constructs a new interpretation
	 * 
	 * @param sig
	 *            The signature over which the interpretation is defined
	 * @param trueValued
	 *            The elements which are considered to be true
	 */
	public PropositionalInterpretation(S sig, PSym[] trueValued) {
		this.trueValued = new HashSet<>();
		for (int i = 0; i < trueValued.length; i++) {
			PSym v = trueValued[i];
			this.trueValued.add(v);
		}
	}

	/***
	 * Constructs a new interpretation
	 * 
	 * @param sig
	 *            The signature over which the interpretation is defined
	 * @param trueValued
	 *            The elements which are considered to be true
	 */
	public PropositionalInterpretation(S sig, Collection<PSym> trueValued) {
		this.trueValued = new HashSet<>(trueValued);
	}

	/***
	 * Checks where under this interpretation {@link symbol} is true.
	 * 
	 * @author Kai Sauerwald
	 * @param symbol
	 *            The symbol
	 */
	public boolean isTrue(PSym symbol) {
		return trueValued.contains(symbol);
	}

	/***
	 * Gets all variables which are true in this world.
	 * 
	 * @author Kai Sauerwald
	 */
	public Set<PSym> getAllTrue() {
		return Collections.unmodifiableSet(trueValued);
	}

	/***
	 * Checks where under this interpretation {@link symbol} is false.
	 * 
	 * @author Kai Sauerwald
	 * @param symbol
	 *            The symbol
	 */
	public boolean isFalse(PSym symbol) {
		return !isTrue(symbol);
	}

	/***
	 * Gets all variables which are false in this world.
	 * 
	 * @author Kai Sauerwald
	 */
	public Set<PSym> getAllFalse() {
		HashSet<PSym> set = new HashSet<>(signature.getSymbols());
		set.removeAll(trueValued);
		return Collections.unmodifiableSet(set);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs.ai.logic.Interpretation#getSignature()
	 */
	@Override
	public S getSignature() {
		return signature;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.trueValued.toString();
	}
}
