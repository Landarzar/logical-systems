package edu.cs.ai.alchourron.logic.syntax.formula;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.structure.AtomLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.VerumLogicSignature;

/***
 * Represents tautologies
 * 
 * @author Kai Sauerwald
 *
 * @param <S> The signature type
 */
public class FormulaVerum<S extends VerumLogicSignature> extends FormulaAtom<S> {

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
	public String toString() {
		return "TOP";
	}
	
	@Override
	public boolean isSignatureMatching(S signature) throws UnsupportedOperationException {
		return true;
	}
	
	@Override
	public String toLaTeX() {
		return "\\top";
	}
}
