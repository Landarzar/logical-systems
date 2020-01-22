package edu.cs.ai.alchourron.logic.syntax;

import edu.cs.ai.alchourron.LaTeX;

/***
 * Represents an formula
 * @author Kai Sauerwald
 *
 * @param <S> The signature
 */
public interface Formula<S extends Signature> extends LaTeX, SyntacticElement<S> {
	
	/**
	 * Returns the "syntax tree" for this formula.
	 * @author Kai Sauerwald
	 * @throws UnsupportedOperationException If the method is not implement or supported
	 */
	public default SyntacticElement<S> getSyntaxTree() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException("This operation is not implemented");
	}; 
	
	/***
	 * Gets the (smalles) signature matching to this formula.
	 * @author Kai Sauerwald
	 * @return The signature of the formula
	 */
	public S getSignature();	
	
	/***
	 * Checks whether the formula matches this signature
	 * @author Kai Sauerwald
	 * @param signature
	 */
	public default boolean isSignatureMatching(S signature)  throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException("This operation is not implemented");
	}; 
}
