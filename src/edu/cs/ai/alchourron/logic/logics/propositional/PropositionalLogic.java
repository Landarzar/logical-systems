package edu.cs.ai.alchourron.logic.logics.propositional;

import java.security.InvalidParameterException;
import java.util.Set;
import java.util.stream.Collectors;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.ModelTheoreticLogic;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaAND;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaBiImplication;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaFalsum;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaImplication;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaNeg;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaOR;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaProposition;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaVerum;
import edu.cs.ai.alchourron.logic.syntax.formula.LogicalOperator;

/***
 * Logical System representing propositional logic over symbol space of type
 * {@link P}.
 * 
 * @author Kai Sauerwald
 *
 * @param <P> The symbol space over which the signature is definable
 * @param <F> The type for formula
 */
public class PropositionalLogic<P> implements
		ModelTheoreticLogic<PropositionalInterpretation<P>, Formula<PropositionalSignature<P>>, Boolean, PropositionalSignature<P>> {

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
		} else if (syntacton instanceof FormulaProposition<?, ?>) {
			FormulaProposition<P, PropositionalSignature<P>> atom = (FormulaProposition<P, PropositionalSignature<P>>) syntacton;
			if (!sig.getSymbols().contains(atom.getSymbol()))
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
	public Formula<PropositionalSignature<P>> getCharacterisingFormula(PropositionalSignature<P> sig,
			Set<PropositionalInterpretation<P>> set) {
		if (set.isEmpty())
			return new FormulaVerum<>(sig);
		if (set.size() == 1)
			return set.stream().findFirst().get().getCharacterizingFormula();

		Formula<PropositionalSignature<P>> formula = null;

		for (PropositionalInterpretation<P> i : set) {
			if (formula == null)
				formula = i.getCharacterizingFormula();
			else
				formula = new FormulaOR<PropositionalSignature<P>>(sig, formula, i.getCharacterizingFormula());
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
	public Set<PropositionalInterpretation<P>> modelsOf(
			Formula<PropositionalSignature<P>> formula) {
//		assert this.validFormula(formula) : "given formula ist not (syntactically) valid";
		return formula.getSignature().stream().filter(i -> satisfies(i, formula)).collect(Collectors.toSet());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.cs.ai.alchourron.logic.LogicalSystem#eval(edu.cs.ai.alchourron.logic.
	 * Interpretation, edu.cs.ai.alchourron.logic.Formula)
	 */
	@Override
	public Boolean eval(PropositionalInterpretation<P> interpretation,
			Formula<PropositionalSignature<P>> formula) {
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
//		assert this.validFormula(formula) : "the given formula ist not valid";

		if (formula instanceof FormulaProposition<?, ?>) {
			FormulaProposition<P, PropositionalSignature<P>> atom = (FormulaProposition<P, PropositionalSignature<P>>) formula;
			return interpretation.isTrue(atom.getSymbol());
		}
		if (formula instanceof FormulaAND<?>) {
			FormulaAND<PropositionalSignature<P>> and = (FormulaAND<PropositionalSignature<P>>) formula;
			return and.getOperands().stream().allMatch(o -> satisfies(interpretation, o));
		}
		if (formula instanceof FormulaOR<?>) {
			FormulaOR<PropositionalSignature<P>> or = (FormulaOR<PropositionalSignature<P>>) formula;
			return or.getOperands().stream().anyMatch(o -> satisfies(interpretation, o));
		}
		if (formula instanceof FormulaImplication<?>) {
			FormulaImplication<PropositionalSignature<P>> implication = (FormulaImplication<PropositionalSignature<P>>) formula;
			return !satisfies(interpretation, implication.getPremise())
					|| satisfies(interpretation, implication.getConclusion());
		}

		if (formula instanceof FormulaBiImplication<?>) {
			FormulaBiImplication<PropositionalSignature<P>> implication = (FormulaBiImplication<PropositionalSignature<P>>) formula;
			return (satisfies(interpretation, implication.getFirst())
					&& satisfies(interpretation, implication.getSecond()))
					|| (!satisfies(interpretation, implication.getFirst())
							&& !satisfies(interpretation, implication.getSecond()));
		}
		if (formula instanceof FormulaNeg<?>) {
			FormulaNeg<PropositionalSignature<P>> neg = (FormulaNeg<PropositionalSignature<P>>) formula;
			return !satisfies(interpretation, neg.getOperands().get(0));
		}
		if (formula instanceof FormulaFalsum<?>) {
			return false;
		}
		if (formula instanceof FormulaVerum<?>) {
			return true;
		}

		throw new InvalidParameterException("The given formula object is not a valid propositional formula");
	}

}
