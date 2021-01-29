package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.ArrayList;
import java.util.List;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.Signature;
import edu.cs.ai.alchourron.logic.SyntacticElement;

/***
 * Base for 
 * @author Kai Sauerwald
 *
 * @param <S> The Signature
 */
public abstract class LogicalOperator<S extends Signature>  extends Formula<S> implements SyntacticElement {
	
	
	/***
	 * Returns the operands of this logical connective.
	 * @author Kai Sauerwald
	 */
	public abstract List<Formula<S>> getOperands();
	
	/*
	 * (non-Javadoc)
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	public boolean isLogical() {
		return true;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.logic.Formula#getSyntaxTree()
	 */
	@Override
	public List<SyntacticElement> getSyntacticChildren() throws UnsupportedOperationException {
		ArrayList<SyntacticElement> result = new ArrayList<>(this.getOperands().size());
		this.getOperands().forEach(op -> result.add(op));
		return result;
	}

}
