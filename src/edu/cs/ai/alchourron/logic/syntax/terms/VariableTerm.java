package edu.cs.ai.alchourron.logic.syntax.terms;

import java.util.List;

import edu.cs.ai.alchourron.logic.SyntacticElement;
import edu.cs.ai.alchourron.logic.syntax.structure.FunctionTermLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.VariableTermLogicSignature;

public class VariableTerm<V, S extends VariableTermLogicSignature<V>> implements Term<S> {

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
		return List.of();
	}

	@Override
	public boolean isSignatureMatching(
			S signature) {
		return true;
	}
}
