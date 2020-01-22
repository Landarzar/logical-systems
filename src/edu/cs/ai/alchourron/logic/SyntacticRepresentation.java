/**
 * 
 */
package edu.cs.ai.alchourron.logic;

/***
 * Represents an formula
 * @author Kai Sauerwald
 *
 * @param <S> The Type of signatures this formals are defined over
 */
public interface SyntacticRepresentation<S> {
	
	/**
	 * Returns the "syntax tree" for this formula.
	 * @author Kai Sauerwald
	 * @throws UnsupportedOperationException If the method is not implement or supported
	 */
	public TreeIterator<SyntacticRepresentation<S>> getSyntaxTreeIterator() throws UnsupportedOperationException;
	
	/***
	 * Gets the (smalles) signature matching to this formula.
	 * @author Kai Sauerwald
	 * @return The signature of the formula
	 */
	public S getSignature() throws UnsupportedOperationException;
	
	/***
	 * Checks whether the formula matches this signature
	 * @author Kai Sauerwald
	 * @param signature
	 */
	public boolean isSignatureMatching(S signature)  throws UnsupportedOperationException; 
}
