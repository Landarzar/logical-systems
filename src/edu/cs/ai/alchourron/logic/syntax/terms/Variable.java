package edu.cs.ai.alchourron.logic.syntax.terms;

import java.util.List;

import edu.cs.ai.alchourron.logic.SyntacticElement;

public class Variable<F extends Enum<F>,V> implements Term<F> {
	
	V variable;
	
	public Variable(V var) {
		this.variable = variable;
		// TODO Auto-generated constructor stub
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
	public String stringify() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public List<SyntacticElement> getSyntacticChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
