/**
 * 
 */
package edu.cs.ai.alchourron.logic.syntax.formula;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.structure.AtomLogicSignature;

/**
 * @author kai
 *
 */
public abstract class FormulaAtom<S extends AtomLogicSignature> implements Formula<S> {

	@Override
	public boolean isAtom() {
		return true;
	}

	@Override
	public boolean isLogical() {
		return false;
	}

}
