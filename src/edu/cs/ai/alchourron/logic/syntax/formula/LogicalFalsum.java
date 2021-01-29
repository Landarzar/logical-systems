package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.SyntacticElement;
import edu.cs.ai.alchourron.logic.syntax.structure.FalsumLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.PropositionLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.terms.Term;

/***
 * Represents bottom
 * @author Kai Sauerwald
 *
 * @param <S> The signature type
 */
public class LogicalFalsum<S extends FalsumLogicSignature>  extends Formula<S> {

	
	public LogicalFalsum(S signature) {
		super(signature);
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	public boolean isLogical() {
		return false;
	}
	
	/*
	 * (non-Javadoc)
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
