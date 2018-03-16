package edu.cs.ai.alchourron.logic;

/***
 * This class represents a interpretation type.
 * @author Kai Sauerwald
 *
 * @param <S> The signature type
 */
public interface Interpretation <S extends Signature> {

	/***
	 * Gets the (smalles) signature matching to this Interpretation.
	 * @author Kai Sauerwald
	 * @return The signature of the interpretation
	 */
	public S getSignature();
}
