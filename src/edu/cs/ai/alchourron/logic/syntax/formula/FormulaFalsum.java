package edu.cs.ai.alchourron.logic.syntax.formula;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.structure.FalsumLogicSignature;

/***
 * Represents bottom
 * 
 * @author Kai Sauerwald
 *
 * @param <S> The signature type
 */
public class FormulaFalsum<S extends FalsumLogicSignature> extends Formula<S> {

	public FormulaFalsum(S signature) {
		super(signature);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	public boolean isLogical() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	public boolean isAtom() {
		return true;
	}

	@Override
	public String stringify() {
		return "BOT";
	}
}
