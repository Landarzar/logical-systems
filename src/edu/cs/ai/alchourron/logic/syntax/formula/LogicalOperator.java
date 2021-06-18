package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.Signature;
import edu.cs.ai.alchourron.logic.SyntacticElement;

/***
 * Base for
 * 
 * @author Kai Sauerwald
 *
 * @param <S> The Signature
 */
public abstract class LogicalOperator<S extends Signature> implements Formula<S> {


	/***
	 * Returns the operands of this logical connective.
	 * 
	 * @author Kai Sauerwald
	 */
	public abstract List<Formula<S>> getOperands();

	/*
	 * (non-Javadoc)
	 * 
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

	@Override
	public boolean isSignatureMatching(S signature) throws UnsupportedOperationException {
		return getOperands().stream().allMatch(f -> isSignatureMatching(signature));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.LaTeX#toLaTeX()
	 */
	protected String toLaTeX(String symbol) {
		StringBuilder builder = new StringBuilder();
//		builder.append("(");

		boolean first = true;

		for (Formula<S> propositionalFormula : getOperands()) {
			if (first) {
				builder.append(propositionalFormula.toLaTeX());
				first = false;
				continue;
			}
			builder.append(symbol); // Short form
			builder.append(propositionalFormula.toLaTeX());
		}

//		builder.append(")");
		return builder.toString();
	}
}
