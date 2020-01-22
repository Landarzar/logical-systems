package edu.cs.ai.alchourron.logic.syntax.formula;

import edu.cs.ai.alchourron.logic.syntax.Signature;
import edu.cs.ai.alchourron.logic.syntax.signature.ModalLogicSignature;

/***
 * Represents a modus of a modal logic. Typical is the modus "K" with the intended meaning someone "knows".
 * @author Kai Sauerwald
 *
 * @param <S> The signature
 * @param <M> The type of modi
 */
public interface Modus<M,S extends ModalLogicSignature<M>> extends LogicalOperator<S> {
	
	/***
	 * Gets the kind of the modus. 
	 * @author Kai Sauerwald
	 */
	public M getKindofModus();
	
	/*
	 * (non-Javadoc)
	 * @see cs.ai.logic.syntax.SyntacticElement#stringify()
	 */
	@Override
	public default String stringify() {
		return "(" + getKindofModus() + getOperands().get(0) + ")";
	}
}
