package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.structure.ModalLogicSignature;

/***
 * Represents a modus of a modal logic. Typical is the modus "K" with the
 * intended meaning someone "knows".
 * 
 * @author Kai Sauerwald
 *
 * @param <S> The signature
 * @param <M> The type of modi
 */
public class FormulaModus<M, S extends ModalLogicSignature<M>> extends LogicalOperator<S> {

	M modus;

	public FormulaModus(M modus) {
		this.modus = modus;
	}

	/***
	 * Gets the kind of the modus.
	 * 
	 * @author Kai Sauerwald
	 */
	public M getKindofModus() {
		return modus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs.ai.logic.syntax.SyntacticElement#stringify()
	 */
	@Override
	public String stringify() {
		return "(" + getKindofModus() + getOperands().get(0) + ")";
	}
	
	@Override
	public String toLaTeX() {
		return getKindofModus() + getOperands().get(0).toLaTeX();
	}

	@Override
	public boolean isAtom() {
		return false;
	}

	@Override
	public List<Formula<S>> getOperands() {
		// TODO Auto-generated method stub
		return null;
	}
}
