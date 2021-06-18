package edu.cs.ai.alchourron.logic.logics.propositional;

import java.security.InvalidParameterException;
import java.util.Set;
import java.util.stream.Collectors;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.ModelTheoreticLogic;
import edu.cs.ai.alchourron.logic.semantics.interpretations.PropositionalInterpretation;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaAND;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaBiImplication;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaFalsum;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaImplication;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaNeg;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaOR;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaPropositionalAtom;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaVerum;
import edu.cs.ai.alchourron.logic.syntax.formula.LogicalOperator;

/***
 * Logical System representing propositional logic over symbol space of type
 * {@link P}.
 * 
 * @author Kai Sauerwald
 *
 * @param <P> The symbol space over which the signature is definable
 */
public class PropositionalLogic<P> implements
		ModelTheoreticLogic<PropositionalInterpretation<P>, Formula<PropositionalSignature<P>>, Boolean, PropositionalSignature<P>> {
	

	
	/**
	 * Generats a new propositional logic over the given signature.
	 * @author Kai Sauerwald
	 */
	public PropositionalLogic() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs.ai.logic.LogicalSystem#validFormula(cs.ai.logic.Formula)
	 */
	@Override
	public boolean validFormula(Formula<PropositionalSignature<P>> formula) {
//		SyntacticElement<PropositionalSignature<PSym>> syntacton = formula.getSyntaxTree();
//		return validSyntaxTree(syntacton, (PropositionalSignature<PSym>) formula.getSignature());
		throw new UnsupportedOperationException("Not impleted yet.");
	}

	/****
	 * Checks where the syntax tree contains only elements that are valid for a
	 * propositional formula
	 * 
	 * @author Kai Sauerwald
	 * @param syntacton the root of the syntax tree
	 */
	@SuppressWarnings("unchecked")
	protected boolean validSyntaxTree(Formula<PropositionalSignature<P>> syntacton, PropositionalSignature<P> sig) {
		if (syntacton instanceof FormulaAND<?> || syntacton instanceof FormulaOR<?>
				|| syntacton instanceof FormulaNeg<?>) {
			LogicalOperator<PropositionalSignature<P>> le = (LogicalOperator<PropositionalSignature<P>>) syntacton;
			return le.getOperands().stream().allMatch(se -> validSyntaxTree(se, sig));
		} else if (syntacton instanceof FormulaPropositionalAtom<?, ?>) {
			FormulaPropositionalAtom<P, PropositionalSignature<P>> atom = (FormulaPropositionalAtom<P, PropositionalSignature<P>>) syntacton;
			if (!sig.getPropositions().contains(atom.getSymbol()))
				return false;
			return true;
		}

		return false;
	}

	/***
	 * Generates a formula having exact having exact the set of interpretations as
	 * models.
	 * 
	 * @param set The set of models.
	 */
	public Formula<PropositionalSignature<P>> getCharacterisingFormula(
			Set<PropositionalInterpretation<P>> set) {
		if (set.isEmpty())
			return new FormulaVerum<>();
		if (set.size() == 1)
			return set.stream().findFirst().get().getCharacterizingFormula();

		Formula<PropositionalSignature<P>> formula = null;

		for (PropositionalInterpretation<P> i : set) {
			if (formula == null)
				formula = i.getCharacterizingFormula();
			else
				formula = new FormulaOR<PropositionalSignature<P>>(formula, i.getCharacterizingFormula());
		}

		return formula;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.cs.ai.alchourron.logic.LogicalSystem#models(edu.cs.ai.alchourron.logic.
	 * Formula)
	 */
	@Override
	public Set<PropositionalInterpretation<P>> modelsOf(Formula<PropositionalSignature<P>> formula, PropositionalSignature<P> signature) {
//		assert this.validFormula(formula) : "given formula ist not (syntactically) valid";
		return signature.stream().filter(i -> satisfies(i, formula)).collect(Collectors.toSet());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.cs.ai.alchourron.logic.LogicalSystem#eval(edu.cs.ai.alchourron.logic.
	 * Interpretation, edu.cs.ai.alchourron.logic.Formula)
	 */
	@Override
	public Boolean eval(PropositionalInterpretation<P> interpretation, Formula<PropositionalSignature<P>> formula) {
		return satisfies(interpretation, formula);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.cs.ai.alchourron.logic.LogicalSystem#entails(edu.cs.ai.alchourron.logic.
	 * Interpretation, edu.cs.ai.alchourron.logic.Formula)
	 */
	@Override
	public boolean satisfies(PropositionalInterpretation<P> interpretation,
			Formula<PropositionalSignature<P>> formula) {

		if (formula instanceof FormulaPropositionalAtom<?,?>) {
			FormulaPropositionalAtom<P,PropositionalSignature<P>> f = (FormulaPropositionalAtom<P,PropositionalSignature<P>>) formula;
			return interpretation.isTrue(f.getSymbol());
		}
		else if (formula instanceof FormulaAND<?>) {
			FormulaAND<PropositionalSignature<P>> f = (FormulaAND<PropositionalSignature<P>>) formula;
			return f.getOperands().stream().allMatch(o -> satisfies(interpretation, o));
		}
		else if (formula instanceof FormulaOR<?>) {
			FormulaOR<PropositionalSignature<P>> f = (FormulaOR<PropositionalSignature<P>>) formula;
			return f.getOperands().stream().anyMatch(o -> satisfies(interpretation, o));
		}
		else if (formula instanceof FormulaImplication<?>) {
			FormulaImplication<PropositionalSignature<P>> f = (FormulaImplication<PropositionalSignature<P>>) formula;
			return !satisfies(interpretation, f.getPremise())
					|| satisfies(interpretation, f.getConclusion());
		}
		else if (formula instanceof FormulaBiImplication<?>) {
			FormulaBiImplication<PropositionalSignature<P>> f = (FormulaBiImplication<PropositionalSignature<P>>) formula;
			return (satisfies(interpretation, f.getFirst())
					&& satisfies(interpretation, f.getSecond()))
					|| (!satisfies(interpretation, f.getFirst())
					&& !satisfies(interpretation, f.getSecond()));
		}
		else if (formula instanceof FormulaNeg<?>) {
			FormulaNeg<PropositionalSignature<P>> f = (FormulaNeg<PropositionalSignature<P>>) formula;
			return !satisfies(interpretation, f.getOperands().get(0));
		}
		else if (formula instanceof FormulaFalsum<?>) {
			return false;
		}
		else if (formula instanceof FormulaVerum<?>) {
			return true;
		}

		// This happens if none of the special cases matches
		throw new InvalidParameterException("The given formula object is not a valid propositional formula: "+ formula);
	}

//	/***
//	 * Test satisfaction
//	 * @param interpretation
//	 * @param formula
//	 * @return
//	 */
//	public boolean satisfies(PropositionalInterpretation<P> interpretation,
//			FormulaProposition<P, PropositionalSignature<P>> formula) {
//		return interpretation.isTrue(formula.getSymbol());
//	}
//
//	public boolean satisfies(PropositionalInterpretation<P> interpretation,
//			FormulaAND<PropositionalSignature<P>> formula) {
//		return formula.getOperands().stream().allMatch(o -> satisfies(interpretation, o));
//	}
//
//	public boolean satisfies(PropositionalInterpretation<P> interpretation,
//			FormulaOR<PropositionalSignature<P>> formula) {
//		return formula.getOperands().stream().anyMatch(o -> satisfies(interpretation, o));
//	}
//
//	public boolean satisfies(PropositionalInterpretation<P> interpretation,
//			FormulaImplication<PropositionalSignature<P>> formula) {
//		return !satisfies(interpretation, formula.getPremise())
//				|| satisfies(interpretation, formula.getConclusion());
//	}
//
//	public boolean satisfies(PropositionalInterpretation<P> interpretation,
//			FormulaBiImplication<PropositionalSignature<P>> formula) {
//		return (satisfies(interpretation, formula.getFirst())
//				&& satisfies(interpretation, formula.getSecond()))
//				|| (!satisfies(interpretation, formula.getFirst())
//						&& !satisfies(interpretation, formula.getSecond()));
//	}
//
//	public boolean satisfies(PropositionalInterpretation<P> interpretation,
//			FormulaNeg<PropositionalSignature<P>> formula) {
//		return !satisfies(interpretation, formula.getOperands().get(0));
//	}
//
//	public boolean satisfies(PropositionalInterpretation<P> interpretation,
//			FormulaFalsum<PropositionalSignature<P>> formula) {
//		return false;
//	}
//
//	public boolean satisfies(PropositionalInterpretation<P> interpretation,
//			FormulaVerum<PropositionalSignature<P>> formula) {
//		return true;
//	}

}
