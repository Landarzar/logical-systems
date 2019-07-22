package edu.cs.ai.alchourron.logic.propositional;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalAND;
import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalAtom;
import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalFalsum;
import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalNEG;
import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalOR;
import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalVerum;
import edu.cs.ai.alchourron.logic.syntax.SyntacticElement;

public class PropositionalNormalForms<PSym> {

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

		ArrayList<PropositionalFormula<PSym>> list = new ArrayList<PropositionalFormula<PSym>>(
				phi.getOperands().size());

		for (int i = 1; i < phi.getOperands().size(); i++) {
			PropositionalFormula<PSym> form = (PropositionalFormula<PSym>) phi.getOperands().get(i);
			list.add(clearTree(form));
		}

		ArrayList<PropositionalFormula<PSym>> list2 = new ArrayList<PropositionalFormula<PSym>>(list.size());
		for (PropositionalFormula<PSym> form : list) {
			if (form instanceof PropositionalAND<?>) {
				list2.addAll(((PropositionalAND<PSym>) form).getOperands().stream()
						.map(a -> (PropositionalFormula<PSym>) a).collect(Collectors.toList()));
			} else if (form instanceof PropositionalVerum<?>)
				continue;
			else if (form instanceof PropositionalFalsum<?>)
				return form;
			else
				list2.add(form);
		}

		if (list2.size() == 0)
			return new PropositionalVerum<PSym>(phi.getSignature());
		if (list2.size() == 1)
			return list2.get(0);

		return new PropositionalAND<PSym>(phi.getSignature(), list2);
	}

	private PropositionalFormula<PSym> _clearTree(PropositionalOR<PSym> phi) {
		if (phi.getOperands().isEmpty())
			return new PropositionalVerum<PSym>(phi.getSignature());
		if (phi.getOperands().size() == 1)
			return clearTree((PropositionalFormula<PSym>) phi.getOperands().get(0));

		ArrayList<PropositionalFormula<PSym>> list = new ArrayList<PropositionalFormula<PSym>>(
				phi.getOperands().size());

		for (int i = 1; i < phi.getOperands().size(); i++) {
			PropositionalFormula<PSym> form = (PropositionalFormula<PSym>) phi.getOperands().get(i);
			list.add(clearTree(form));
		}

		ArrayList<PropositionalFormula<PSym>> list2 = new ArrayList<PropositionalFormula<PSym>>(list.size());
		for (PropositionalFormula<PSym> form : list) {
			if (form instanceof PropositionalOR<?>) {
				list2.addAll(((PropositionalOR<PSym>) form).getOperands().stream()
						.map(a -> (PropositionalFormula<PSym>) a).collect(Collectors.toList()));
			} else if (form instanceof PropositionalFalsum<?>)
				continue;
			else if (form instanceof PropositionalVerum<?>)
				return form;
			else
				list2.add(form);
		}

		if (list2.size() == 0)
			return new PropositionalVerum<PSym>(phi.getSignature());
		if (list2.size() == 1)
			return list2.get(0);

		return new PropositionalOR<PSym>(phi.getSignature(), list2);
	}

	/*******************************************************************
	 * Conjunctive Normal Form
	 ******************************************************************/

	public PropositionalFormula<PSym> formulaToCNF(PropositionalFormula<PSym> phi) {
		return _formulaToCNF(formulaToNegationNormalForm(phi));
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
			list.add(_formulaToCNF(form));
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
						.map(a -> _formulaToCNF(a.Or(theRestOR))).collect(Collectors.toList()));
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
