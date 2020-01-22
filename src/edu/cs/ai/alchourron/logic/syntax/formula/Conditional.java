package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.Objects;

import edu.cs.ai.alchourron.logic.syntax.Formula;
import edu.cs.ai.alchourron.logic.syntax.Signature;
import edu.cs.ai.alchourron.logic.syntax.SyntacticElement;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <S> Signature Type
 * @param <B> Formulas of the base logic
 */
public class Conditional<S extends Signature, B extends Formula<S>> implements Formula<S> {
	private B antecendence;
	private B consequence;

	public Conditional(B antecendence, B consequence) {
		this.antecendence = antecendence;
		this.consequence = consequence;
	}

	/***
	 * Returns the antecendence of this conditional
	 */
	public B getAntecendence() {
		return antecendence;
	}

	/***
	 * Returns the consequence of this conditional
	 */
	public B getConsequence() {
		return consequence;
	}
	
	@Override
	public String toString() {
		return "( " + consequence.toString() + " | " + antecendence.toString() + " )";
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
	
	@Override
	public String stringify() {
		// TODO Auto-generated method stub
		return toString();
	}
	
	@Override
	public boolean isAtom() {
		return true;
	}
	
	@Override
	public boolean isLogical() {
		return true;
	}

	@Override
	public SyntacticElement<S> getSyntaxTree() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S getSignature() {
		// TODO Auto-generated method stub
		return null;
	}
}