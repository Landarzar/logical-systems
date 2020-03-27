package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;

import edu.cs.ai.alchourron.logic.syntax.Formula;
import edu.cs.ai.alchourron.logic.syntax.signature.PropositionLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.signature.VerumLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.terms.Term;

/***
 * Represents tautologies
 * @author Kai Sauerwald
 *
 * @param <S> The signature type
 */
public class LogicalVerum<S extends VerumLogicSignature> extends Formula<S> {

	S signature;
	
	public LogicalVerum(S signature) {
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
		return "TOP";
	}

	@Override
	public S getSignature() {
		// TODO Auto-generated method stub
		return signature;
	}
}
