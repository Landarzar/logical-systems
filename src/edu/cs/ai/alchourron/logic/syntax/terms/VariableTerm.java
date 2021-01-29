package edu.cs.ai.alchourron.logic.syntax.terms;

import java.util.List;

import edu.cs.ai.alchourron.logic.SyntacticElement;

public class VariableTerm<F extends Enum<F>, V> implements Term<F, V> {

	V variable;

	public VariableTerm(V var) {
		this.variable = var;
		// TODO Auto-generated constructor stub
	}

	public V getVariable() {
		return variable;
	}

	@Override
	public boolean isAtom() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLogical() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		return variable.toString();
	}

	@Override
	public List<SyntacticElement> getSyntacticChildren() {
		// TODO Auto-generated method stub
		return null;
	}

}
