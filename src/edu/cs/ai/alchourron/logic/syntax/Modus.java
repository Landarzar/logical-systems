package edu.cs.ai.alchourron.logic.syntax;

import edu.cs.ai.alchourron.logic.Signature;

/***
 * Represents a modus of a modal logic. Typical is the modus "K" with the intended meaning someone "knows".
 * @author Kai Sauerwald
 *
 * @param <S> The signature
 * @param <K> The type of modi
 */
public interface Modus<S extends Signature, K> extends LogicalOperator<S> {
	
	/***
	 * Gets the kind of the modus. 
	 * @author Kai Sauerwald
	 */
	public K getKindofModus();
	
	/*
	 * (non-Javadoc)
	 * @see cs.ai.logic.syntax.SyntacticElement#stringify()
	 */
	@Override
	public default String stringify() {
		return "(" + getKindofModus() + getOperands().get(0) + ")";
	}
}
