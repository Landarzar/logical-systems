package edu.cs.ai.alchourron.logic.propositional;

import java.security.InvalidParameterException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import edu.cs.ai.alchourron.logic.semantics.ModelTheory;
import edu.cs.ai.alchourron.logic.syntax.Formula;
import edu.cs.ai.alchourron.logic.syntax.SyntacticElement;
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
 * @param <PSym> The symbol space over which the signature is definable
 * @param <F>    The type for formula
 */
public class PropositionalLogic<PSym> implements
		ModelTheory<PropositionalInterpretation<PSym, PropositionalSignature<PSym>>, Formula<PropositionalSignature<PSym>>, Boolean, PropositionalSignature<PSym>> {


	/*
	 * (non-Javadoc)
	 * 
	 * @see cs.ai.logic.LogicalSystem#validFormula(cs.ai.logic.Formula)
	 */
	@Override
	public boolean validFormula(Formula<PropositionalSignature<PSym>> formula) {
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
	protected boolean validSyntaxTree(SyntacticElement<PropositionalSignature<PSym>> syntacton,
			PropositionalSignature<PSym> sig) {
		if (syntacton instanceof LogicalAND<?> || syntacton instanceof LogicalOR<?>
				|| syntacton instanceof LogicalNEG<?>) {
			LogicalOperator<PropositionalSignature<PSym>> le = (LogicalOperator<PropositionalSignature<PSym>>) syntacton;
			return le.getOperands().stream().allMatch(se -> validSyntaxTree(se, sig));
		} else if (syntacton instanceof Proposition<?, ?>) {
			Proposition<PSym, PropositionalSignature<PSym>> atom = (Proposition<PSym, PropositionalSignature<PSym>>) syntacton;
			if (!sig.getSymbols().contains(atom.getSymbol()))
				return false;
			return true;
		}

		return false;
	}
	

	

	
	/***
	 * Generates a formula having exact having exact the set of interpretations as models.
	 * @param set The set of models.
	 */
	public Formula<PropositionalSignature<PSym>> getCharacterisingFormula(PropositionalSignature<PSym> sig, Set<PropositionalInterpretation<PSym, PropositionalSignature<PSym>>> set){
		if(set.isEmpty())
			return new LogicalVerum<>(sig);
		if(set.size() == 1)
			return set.stream().findFirst().get().getCharacterizingFormula();
		
		Formula<PropositionalSignature<PSym>> formula = null;
		
		for (PropositionalInterpretation<PSym, PropositionalSignature<PSym>> i : set) {
			if(formula == null)
				formula = i.getCharacterizingFormula();
			else
				formula = new LogicalOR<PropositionalSignature<PSym>>(sig, formula, i.getCharacterizingFormula()) ;
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
	public Set<PropositionalInterpretation<PSym, PropositionalSignature<PSym>>> modelsOf(
			Formula<PropositionalSignature<PSym>> formula) {
		assert this.validFormula(formula) : "given formula ist not (syntactically) valid";
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
	public Boolean eval(PropositionalInterpretation<PSym, PropositionalSignature<PSym>> interpretation,
			Formula<PropositionalSignature<PSym>> formula) {
		return satisfies(interpretation, formula);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.cs.ai.alchourron.logic.LogicalSystem#entails(edu.cs.ai.alchourron.logic.
	 * Interpretation, edu.cs.ai.alchourron.logic.Formula)
	 */
	public boolean satisfies(PropositionalInterpretation<PSym, PropositionalSignature<PSym>> interpretation,
			Formula<PropositionalSignature<PSym>> formula) {
//		assert this.validFormula(formula) : "the given formula ist not valid";

		if (formula instanceof Proposition<?,?>) {
			Proposition<PSym,PropositionalSignature<PSym>> atom = (Proposition<PSym,PropositionalSignature<PSym>>) formula;
			return interpretation.isTrue(atom.getSymbol());
		}
		if (formula instanceof LogicalAND<?>) {
			LogicalAND<PropositionalSignature<PSym>> and = (LogicalAND<PropositionalSignature<PSym>>) formula;
			return and.getOperands().stream().allMatch(o -> satisfies(interpretation, o));
		}
		if (formula instanceof LogicalOR<?>) {
			LogicalOR<PropositionalSignature<PSym>> or = (LogicalOR<PropositionalSignature<PSym>>) formula;
			return or.getOperands().stream().anyMatch(o -> satisfies(interpretation, o));
		}
		if (formula instanceof LogicalImplication<?>) {
			LogicalImplication<PropositionalSignature<PSym>> implication = (LogicalImplication<PropositionalSignature<PSym>>) formula;
			return  !satisfies(interpretation, implication.getPremise()) || satisfies(interpretation, implication.getConclusion());
		}

		if (formula instanceof LogicalBiImplication<?>) {
			LogicalBiImplication<PropositionalSignature<PSym>> implication = (LogicalBiImplication<PropositionalSignature<PSym>>) formula;
			return  (satisfies(interpretation, implication.getFirst()) && satisfies(interpretation, implication.getSecond()))
					|| (!satisfies(interpretation, implication.getFirst())
							&& !satisfies(interpretation, implication.getSecond()));
		}
		if (formula instanceof LogicalNEG<?>) {
			LogicalNEG<PropositionalSignature<PSym>> neg = (LogicalNEG<PropositionalSignature<PSym>>) formula;
			return !satisfies(interpretation, neg.getOperands().get(0));
		}
		if (formula instanceof LogicalFalsum<?>) {
			return false;
		}
		if (formula instanceof LogicalVerum<?>) {
			return true;
		}

		throw new InvalidParameterException("The given formula object is not a valid propositional formula");
	}

}
