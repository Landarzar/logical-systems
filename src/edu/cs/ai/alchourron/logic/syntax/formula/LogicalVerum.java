package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.structure.PropositionLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.VerumLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.terms.Term;

/***
 * Represents tautologies
 * @author Kai Sauerwald
 *
 * @param <S> The signature type
 */
public class LogicalVerum<S extends VerumLogicSignature> extends Formula<S> {

	
	public LogicalVerum(S signature) {
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
		return "TOP";
	}

	@Override
	public S getSignature() {
		// TODO Auto-generated method stub
		return signature;
	}
}
