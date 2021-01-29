package edu.cs.ai.alchourron.logic.logics.predicatelogics;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.ModelTheoreticLogic;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaAND;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaBiImplication;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaFalsum;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaImplication;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaNeg;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaOR;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaPredicate;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaQuantification;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaVerum;
import edu.cs.ai.alchourron.logic.syntax.formula.LogicalOperator;
import edu.cs.ai.alchourron.logic.syntax.terms.FunctionTerm;
import edu.cs.ai.alchourron.logic.syntax.terms.Term;
import edu.cs.ai.alchourron.logic.syntax.terms.VariableTerm;
import edu.cs.ai.math.setheory.objects.Tuple;

/***
 * Logical System representing propositional logic over symbol space of type
 * {@link PSym}.
 * 
 * @author Kai Sauerwald
 *
 * @param <R> The type for Relationsymbols
 * @param <K> The type for Functionsymbols
 * @param <V> The type for Variables
 */
public class FirstOrderLogic<R extends Enum<R>, K extends Enum<K>, V> implements
		ModelTheoreticLogic<FiniteStructure<?, R, K, FOSignature<R, K, V>>, Formula<FOSignature<R, K, V>>, Boolean, FOSignature<R, K, V>> {

	public Set<V> getFreeVariables(Term<K, V> term) {
		if (term instanceof FunctionTerm<?, ?>) {
			FunctionTerm<K, V> new_name = (FunctionTerm<K, V>) term;

			Set<V> result = new HashSet<V>();
			new_name.getSubTerms().forEach(t -> result.addAll(getFreeVariables(t)));
			return result;
		}
		if (term instanceof VariableTerm<?, ?>) {
			VariableTerm<K, V> new_name = (VariableTerm<K, V>) term;
			return Set.of(new_name.getVariable());
		}
		throw new IllegalArgumentException("unexpected term type");
	}

	public Set<V> getFreeVariables(Formula<FOSignature<R, K, V>> formula) {
		if (formula instanceof FormulaPredicate<?, ?, ?, ?>) {
			FormulaPredicate<R, K, V, FOSignature<R, K, V>> pred = (FormulaPredicate<R, K, V, FOSignature<R, K, V>>) formula;
			Set<V> result = new HashSet<>();
			for (Term<K, V> term : pred.getTerms()) {
				result.addAll(getFreeVariables(term));
			}

			return result;
		}

		if (formula instanceof FormulaQuantification<?, ?, ?>) {
			FormulaQuantification<StandardQuantifier, V, FOSignature<R, K, V>> quantor = (FormulaQuantification<StandardQuantifier, V, FOSignature<R, K, V>>) formula;

			Set<V> result = new HashSet<>(getFreeVariables(quantor.getQuantified()));
			result.removeIf(v -> v.equals(quantor.getVariables()));
			return result;
		}

		if (formula instanceof LogicalOperator<?>) {
			LogicalOperator<FOSignature<R, K, V>> operator = (LogicalOperator<FOSignature<R, K, V>>) formula;

			Set<V> result = new HashSet<>();
			for (Formula<FOSignature<R, K, V>> op : operator.getOperands()) {
				result.addAll(getFreeVariables(op));
			}
			return result;
		}

		throw new IllegalArgumentException("unexpected formula type");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.cs.ai.alchourron.logic.LogicalSystem#eval(edu.cs.ai.alchourron.logic.
	 * Interpretation, edu.cs.ai.alchourron.logic.Formula)
	 */
	@Override
	public Boolean eval(FiniteStructure<?, R, K, FOSignature<R, K, V>> interpretation,
			Formula<FOSignature<R, K, V>> formula) {
		if (getFreeVariables(formula).isEmpty()) {
			return eval(interpretation, formula, var -> null);
		}
		throw new UnsupportedOperationException("Not implemeted yed");
	}

	/***
	 * Evaluations a formula under a valuation function
	 * 
	 * @author Kai Sauerwald
	 * @param interpretation The interpretation
	 * @param formula        The formula to be interpreted
	 * @param valuation      A valuation for the variables of type <V>
	 */
	public <U> Boolean eval(FiniteStructure<U, R, K, FOSignature<R, K, V>> interpretation,
			Formula<FOSignature<R, K, V>> formula, Function<V, U> valuation) {

		if (formula instanceof FormulaPredicate<?, ?, ?, ?>) {
			FormulaPredicate<R, K, V, FOSignature<R, K, V>> pred = (FormulaPredicate<R, K, V, FOSignature<R, K, V>>) formula;
			Tuple<U> tuple = new Tuple<>(pred.getTerms().stream().sorted().map(t -> eval(interpretation, t, valuation))
					.collect(Collectors.toUnmodifiableList()));
			return interpretation.getRelation(pred.getSymbol()).contains(tuple);
		}
		if (formula instanceof FormulaQuantification<?, ?, ?>) {
			FormulaQuantification<StandardQuantifier, V, FOSignature<R, K, V>> quantor = (FormulaQuantification<StandardQuantifier, V, FOSignature<R, K, V>>) formula;
			if (quantor.getQuantifyer() == StandardQuantifier.FORALL) {
				return interpretation.getUniverse().stream().allMatch(u -> {

					Function<V, U> nval = var -> {
						if (var.equals(quantor.getVariables()))
							return u;
						return valuation.apply(var);
					};

					return eval(interpretation, quantor.getQuantified(), nval);
				});
			}
			if (quantor.getQuantifyer() == StandardQuantifier.EXISTS) {
				return interpretation.getUniverse().stream().anyMatch(u -> {

					Function<V, U> nval = var -> {
						if (var.equals(quantor.getVariables()))
							return u;
						return valuation.apply(var);
					};

					return eval(interpretation, quantor.getQuantified(), nval);
				});
			}
		}
		if (formula instanceof FormulaAND<?>) {
			FormulaAND<FOSignature<R, K, V>> and = (FormulaAND<FOSignature<R, K, V>>) formula;
			return and.getOperands().stream().allMatch(o -> eval(interpretation, o, valuation));
		}
		if (formula instanceof FormulaOR<?>) {
			FormulaOR<FOSignature<R, K, V>> or = (FormulaOR<FOSignature<R, K, V>>) formula;
			return or.getOperands().stream().anyMatch(o -> eval(interpretation, o, valuation));
		}
		if (formula instanceof FormulaImplication<?>) {
			FormulaImplication<FOSignature<R, K, V>> implication = (FormulaImplication<FOSignature<R, K, V>>) formula;
			return !eval(interpretation, implication.getPremise(), valuation)
					|| eval(interpretation, implication.getConclusion(), valuation);
		}

		if (formula instanceof FormulaBiImplication<?>) {
			FormulaBiImplication<FOSignature<R, K, V>> implication = (FormulaBiImplication<FOSignature<R, K, V>>) formula;
			boolean b1 = eval(interpretation, implication.getFirst(), valuation);
			boolean b2 = eval(interpretation, implication.getSecond(), valuation);
			return (b1 && b2) || (!b1 && !b2);
		}
		if (formula instanceof FormulaNeg<?>) {
			FormulaNeg<FOSignature<R, K, V>> neg = (FormulaNeg<FOSignature<R, K, V>>) formula;
			return !eval(interpretation, neg.getOperands().get(0), valuation);
		}
		if (formula instanceof FormulaFalsum<?>) {
			return false;
		}
		if (formula instanceof FormulaVerum<?>) {
			return true;
		}

		throw new IllegalArgumentException("unexpected formula type");
	}

	/***
	 * Evaluations a term under a valuation function
	 * 
	 * @author Kai Sauerwald
	 * @param interpretation The interpretation
	 * @param term           The term to be interpreted
	 * @param valuation      A valuation for the variables of type <V>
	 */
	public <U> U eval(FiniteStructure<U, R, K, FOSignature<R, K, V>> interpretation, Term<K, V> term,
			Function<V, U> valuation) {
		if (term instanceof FunctionTerm<?, ?>) {
			FunctionTerm<K, V> fterm = (FunctionTerm<K, V>) term;
			Tuple<U> tuple = new Tuple<>(fterm.getSubTerms().stream().sorted()
					.map(t -> eval(interpretation, t, valuation)).collect(Collectors.toUnmodifiableList()));

			return interpretation.getFunction(fterm.getSymbol()).apply(tuple);
		}
		if (term instanceof VariableTerm<?, ?>) {
			VariableTerm<K, V> vterm = (VariableTerm<K, V>) term;
			return valuation.apply(vterm.getVariable());
		}
		throw new InvalidParameterException("Unexpeted term");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.cs.ai.alchourron.logic.LogicalSystem#entails(edu.cs.ai.alchourron.logic.
	 * Interpretation, edu.cs.ai.alchourron.logic.Formula)
	 */
	@Override
	public boolean satisfies(FiniteStructure<?, R, K, FOSignature<R, K, V>> interpretation,
			Formula<FOSignature<R, K, V>> formula) {

		return eval(interpretation, formula);
	}

	@Override
	public boolean validFormula(Formula<FOSignature<R, K, V>> formula) {
		return true;
	}

}