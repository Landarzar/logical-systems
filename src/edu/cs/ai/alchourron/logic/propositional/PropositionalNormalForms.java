package edu.cs.ai.alchourron.logic.propositional;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalAND;
import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalAtom;
import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalFalsum;
import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalNEG;
import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalOR;
import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalVerum;
import edu.cs.ai.alchourron.logic.syntax.SyntacticElement;
import edu.cs.ai.combinatorics.PowerSet;

public class PropositionalNormalForms<PSym> {

	/*******************************************************************
	 * Test Horn
	 ******************************************************************/

	/**
	 * Tests where the given input CNF-Formula is horn. The correctness of this
	 * function is not garuanteed if cnf is not in conjunctive normal form.
	 * 
	 * @param cnf
	 */
	public boolean isHorn(PropositionalFormula<PSym> cnf) {

		if (cnf instanceof PropositionalAND<?>) {
			PropositionalAND<PSym> and = (PropositionalAND<PSym>) cnf;
			for (PropositionalFormula<PSym> iterable_element : and.getOperands()) {
				if (iterable_element instanceof PropositionalOR<?>) {
					PropositionalOR<PSym> or = (PropositionalOR<PSym>) iterable_element;

					int number_of_positive_literals = 0;
					for (PropositionalFormula<PSym> literal : or.getOperands()) {
						if (!(literal instanceof PropositionalNEG<?>))
							number_of_positive_literals += 1;
						if (number_of_positive_literals > 1)
							return false;
					}
				}
			}
		}
		if (cnf instanceof PropositionalOR<?>) {
			PropositionalOR<PSym> or = (PropositionalOR<PSym>) cnf;
			int number_of_positive_literals = 0;
			for (PropositionalFormula<PSym> literal : or.getOperands()) {
				if (!(literal instanceof PropositionalNEG<?>))
					number_of_positive_literals += 1;
				if (number_of_positive_literals > 1)
					return false;
			}
		}

		return true;
	}

	public boolean isHornDefinable(PropositionalFormula<PSym> phi) {
		// We use the characterization by Makowsky (1987): A formula is horndefinable
		// if for any extension by positive atoms there exists a generic assignment.

		// Signature
		PropositionalSignature<PSym> signature = phi.getSignature();

		return PowerSet.stream(signature.getSymbols()).allMatch(set -> {
			// Build formula:
			PropositionalFormula<PSym> formula = null;
			for (PSym psym : set) {
				if (formula == null)
					formula = new PropositionalAtom<>(signature, psym);
				else
					formula = formula.And(new PropositionalAtom<>(signature, psym));
			}
			
			// i hate java.....
			final PropositionalFormula<PSym> tmp = formula;

			// search for a generic interpretation
			return signature.stream().anyMatch(intp -> isGeneric(intp, tmp));
		});
	}

	/***
	 * Test where a pair $(\omega,\phi)$ is generic. "generic" is defined by
	 * Makowsky in 1987: (1) $\omega \models \phi$ (2) $\omega\models\sigma$ iff
	 * $\phi\models\sigma$ for all $\sigma\in\Sigma$ where \Sigma is the signature.
	 * 
	 * @param intp    The interpretation $\omega$
	 * @param formula The formula $\phi$
	 */
	public boolean isGeneric(PropositionalInterpretation<PSym, PropositionalSignature<PSym>> intp,
			PropositionalFormula<PSym> formula) {
		PropositionalLogic<PSym> logic = new PropositionalLogic<>();
		if (!logic.satisfies(intp, formula))
			return false;

		return formula.getSignature().getSymbols().stream().allMatch(sigma -> {
			return logic.satisfies(intp, new PropositionalAtom<>(formula.getSignature(), sigma)) == logic
					.entails(formula, new PropositionalAtom<>(formula.getSignature(), sigma));
		});
	}

	/*******************************************************************
	 * Clear Tree
	 ******************************************************************/

	/***
	 * Clean up the Syntaxtree
	 * 
	 * @author Kai Sauerwald
	 * @param phi
	 * @return
	 */
	public PropositionalFormula<PSym> clearTree(PropositionalFormula<PSym> phi) {
		if (phi instanceof PropositionalAtom<?> || phi instanceof PropositionalVerum<?>
				|| phi instanceof PropositionalFalsum<?>)
			return phi;
		if (phi instanceof PropositionalAND<?>)
			return _clearTree((PropositionalAND<PSym>) phi);
		if (phi instanceof PropositionalOR<?>)
			return _clearTree((PropositionalOR<PSym>) phi);
		if (phi instanceof PropositionalNEG<?>)
			return _clearTree((PropositionalNEG<PSym>) phi);
		return phi;
	}

	private PropositionalFormula<PSym> _clearTree(PropositionalNEG<PSym> phi) {
		@SuppressWarnings("unchecked")
		PropositionalFormula<PSym> param = (PropositionalFormula<PSym>) phi.getOperands().get(0);
		return clearTree(param).Neg();
	}

	private PropositionalFormula<PSym> _clearTree(PropositionalAND<PSym> phi) {
		if (phi.getOperands().isEmpty())
			return new PropositionalVerum<PSym>(phi.getSignature());
		if (phi.getOperands().size() == 1)
			return clearTree((PropositionalFormula<PSym>) phi.getOperands().get(0));

		HashSet<PropositionalFormula<PSym>> set = new HashSet<PropositionalFormula<PSym>>(phi.getOperands().size());

		for (PropositionalFormula<PSym> form : phi.getOperands()) {
			set.add(clearTree(form));
		}

		HashSet<PropositionalFormula<PSym>> set2 = new HashSet<PropositionalFormula<PSym>>(set.size());
		for (PropositionalFormula<PSym> form : set) {
			if (form instanceof PropositionalAND<?>) {
				set2.addAll(((PropositionalAND<PSym>) form).getOperands().stream()
						.map(a -> (PropositionalFormula<PSym>) a).collect(Collectors.toList()));
			} else if (form instanceof PropositionalVerum<?>)
				continue;
			else if (form instanceof PropositionalFalsum<?>)
				return form;
			else
				set2.add(form);
		}

		if (set2.size() == 0)
			return new PropositionalVerum<PSym>(phi.getSignature());
		if (set2.size() == 1)
			return set2.stream().findFirst().get();

		return new PropositionalAND<PSym>(phi.getSignature(), set2);
	}

	private PropositionalFormula<PSym> _clearTree(PropositionalOR<PSym> phi) {
		if (phi.getOperands().isEmpty())
			return new PropositionalVerum<PSym>(phi.getSignature());
		if (phi.getOperands().size() == 1)
			return clearTree((PropositionalFormula<PSym>) phi.getOperands().get(0));

		HashSet<PropositionalFormula<PSym>> set = new HashSet<PropositionalFormula<PSym>>(phi.getOperands().size());

		for (PropositionalFormula<PSym> form : phi.getOperands()) {
			set.add(clearTree(form));
		}

		HashSet<PropositionalFormula<PSym>> set2 = new HashSet<PropositionalFormula<PSym>>(set.size());
		for (PropositionalFormula<PSym> form : set) {
			if (form instanceof PropositionalOR<?>) {
				set2.addAll(((PropositionalOR<PSym>) form).getOperands().stream()
						.map(a -> (PropositionalFormula<PSym>) a).collect(Collectors.toList()));
			} else if (form instanceof PropositionalFalsum<?>)
				continue;
			else if (form instanceof PropositionalVerum<?>)
				return form;
			else
				set2.add(form);
		}

		if (set2.size() == 0)
			return new PropositionalVerum<PSym>(phi.getSignature());
		if (set2.size() == 1)
			return set2.stream().findFirst().get();

		return new PropositionalOR<PSym>(phi.getSignature(), set2);
	}

	/*******************************************************************
	 * Conjunctive Normal Form
	 ******************************************************************/

	public PropositionalFormula<PSym> formulaToCNF(PropositionalFormula<PSym> phi) {
		return clearTree(_formulaToCNF(formulaToNegationNormalForm(phi)));
	}

	private PropositionalFormula<PSym> _formulaToCNF(PropositionalFormula<PSym> phi) {
		if (phi instanceof PropositionalAtom<?> || phi instanceof PropositionalVerum<?>
				|| phi instanceof PropositionalFalsum<?> || phi instanceof PropositionalNEG<?>)
			return phi;
		if (phi instanceof PropositionalAND<?>)
			return __formulaToCNF((PropositionalAND<PSym>) phi);
		if (phi instanceof PropositionalOR<?>)
			return __formulaToCNF((PropositionalOR<PSym>) phi);

		throw new InvalidParameterException();
	}

	private PropositionalFormula<PSym> __formulaToCNF(PropositionalAND<PSym> phi) {
		ArrayList<PropositionalFormula<PSym>> list = new ArrayList<PropositionalFormula<PSym>>(
				phi.getOperands().size());
		for (SyntacticElement<PropositionalSignature<PSym>> iterable_element : phi.getOperands()) {
			PropositionalFormula<PSym> form = (PropositionalFormula<PSym>) iterable_element;
			list.add(formulaToCNF(form));
		}

		return new PropositionalAND<PSym>(phi.getSignature(), list);
	}

	private PropositionalFormula<PSym> __formulaToCNF(PropositionalOR<PSym> phi) {
		for (PropositionalFormula<PSym> form : phi.getOperands()) {
			if (form instanceof PropositionalAND<?>) {
				PropositionalAND<PSym> and = (PropositionalAND<PSym>) form;
				PropositionalOR<PSym> theRestOR = new PropositionalOR<PSym>(phi.getSignature(),
						phi.getOperands().stream().filter(a -> a != form).collect(Collectors.toList()));
				return new PropositionalAND<PSym>(phi.getSignature(), and.getOperands().stream()
						.map(a -> formulaToCNF(a.Or(theRestOR))).collect(Collectors.toList()));
			}
		}

		return phi;

	}

	/*******************************************************************
	 * Negation Normal Form
	 ******************************************************************/

	public PropositionalFormula<PSym> formulaToNegationNormalForm(PropositionalFormula<PSym> phi) {
		return _formulaToNegationNormalForm(clearTree(phi));
	}

	private PropositionalFormula<PSym> _formulaToNegationNormalForm(PropositionalFormula<PSym> phi) {
		if (phi instanceof PropositionalAtom<?> || phi instanceof PropositionalVerum<?>
				|| phi instanceof PropositionalFalsum<?>)
			return phi;
		if (phi instanceof PropositionalAND<?>)
			return __formulaToNegationNormalForm((PropositionalAND<PSym>) phi);
		if (phi instanceof PropositionalOR<?>)
			return __formulaToNegationNormalForm((PropositionalOR<PSym>) phi);
		if (phi instanceof PropositionalNEG<?>)
			return __formulaToNegationNormalForm((PropositionalNEG<PSym>) phi);

		throw new InvalidParameterException();
	}

	@SuppressWarnings("unchecked")
	private PropositionalFormula<PSym> __formulaToNegationNormalForm(PropositionalNEG<PSym> phi) {
		PropositionalFormula<PSym> param = (PropositionalFormula<PSym>) phi.getOperands().get(0);
		if (param instanceof PropositionalNEG<?>)
			return (PropositionalFormula<PSym>) ((PropositionalNEG<PSym>) param).getOperands().get(0);
		if (param instanceof PropositionalAtom<?>)
			return phi;
		if (param instanceof PropositionalFalsum<?>)
			return new PropositionalVerum<PSym>(phi.getSignature());
		if (param instanceof PropositionalVerum<?>)
			return new PropositionalFalsum<PSym>(phi.getSignature());
		if (param instanceof PropositionalAND<?>) {
			PropositionalAND<PSym> and = (PropositionalAND<PSym>) param;
			ArrayList<PropositionalFormula<PSym>> list = new ArrayList<PropositionalFormula<PSym>>(
					and.getOperands().size());
			for (SyntacticElement<PropositionalSignature<PSym>> iterable_element : and.getOperands()) {
				PropositionalFormula<PSym> form = (PropositionalFormula<PSym>) iterable_element;
				list.add(_formulaToNegationNormalForm(form.Neg()));
			}
			return new PropositionalOR<PSym>(phi.getSignature(), list);
		}
		if (param instanceof PropositionalOR<?>) {
			PropositionalOR<PSym> or = (PropositionalOR<PSym>) param;
			ArrayList<PropositionalFormula<PSym>> list = new ArrayList<PropositionalFormula<PSym>>(
					or.getOperands().size());
			for (SyntacticElement<PropositionalSignature<PSym>> iterable_element : or.getOperands()) {
				PropositionalFormula<PSym> form = (PropositionalFormula<PSym>) iterable_element;
				list.add(_formulaToNegationNormalForm(form.Neg()));
			}
			return new PropositionalAND<PSym>(phi.getSignature(), list);
		}
		throw new InvalidParameterException();
	}

	@SuppressWarnings("unchecked")
	private PropositionalFormula<PSym> __formulaToNegationNormalForm(PropositionalAND<PSym> phi) {
		ArrayList<PropositionalFormula<PSym>> list = new ArrayList<PropositionalFormula<PSym>>(
				phi.getOperands().size());
		for (int i = 0; i < phi.getOperands().size(); i++) {
			list.add(_formulaToNegationNormalForm((PropositionalFormula<PSym>) phi.getOperands().get(i)));
		}
		return new PropositionalAND<PSym>(phi.getSignature(), list);
	}

	@SuppressWarnings("unchecked")
	private PropositionalFormula<PSym> __formulaToNegationNormalForm(PropositionalOR<PSym> phi) {
		ArrayList<PropositionalFormula<PSym>> list = new ArrayList<PropositionalFormula<PSym>>(
				phi.getOperands().size());
		for (int i = 0; i < phi.getOperands().size(); i++) {
			list.add(_formulaToNegationNormalForm((PropositionalFormula<PSym>) phi.getOperands().get(i)));
		}
		return new PropositionalOR<PSym>(phi.getSignature(), list);
	}
}
