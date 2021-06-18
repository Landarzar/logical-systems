package edu.cs.ai.alchourron.logic.syntax.formula;

import edu.cs.ai.alchourron.LaTeX;

public enum ClassicalQuantifier implements LaTeX {
	FORALL, EXISTS;
	
	@Override
	public String toLaTeX() {
		if(this.equals(FORALL))
			return "\\forall";
		else
			return "\\exists";
	}
}
