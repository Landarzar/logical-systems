/**
 * 
 */
package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;

import edu.cs.ai.alchourron.logic.syntax.Formula;
import edu.cs.ai.alchourron.logic.syntax.Signature;

/**
 * @author Kai Sauerwald
 *
 */
public class Conditionalisation<S extends Signature> extends LogicalOperator<S> {

	@Override
	public boolean isAtom() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String stringify() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends Formula<S>> getOperands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S getSignature() {
		// TODO Auto-generated method stub
		return null;
	}

}
