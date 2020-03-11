package edu.cs.ai.alchourron.logic.semantics;

import edu.cs.ai.alchourron.LaTeX;
import edu.cs.ai.alchourron.logic.syntax.Signature;

/***
 * This class represents a interpretation type.
 * @author Kai Sauerwald
 *
 * @param <S> The signature type
 */
public interface Interpretation <S extends Signature> extends LaTeX {

	/***
	 * Gets the (smalles) signature matching to this Interpretation.
	 * @author Kai Sauerwald
	 * @return The signature of the interpretation
	 */
	public S getSignature();
	
	/***
	 * Checkes whether the signature matching to this Interpretation.
	 * @author Kai Sauerwald
	 * @return The signature of the interpretation
	 */
	public boolean isMatchingSignature(S signature);
}
