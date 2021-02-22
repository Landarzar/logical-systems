package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;
import java.util.Objects;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.structure.BiImplicationLogicSignature;

public class FormulaBiImplication<S extends BiImplicationLogicSignature> extends LogicalOperator<S> {
	Formula<S> implicant1;
	Formula<S> implicant2;

	public FormulaBiImplication(Formula<S> implicant1, Formula<S> implicant2) {
		this.implicant1 = implicant1;
		this.implicant2 = implicant2;
	}

	public Formula<S> getFirst() {
		return implicant1;
	}

	public Formula<S> getSecond() {
		return implicant2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs.ai.logic.syntax.SyntacticElement#stringify()
	 */
	@Override
	public String stringify() {
		return "(" + getOperands().get(0) + " -> " + getOperands().get(1) + ")";
	}

	@Override
	public boolean isAtom() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Formula<S>> getOperands() {
		return List.of(implicant1, implicant2);
	}

	@Override
	public String toString() {
		return implicant1.toLaTeX() + " <=> " + implicant2.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(implicant1, implicant2);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FormulaBiImplication))
			return false;
		FormulaBiImplication other = (FormulaBiImplication) obj;
		return Objects.equals(implicant1, other.implicant1) && Objects.equals(implicant2, other.implicant2);
	}
}
