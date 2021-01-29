package edu.cs.ai.alchourron.logic.fo;

import java.security.InvalidParameterException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.ModelTheoreticLogic;
import edu.cs.ai.alchourron.logic.SyntacticElement;
import edu.cs.ai.alchourron.logic.syntax.formula.LogicalAND;
import edu.cs.ai.alchourron.logic.syntax.formula.LogicalBiImplication;
import edu.cs.ai.alchourron.logic.syntax.formula.LogicalFalsum;
import edu.cs.ai.alchourron.logic.syntax.formula.LogicalImplication;
import edu.cs.ai.alchourron.logic.syntax.formula.LogicalNEG;
import edu.cs.ai.alchourron.logic.syntax.formula.LogicalOR;
import edu.cs.ai.alchourron.logic.syntax.formula.LogicalOperator;
import edu.cs.ai.alchourron.logic.syntax.formula.LogicalVerum;
import edu.cs.ai.alchourron.logic.syntax.formula.Proposition;

/***
 * Logical System representing propositional logic over symbol space of type
 * {@link PSym}.
 * 
 * @author Kai Sauerwald
 *
 * @param <R>    The type for Relationsymbols
 * @param <K>    The type for Functionsymbols
 * @param <V>    The type for Variables
 */
public class FirstOrderLogic<R extends Enum<R>,K extends Enum<K>,V> 
implements ModelTheoreticLogic<FiniteStructure<?, R, K, V, FOSignature<R, K, V>>,Formula<FOSignature<R, K, V>>, Boolean,FOSignature<R, K, V>>
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.cs.ai.alchourron.logic.LogicalSystem#eval(edu.cs.ai.alchourron.logic.
	 * Interpretation, edu.cs.ai.alchourron.logic.Formula)
	 */
	@Override
	public Boolean eval(FiniteStructure<?, R, K, V, FOSignature<R, K, V>> interpretation,
			Formula<FOSignature<R, K, V>> formula) {
		return satisfies(interpretation, formula);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.cs.ai.alchourron.logic.LogicalSystem#entails(edu.cs.ai.alchourron.logic.
	 * Interpretation, edu.cs.ai.alchourron.logic.Formula)
	 */
	public boolean satisfies(FiniteStructure<?, R, K, V, FOSignature<R, K, V>> interpretation,
			Formula<FOSignature<R, K, V>> formula) {
		return false;
	}

	@Override
	public boolean validFormula(Formula<FOSignature<R, K, V>> formula) {
		return true;
	}

}
