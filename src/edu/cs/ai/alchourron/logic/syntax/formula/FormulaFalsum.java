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
public class FormulaFalsum<S extends FalsumLogicSignature> extends FormulaAtom<S> {

	public FormulaFalsum(S signature) {
		super(signature);
	}

	@Override
	public String stringify() {
		return "BOT";
	}
}
