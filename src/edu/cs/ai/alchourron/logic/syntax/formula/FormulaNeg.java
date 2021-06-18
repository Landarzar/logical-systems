package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.List;
import java.util.Objects;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.structure.NegationLogicSignature;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <S> The Signature
 */
public class FormulaNeg<S extends NegationLogicSignature> extends LogicalOperator<S> {

	protected Formula<S> operand;
	
	/**
	 * Constructs a new instance of this class
	 * 
	 * @author Kai Sauerwald
	 */
	public FormulaNeg(Formula<S> operand) {
		this.operand = operand;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.logic.syntax.LogicalOperator#getOperands()
	 */
	@Override
	public List<Formula<S>> getOperands() {
		return List.of(operand);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isAtom()
	 */
	@Override
	public boolean isAtom() {
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(operand);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FormulaNeg))
			return false;
		@SuppressWarnings("unchecked")
		FormulaNeg<S> other = (FormulaNeg<S>) obj;
		return Objects.equals(operand, other.operand);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NOT " + this.operand;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cs.ai.alchourron.LaTeX#toLaTeX()
	 */
	@Override
	public String toLaTeX() {
		// TODO Auto-generated method stub
		return "\\neg " + this.operand.toLaTeX();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs.ai.logic.syntax.SyntacticElement#stringify()
	 */
	@Override
	public String stringify() {
		return toString();
	}
}
