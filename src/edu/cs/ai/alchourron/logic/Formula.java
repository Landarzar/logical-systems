package edu.cs.ai.alchourron.logic;

import edu.cs.ai.alchourron.LaTeX;
import edu.cs.ai.alchourron.logic.syntax.SyntacticElement;

/***
 * Represents an formula
 * @author Kai Sauerwald
 *
 * @param <S> The signature
 */
public interface Formula<S extends Signature> extends LaTeX {
	
	/**
	 * Returns the "syntax tree" for this formula.
	 * This allows to seperate the representation of knoweldege from the syntactic structure.
	 * @author Kai Sauerwald
	 * @throws UnsupportedOperationException If the method is not implement or supported
	 */
	public SyntacticElement<S> getSyntaxTree() throws UnsupportedOperationException;
	
	/***
	 * Gets the (smalles) signature matching to this formula.
	 * @author Kai Sauerwald
	 * @return The signature of the formula
	 */
	public S getSignature();
}
