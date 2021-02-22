package edu.cs.ai.alchourron.logic.logics.predicatelogics;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.ModelTheoreticLogic;
import edu.cs.ai.alchourron.logic.semantics.interpretations.FiniteStructure;
import edu.cs.ai.alchourron.logic.syntax.formula.ClassicalQuantifier;
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
import edu.cs.ai.math.settheory.Tuple;

/***
 * Logical System representing first order logic.
 * 
 * @author Kai Sauerwald
 *
 * @param <R> The type for Relationsymbols
 * @param <K> The type for Functionsymbols
 * @param <V> The type for Variables
 * @param <Q> The type of quantors
 * @param <S> The signature type
 */
public class GeneralizedFirstOrderLogic<R, K, V, Q, S extends GeneralizedFOSignature<R, K, V, Q>> implements
		ModelTheoreticLogic<FiniteStructure<?, R, K, S>, Formula<S>, Boolean, S> {

	public Set<V> getFreeVariables(Term<S> term) {
		if (term instanceof FunctionTerm<?, ?>) {
			FunctionTerm<K, S> new_name = (FunctionTerm<K, S>) term;

			Set<V> result = new HashSet<V>();
			new_name.getSubTerms().forEach(t -> result.addAll(getFreeVariables(t)));
			return result;
		}
		if (term instanceof VariableTerm<?, ?>) {
			VariableTerm<V, S> new_name = (VariableTerm<V, S>) term;
			return Set.of(new_name.getVariable());
		}
		throw new IllegalArgumentException("unexpected term type");
	}

	public Set<V> getFreeVariables(Formula<S> formula) {
		if (formula instanceof FormulaPredicate<?,  ?>) {
			FormulaPredicate<R, S> pred = (FormulaPredicate<R, S>) formula;
			Set<V> result = new HashSet<>();
			for (Term<S> term : pred.getTerms()) {
				result.addAll(getFreeVariables(term));
			}

			return result;
		}

		if (formula instanceof FormulaQuantification<?, ?, ?>) {
			FormulaQuantification<Q, V, S> quantor = (FormulaQuantification<Q, V, S>) formula;

			Set<V> result = new HashSet<>(getFreeVariables(quantor.getQuantified()));
			result.removeIf(v -> v.equals(quantor.getVariables()));
			return result;
		}

		if (formula instanceof LogicalOperator<?>) {
			LogicalOperator<S> operator = (LogicalOperator<S>) formula;

			Set<V> result = new HashSet<>();
			for (Formula<S> op : operator.getOperands()) {
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
	public Boolean eval(FiniteStructure<?, R, K, S> interpretation,
			Formula<S> formula) {
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
	public <U> Boolean eval(FiniteStructure<U, R, K, S> interpretation,
			Formula<S> formula, Function<V, U> valuation) {

		if (formula instanceof FormulaPredicate<?, ?>) {
			FormulaPredicate<R, S> pred = (FormulaPredicate<R, S>) formula;
			Tuple<U> tuple = new Tuple<U>(pred.getTerms().stream().sorted().map(t -> eval(interpretation, t, valuation))
					.collect(Collectors.toUnmodifiableList()));
			return interpretation.getRelation(pred.getSymbol()).contains(tuple);
		}
		if (formula instanceof FormulaQuantification<?, ?, ?>) {
			FormulaQuantification<Q, V, S> quantor = (FormulaQuantification<Q, V, S>) formula;
			if (quantor.getQuantifyer() == ClassicalQuantifier.FORALL) {
				return interpretation.getUniverse().stream().allMatch(u -> {

					Function<V, U> nval = var -> {
						if (var.equals(quantor.getVariables()))
							return u;
						return valuation.apply(var);
					};

					return eval(interpretation, quantor.getQuantified(), nval);
				});
			}
			if (quantor.getQuantifyer() == ClassicalQuantifier.EXISTS) {
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
			FormulaAND<S> and = (FormulaAND<S>) formula;
			return and.getOperands().stream().allMatch(o -> eval(interpretation, o, valuation));
		}
		if (formula instanceof FormulaOR<?>) {
			FormulaOR<S> or = (FormulaOR<S>) formula;
			return or.getOperands().stream().anyMatch(o -> eval(interpretation, o, valuation));
		}
		if (formula instanceof FormulaImplication<?>) {
			FormulaImplication<S> implication = (FormulaImplication<S>) formula;
			return !eval(interpretation, implication.getPremise(), valuation)
					|| eval(interpretation, implication.getConclusion(), valuation);
		}

		if (formula instanceof FormulaBiImplication<?>) {
			FormulaBiImplication<S> implication = (FormulaBiImplication<S>) formula;
			boolean b1 = eval(interpretation, implication.getFirst(), valuation);
			boolean b2 = eval(interpretation, implication.getSecond(), valuation);
			return (b1 && b2) || (!b1 && !b2);
		}
		if (formula instanceof FormulaNeg<?>) {
			FormulaNeg<S> neg = (FormulaNeg<S>) formula;
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
	public <U> U eval(FiniteStructure<U, R, K, S> interpretation, Term<S> term,
			Function<V, U> valuation) {
		if (term instanceof FunctionTerm<?, ?>) {
			FunctionTerm<K,S> fterm = (FunctionTerm<K,S>) term;
			Tuple<U> tuple = new Tuple<>(fterm.getSubTerms().stream().sorted()
					.map(t -> eval(interpretation, t, valuation)).collect(Collectors.toUnmodifiableList()));

			return interpretation.getFunction(fterm.getSymbol()).apply(tuple);
		}
		if (term instanceof VariableTerm<?, ?>) {
			VariableTerm<V,S> vterm = (VariableTerm<V,S>) term;
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
	public boolean satisfies(FiniteStructure<?, R, K, S> interpretation,
			Formula<S> formula) {

		return eval(interpretation, formula);
	}

	@Override
	public boolean validFormula(Formula<S> formula) {
		return true;
	}

}
