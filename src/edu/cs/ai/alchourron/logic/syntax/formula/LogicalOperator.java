package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;

import edu.cs.ai.alchourron.logic.syntax.Formula;
import edu.cs.ai.alchourron.logic.syntax.Signature;
import edu.cs.ai.alchourron.logic.syntax.SyntacticElement;

/***
 * Base for 
 * @author Kai Sauerwald
 *
 * @param <S> The Signature
 */
public interface LogicalOperator<S extends Signature>  extends Formula<S> {
	
	
	/***
	 * Returns the operands of this logical connective.
	 * @author Kai Sauerwald
	 */
	List<? extends SyntacticElement<S>> getOperands();
	
	/*
	 * (non-Javadoc)
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	default boolean isLogical() {
		return true;
	}

}
