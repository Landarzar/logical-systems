package edu.cs.ai.alchourron.logic;

import edu.cs.ai.alchourron.LaTeX;

/***
 * Represents a formula
 * 
 * @author Kai Sauerwald
 *
 * @param <S> The signature
 */
public interface Formula<S extends Signature> extends LaTeX, SyntacticElement {

//	/**
//	 * Returns the "children" for this formula.
//	 * @author Kai Sauerwald
//	 * @throws UnsupportedOperationException If the method is not implement or supported
//	 */
//	public List<Formula<S>> getSyntaxTree() throws UnsupportedOperationException
//	{
//		throw new UnsupportedOperationException("This operation is not implemented");
//	}; 

//	protected S signature;

//	public Formula(S signature) {
//		this.signature = signature;
//	}

//	/***
//	 * Gets the (smalles) signature matching to this formula.
//	 * 
//	 * @author Kai Sauerwald
//	 * @return The signature of the formula
//	 */
//	public S getSignature() {
//		return signature;
//	}

	/***
	 * Checks whether the formula fits a signature
	 * 
	 * @author Kai Sauerwald
	 * @param signature
	 */
	public default boolean isSignatureMatching(S signature) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("This operation is not implemented");
	};
}
