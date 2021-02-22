package edu.cs.ai.alchourron.logic.syntax.formula;

import java.io.Serializable;

import edu.cs.ai.alchourron.logic.syntax.structure.FalsumLogicSignature;

/***
 * Represents bottom
 * 
 * @author Kai Sauerwald
 *
 * @param <S> The signature type
 */
public class FormulaFalsum<S extends FalsumLogicSignature> extends FormulaAtom<S> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5499116383235467303L;

	@Override
	public String stringify() {
		return "BOT";
	}

	@Override
	public int hashCode() {
		return (int) -5499116383235467303L;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FormulaFalsum<?>)
			return true;
		return super.equals(obj);
	}
}
