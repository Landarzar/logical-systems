package edu.cs.ai.alchourron.logic.propositional;

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
 * {@link P}.
 * 
 * @author Kai Sauerwald
 *
 * @param <P> The symbol space over which the signature is definable
 * @param <F>    The type for formula
 */
public class PropositionalLogic<P> implements
		ModelTheoreticLogic<PropositionalInterpretation<P, PropositionalSignature<P>>, Formula<PropositionalSignature<P>>, Boolean, PropositionalSignature<P>> {


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
	protected boolean validSyntaxTree(Formula<PropositionalSignature<P>> syntacton,
			PropositionalSignature<P> sig) {
		if (syntacton instanceof LogicalAND<?> || syntacton instanceof LogicalOR<?>
				|| syntacton instanceof LogicalNEG<?>) {
			LogicalOperator<PropositionalSignature<P>> le = (LogicalOperator<PropositionalSignature<P>>) syntacton;
			return le.getOperands().stream().allMatch(se -> validSyntaxTree(se, sig));
		} else if (syntacton instanceof Proposition<?, ?>) {
			Proposition<P, PropositionalSignature<P>> atom = (Proposition<P, PropositionalSignature<P>>) syntacton;
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
	public Formula<PropositionalSignature<P>> getCharacterisingFormula(PropositionalSignature<P> sig, Set<PropositionalInterpretation<P, PropositionalSignature<P>>> set){
		if(set.isEmpty())
			return new LogicalVerum<>(sig);
		if(set.size() == 1)
			return set.stream().findFirst().get().getCharacterizingFormula();
		
		Formula<PropositionalSignature<P>> formula = null;
		
		for (PropositionalInterpretation<P, PropositionalSignature<P>> i : set) {
			if(formula == null)
				formula = i.getCharacterizingFormula();
			else
				formula = new LogicalOR<PropositionalSignature<P>>(sig, formula, i.getCharacterizingFormula()) ;
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
	public Set<PropositionalInterpretation<P, PropositionalSignature<P>>> modelsOf(
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
	public Boolean eval(PropositionalInterpretation<P, PropositionalSignature<P>> interpretation,
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
	public boolean satisfies(PropositionalInterpretation<P, PropositionalSignature<P>> interpretation,
			Formula<PropositionalSignature<P>> formula) {
//		assert this.validFormula(formula) : "the given formula ist not valid";

		if (formula instanceof Proposition<?,?>) {
			Proposition<P,PropositionalSignature<P>> atom = (Proposition<P,PropositionalSignature<P>>) formula;
			return interpretation.isTrue(atom.getSymbol());
		}
		if (formula instanceof LogicalAND<?>) {
			LogicalAND<PropositionalSignature<P>> and = (LogicalAND<PropositionalSignature<P>>) formula;
			return and.getOperands().stream().allMatch(o -> satisfies(interpretation, o));
		}
		if (formula instanceof LogicalOR<?>) {
			LogicalOR<PropositionalSignature<P>> or = (LogicalOR<PropositionalSignature<P>>) formula;
			return or.getOperands().stream().anyMatch(o -> satisfies(interpretation, o));
		}
		if (formula instanceof LogicalImplication<?>) {
			LogicalImplication<PropositionalSignature<P>> implication = (LogicalImplication<PropositionalSignature<P>>) formula;
			return  !satisfies(interpretation, implication.getPremise()) || satisfies(interpretation, implication.getConclusion());
		}

		if (formula instanceof LogicalBiImplication<?>) {
			LogicalBiImplication<PropositionalSignature<P>> implication = (LogicalBiImplication<PropositionalSignature<P>>) formula;
			return  (satisfies(interpretation, implication.getFirst()) && satisfies(interpretation, implication.getSecond()))
					|| (!satisfies(interpretation, implication.getFirst())
							&& !satisfies(interpretation, implication.getSecond()));
		}
		if (formula instanceof LogicalNEG<?>) {
			LogicalNEG<PropositionalSignature<P>> neg = (LogicalNEG<PropositionalSignature<P>>) formula;
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
