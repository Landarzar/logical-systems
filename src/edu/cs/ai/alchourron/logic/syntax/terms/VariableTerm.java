package edu.cs.ai.alchourron.logic.syntax.terms;

import java.util.List;
import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(variable);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof VariableTerm))
			return false;
		VariableTerm other = (VariableTerm) obj;
		return Objects.equals(variable, other.variable);
	}
}
