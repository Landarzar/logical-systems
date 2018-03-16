package edu.cs.ai.alchourron.logic.syntax;

import java.util.List;

import edu.cs.ai.alchourron.logic.Signature;

/***
 * Base for 
 * @author Kai Sauerwald
 *
 * @param <S> The Signature
 */
public interface LogicalOperator<S extends Signature>  extends SyntacticFormula<S> {
	
	
	/***
	 * Returns the operands of this logical connective.
	 * @author Kai Sauerwald
	 */
	List<SyntacticElement<S>> getOperands();
	
	/*
	 * (non-Javadoc)
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	default boolean isLogical() {
		return true;
	}

}
