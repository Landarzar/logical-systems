package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;

import edu.cs.ai.alchourron.logic.syntax.Formula;
import edu.cs.ai.alchourron.logic.syntax.signature.FalsumLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.signature.PropositionLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.terms.Term;

/***
 * Represents bottom
 * @author Kai Sauerwald
 *
 * @param <S> The signature type
 */
public class LogicalFalsum<S extends FalsumLogicSignature>  implements Formula<S> {

	S signature;
	
	public LogicalFalsum(S signature) {
		this.signature = signature;
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

	@Override
	public S getSignature() {
		// TODO Auto-generated method stub
		return signature;
	}
}
