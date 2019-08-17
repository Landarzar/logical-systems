package edu.cs.ai.alchourron.logic.conditional;

import java.util.Objects;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.Signature;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <S> Signature Type
 * @param <F> Formula Type
 */
public class Conditional<S extends Signature, F extends Formula<S>> {
	private F antecendence;
	private F consequence;

	public Conditional(F antecendence, F consequence) {
		this.antecendence = antecendence;
		this.consequence = consequence;
	}

	/***
	 * Returns the antecendence of this conditional
	 */
	public F getAntecendence() {
		return antecendence;
	}

	/***
	 * Returns the consequence of this conditional
	 */
	public F getConsequence() {
		return consequence;
	}

	@Override
	public int hashCode() {
		return Objects.hash(antecendence, consequence);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Conditional))
			return false;
		Conditional other = (Conditional) obj;
		return Objects.equals(antecendence, other.antecendence) && Objects.equals(consequence, other.consequence);
	}
	
	
}
