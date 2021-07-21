
package edu.cs.ai.alchourron.logic.logics.propositional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.semantics.interpretations.PropositionalInterpretation;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaAND;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaAtom;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaFalsum;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaNeg;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaOR;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaPropositionalAtom;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaVerum;
import edu.cs.ai.math.combinatorics.PowerSet;
import edu.cs.algo.QMCAlgorithm;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <P> Type of atoms
 */
public class PropositionalNormalForms<P> {

	public Formula<PropositionalSignature<P>> computeQMC(Set<PropositionalInterpretation<P>> models, PropositionalSignature<P> signature){
		PropositionalLogic<P> logic = new  PropositionalLogic<P>();

		ArrayList<ArrayList<Integer>> evaluationsTable = new ArrayList<>(models.size());

		/* Fill table of evaluations
		 * one row per minterm
		 * true = 1 , false = 0
		 */
		for (PropositionalInterpretation<P> ip : models){
			ArrayList<Integer> eval = new ArrayList<>(signature.getPropositions().size());
			for (P symbol : signature.getPropositions()){
				if (ip.isTrue(symbol))
					eval.add(1);
				else
					eval.add(0);
			}
			evaluationsTable.add(eval);
		}

		HashSet<ArrayList<Integer>> primeImplicants = QMCAlgorithm.getPrimeImplicants(evaluationsTable);
		Map<ArrayList<Integer>, ArrayList<Boolean>> piChart = QMCAlgorithm.getPIChart(primeImplicants);
		HashSet<ArrayList<Integer>> finalImplicants = QMCAlgorithm.getFinalImplicants(piChart);


		List<P> variables = new ArrayList<>(signature.getPropositions());
		HashSet<Formula<PropositionalSignature<P>>> implicants = new HashSet<>(finalImplicants.size());

		for ( ArrayList<Integer> finalImplicant : finalImplicants){
			HashSet<Formula<PropositionalSignature<P>>> set = new HashSet<>();
			// if there is only one final implicant, which one contains "don't care" (-1), this formula is equivalent to "top"
			if (finalImplicants.size()==1 && !(finalImplicant.contains(1) || finalImplicant.contains(0)))
				return new FormulaVerum<>();
			for (int i = 0; i < finalImplicant.size(); i++) {
				if (finalImplicant.get(i) == -1)
					continue;
				else if (finalImplicant.get(i) == 1)
					set.add( new FormulaPropositionalAtom<>(variables.get(i)) );
				else
					set.add( new FormulaNeg<>( new FormulaPropositionalAtom<>(variables.get(i)) ));
			}
			if (!set.isEmpty())
				implicants.add(set.size() > 1 ? new FormulaAND<>(set) : set.iterator().next());
		}

		return implicants.size() > 1 ? new FormulaOR<>(implicants) : implicants.iterator().next();
	}
	
	public Formula<PropositionalSignature<P>> computeQMC(Formula<PropositionalSignature<P>> formula, PropositionalSignature<P> signature){
		PropositionalLogic<P> logic = new  PropositionalLogic<P>();
		Set<PropositionalInterpretation<P>> models = logic.modelsOf(formula, new PropositionalSignature<>(formula));

		return this.computeQMC(models, signature);
	}

	/*******************************************************************
	 * Test Horn
	 ******************************************************************/

	/**
	 * Tests where the given input CNF-Formula is horn. The correctness of this
	 * function is not garuanteed if cnf is not in conjunctive normal form.
	 * 
	 * @param cnf
	 */
	public boolean isHorn(Formula<PropositionalSignature<P>> cnf) {

		if (cnf instanceof FormulaAND<?>) {
			FormulaAND<PropositionalSignature<P>> and = (FormulaAND<PropositionalSignature<P>>) cnf;
			for (Formula<PropositionalSignature<P>> iterable_element : and.getOperands()) {
				if (iterable_element instanceof FormulaOR<?>) {
					FormulaOR<PropositionalSignature<P>> or = (FormulaOR<PropositionalSignature<P>>) iterable_element;

					int number_of_positive_literals = 0;
					for (Formula<PropositionalSignature<P>> literal : or.getOperands()) {
						if (!(literal instanceof FormulaNeg<?>))
							number_of_positive_literals += 1;
						if (number_of_positive_literals > 1)
							return false;
					}
				}
			}
		}
		if (cnf instanceof FormulaOR<?>) {
			FormulaOR<PropositionalSignature<P>> or = (FormulaOR<PropositionalSignature<P>>) cnf;
			int number_of_positive_literals = 0;
			for (Formula<PropositionalSignature<P>> literal : or.getOperands()) {
				if (!(literal instanceof FormulaNeg<?>))
					number_of_positive_literals += 1;
				if (number_of_positive_literals > 1)
					return false;
			}
		}

		return true;
	}

	/***
	 * Tests if a the formula phi is horndefinable.
	 * 
	 * We use the characterization by Makowsky (1987): A formula is horndefinable
	 * if for any extension by positive atoms there exists a generic assignment.
	 * @author Kai Sauerwald
	 * @param phi formula
	 * @param signature signature
	 */
	public boolean isHornDefinable(Formula<PropositionalSignature<P>> phi, PropositionalSignature<P> signature) {
		// We use the characterization by Makowsky (1987): A formula is horndefinable
		// if for any extension by positive atoms there exists a generic assignment.

		// Signature
		return PowerSet.stream(signature.getPropositions()).allMatch(set -> {
			// Build formula:
			Formula<PropositionalSignature<P>> formula = phi;
			for (P p : set) {
				formula = new FormulaAND<>(formula, new FormulaPropositionalAtom<>(p));
			}

			// i hate java.....
			final Formula<PropositionalSignature<P>> tmp = formula;
			final PropositionalLogic<P> logic = new PropositionalLogic<>();

			// search for a generic interpretation
			return signature.stream().noneMatch(intp -> logic.satisfies(intp, tmp))
					|| signature.stream().anyMatch(intp -> isGeneric(intp, tmp));
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
	public boolean isGeneric(PropositionalInterpretation<P> intp, Formula<PropositionalSignature<P>> formula) {
		PropositionalLogic<P> logic = new PropositionalLogic<>();
		if (!logic.satisfies(intp, formula))
			return false;

		return intp.getSignature().getPropositions().stream().allMatch(sigma -> {
			return logic.satisfies(intp, new FormulaPropositionalAtom<>(sigma)) == logic.entails(formula,
					new FormulaPropositionalAtom<>(sigma), intp.getSignature());
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
	public Formula<PropositionalSignature<P>> clearTree(Formula<PropositionalSignature<P>> phi) {
		if (phi instanceof FormulaAtom<?>)
			return phi;
		if (phi instanceof FormulaAND<?>)
			return _clearTree((FormulaAND<PropositionalSignature<P>>) phi);
		if (phi instanceof FormulaOR<?>)
			return _clearTree((FormulaOR<PropositionalSignature<P>>) phi);
		if (phi instanceof FormulaNeg<?>)
			return _clearTree((FormulaNeg<PropositionalSignature<P>>) phi);
		return phi;
	}

	private Formula<PropositionalSignature<P>> _clearTree(FormulaNeg<PropositionalSignature<P>> phi) {
		@SuppressWarnings("unchecked")
		Formula<PropositionalSignature<P>> param = (Formula<PropositionalSignature<P>>) phi.getOperands().get(0);
		return new FormulaNeg<PropositionalSignature<P>>(clearTree(param));
	}

	private Formula<PropositionalSignature<P>> _clearTree(FormulaAND<PropositionalSignature<P>> phi) {
		if (phi.getOperands().isEmpty())
			throw new IllegalArgumentException();
//			return new FormulaVerum<PropositionalSignature<P>>();
		if (phi.getOperands().size() == 1)
			return clearTree((Formula<PropositionalSignature<P>>) phi.getOperands().get(0));

		HashSet<Formula<PropositionalSignature<P>>> set = new HashSet<Formula<PropositionalSignature<P>>>(
				phi.getOperands().size());

		for (Formula<PropositionalSignature<P>> form : phi.getOperands()) {
			set.add(clearTree(form));
		}

		HashSet<Formula<PropositionalSignature<P>>> set2 = new HashSet<Formula<PropositionalSignature<P>>>(set.size());
		for (Formula<PropositionalSignature<P>> form : set) {
			if (form instanceof FormulaAND<?>) {
				set2.addAll(((FormulaAND<PropositionalSignature<P>>) form).getOperands().stream()
						.map(a -> (Formula<PropositionalSignature<P>>) a).collect(Collectors.toList()));
			} else if (form instanceof FormulaVerum<?>)
				continue;
			else if (form instanceof FormulaFalsum<?>)
				return form;
			else
				set2.add(form);
		}

		if (set2.size() == 0)
			return new FormulaVerum<PropositionalSignature<P>>();
		if (set2.size() == 1)
			return set2.stream().findFirst().get();

		return new FormulaAND<PropositionalSignature<P>>(set2);
	}

	private Formula<PropositionalSignature<P>> _clearTree(FormulaOR<PropositionalSignature<P>> phi) {
		if (phi.getOperands().isEmpty())
			return new FormulaVerum<PropositionalSignature<P>>();
		if (phi.getOperands().size() == 1)
			return clearTree((Formula<PropositionalSignature<P>>) phi.getOperands().get(0));

		HashSet<Formula<PropositionalSignature<P>>> set = new HashSet<Formula<PropositionalSignature<P>>>(
				phi.getOperands().size());

		for (Formula<PropositionalSignature<P>> form : phi.getOperands()) {
			set.add(clearTree(form));
		}

		HashSet<Formula<PropositionalSignature<P>>> set2 = new HashSet<Formula<PropositionalSignature<P>>>(set.size());
		for (Formula<PropositionalSignature<P>> form : set) {
			if (form instanceof FormulaOR<?>) {
				set2.addAll(((FormulaOR<PropositionalSignature<P>>) form).getOperands().stream()
						.map(a -> (Formula<PropositionalSignature<P>>) a).collect(Collectors.toList()));
			} else if (form instanceof FormulaFalsum<?>)
				continue;
			else if (form instanceof FormulaVerum<?>)
				return form;
			else
				set2.add(form);
		}

		if (set2.size() == 0)
			return new FormulaVerum<PropositionalSignature<P>>();
		if (set2.size() == 1)
			return set2.stream().findFirst().get();

		return new FormulaOR<PropositionalSignature<P>>(set2);
	}

	/*******************************************************************
	 * Conjunctive Normal Form
	 ******************************************************************/

	public Formula<PropositionalSignature<P>> formulaToCNF(Formula<PropositionalSignature<P>> phi) {
		return clearTree(_formulaToCNF(formulaToNegationNormalForm(phi)));
	}

	private Formula<PropositionalSignature<P>> _formulaToCNF(Formula<PropositionalSignature<P>> phi) {
		if (phi instanceof FormulaAtom<?> || phi instanceof FormulaNeg<?>)
			return phi;
		if (phi instanceof FormulaAND<?>)
			return __formulaToCNF((FormulaAND<PropositionalSignature<P>>) phi);
		if (phi instanceof FormulaOR<?>)
			return __formulaToCNF((FormulaOR<PropositionalSignature<P>>) phi);

		throw new IllegalArgumentException();
	}

	private Formula<PropositionalSignature<P>> __formulaToCNF(FormulaAND<PropositionalSignature<P>> phi) {
		ArrayList<Formula<PropositionalSignature<P>>> list = new ArrayList<Formula<PropositionalSignature<P>>>(
				phi.getOperands().size());
		for (Formula<PropositionalSignature<P>> iterable_element : phi.getOperands()) {
			Formula<PropositionalSignature<P>> form = (Formula<PropositionalSignature<P>>) iterable_element;
			list.add(formulaToCNF(form));
		}

		return new FormulaAND<PropositionalSignature<P>>(list);
	}

	private Formula<PropositionalSignature<P>> __formulaToCNF(FormulaOR<PropositionalSignature<P>> phi) {
		for (Formula<PropositionalSignature<P>> form : phi.getOperands()) {
			if (form instanceof FormulaAND<?>) {
				FormulaAND<PropositionalSignature<P>> and = (FormulaAND<PropositionalSignature<P>>) form;
				FormulaOR<PropositionalSignature<P>> theRestOR = new FormulaOR<PropositionalSignature<P>>(
						phi.getOperands().stream().filter(a -> a != form).collect(Collectors.toList()));
				return new FormulaAND<PropositionalSignature<P>>(and.getOperands().stream()
						.map(a -> formulaToCNF(new FormulaOR<PropositionalSignature<P>>(a, theRestOR)))
						.collect(Collectors.toList()));
			}
		}

		return phi;

	}

	/*******************************************************************
	 * Negation Normal Form
	 ******************************************************************/

	public Formula<PropositionalSignature<P>> formulaToNegationNormalForm(Formula<PropositionalSignature<P>> phi) {
		return _formulaToNegationNormalForm(clearTree(phi));
	}

	private Formula<PropositionalSignature<P>> _formulaToNegationNormalForm(Formula<PropositionalSignature<P>> phi) {
		if (phi instanceof FormulaAtom<?>)
			return phi;
		if (phi instanceof FormulaAND<?>)
			return __formulaToNegationNormalForm((FormulaAND<PropositionalSignature<P>>) phi);
		if (phi instanceof FormulaOR<?>)
			return __formulaToNegationNormalForm((FormulaOR<PropositionalSignature<P>>) phi);
		if (phi instanceof FormulaNeg<?>)
			return __formulaToNegationNormalForm((FormulaNeg<PropositionalSignature<P>>) phi);

		throw new IllegalArgumentException();
	}

	@SuppressWarnings("unchecked")
	private Formula<PropositionalSignature<P>> __formulaToNegationNormalForm(
			FormulaNeg<PropositionalSignature<P>> phi) {
		Formula<PropositionalSignature<P>> param = (Formula<PropositionalSignature<P>>) phi.getOperands().get(0);
		if (param instanceof FormulaNeg<?>)
			return (Formula<PropositionalSignature<P>>) ((FormulaNeg<PropositionalSignature<P>>) param).getOperands()
					.get(0);
		if (param instanceof FormulaAtom<?>)
			return phi;
		if (param instanceof FormulaAND<?>) {
			FormulaAND<PropositionalSignature<P>> and = (FormulaAND<PropositionalSignature<P>>) param;
			ArrayList<Formula<PropositionalSignature<P>>> list = new ArrayList<Formula<PropositionalSignature<P>>>(
					and.getOperands().size());
			for (Formula<PropositionalSignature<P>> iterable_element : and.getOperands()) {
				Formula<PropositionalSignature<P>> form = (Formula<PropositionalSignature<P>>) iterable_element;
				list.add(_formulaToNegationNormalForm(new FormulaNeg<PropositionalSignature<P>>(form)));
			}
			return new FormulaOR<PropositionalSignature<P>>(list);
		}
		if (param instanceof FormulaOR<?>) {
			FormulaOR<PropositionalSignature<P>> or = (FormulaOR<PropositionalSignature<P>>) param;
			ArrayList<Formula<PropositionalSignature<P>>> list = new ArrayList<Formula<PropositionalSignature<P>>>(
					or.getOperands().size());
			for (Formula<PropositionalSignature<P>> iterable_element : or.getOperands()) {
				Formula<PropositionalSignature<P>> form = (Formula<PropositionalSignature<P>>) iterable_element;
				list.add(_formulaToNegationNormalForm(new FormulaNeg<>(form)));
			}
			return new FormulaAND<PropositionalSignature<P>>(list);
		}
		throw new IllegalArgumentException();
	}

	@SuppressWarnings("unchecked")
	private Formula<PropositionalSignature<P>> __formulaToNegationNormalForm(
			FormulaAND<PropositionalSignature<P>> phi) {
		ArrayList<Formula<PropositionalSignature<P>>> list = new ArrayList<Formula<PropositionalSignature<P>>>(
				phi.getOperands().size());
		for (int i = 0; i < phi.getOperands().size(); i++) {
			list.add(_formulaToNegationNormalForm((Formula<PropositionalSignature<P>>) phi.getOperands().get(i)));
		}
		return new FormulaAND<PropositionalSignature<P>>(list);
	}

	@SuppressWarnings("unchecked")
	private Formula<PropositionalSignature<P>> __formulaToNegationNormalForm(FormulaOR<PropositionalSignature<P>> phi) {
		ArrayList<Formula<PropositionalSignature<P>>> list = new ArrayList<Formula<PropositionalSignature<P>>>(
				phi.getOperands().size());
		for (int i = 0; i < phi.getOperands().size(); i++) {
			list.add(_formulaToNegationNormalForm((Formula<PropositionalSignature<P>>) phi.getOperands().get(i)));
		}
		return new FormulaOR<PropositionalSignature<P>>(list);
	}
}
