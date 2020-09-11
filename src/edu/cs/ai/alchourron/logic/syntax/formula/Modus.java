package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;

import edu.cs.ai.alchourron.logic.syntax.Formula;
import edu.cs.ai.alchourron.logic.syntax.signature.ModalLogicSignature;

/***
 * Represents a modus of a modal logic. Typical is the modus "K" with the intended meaning someone "knows".
 * @author Kai Sauerwald
 *
 * @param <S> The signature
 * @param <M> The type of modi
 */
public class Modus<M,S extends ModalLogicSignature<M>> extends LogicalOperator<S> {
	
	M modus;
	
	public Modus(M modus) {
		this.modus = modus;
	}
	
	/***
	 * Gets the kind of the modus. 
	 * @author Kai Sauerwald
	 */
	public M getKindofModus() {
		return modus;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cs.ai.logic.syntax.SyntacticElement#stringify()
	 */
	@Override
	public String stringify() {
		return "(" + getKindofModus() + getOperands().get(0) + ")";
	}

	@Override
	public boolean isAtom() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<? extends Formula<S>> getOperands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S getSignature() {
		// TODO Auto-generated method stub
		return null;
	}
}
