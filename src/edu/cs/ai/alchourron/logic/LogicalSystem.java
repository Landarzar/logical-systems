package edu.cs.ai.alchourron.logic;

import java.util.Set;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <S>
 *            The type of signature
 * @param <F>
 *            The type of formula
 * @param <I>
 *            The type of interpretations
 * @param <T>
 *            The type of truth values
 */
public interface LogicalSystem<T, S extends Signature, F extends Formula<S>, I extends Interpretation<S>> {
	
	/***
	 * Checks where the given Formula is (syntactically) valid in the Logical System.
	 * @author Kai Sauerwald
	 * @param formula The formula
	 * @return True if {@link formula} is a valid formula
	 */
	public boolean validFormula(F formula);

	/***
	 * Gets the set of all models of the formula
	 * 
	 * @author Kai Sauerwald
	 * @param formula
	 * @return
	 * @throws UnsupportedOperationException
	 *             Maybe:
	 *             <ol>
	 *             <li>There is no finite way to represent the set of all models of
	 *             the formula, or</li>
	 *             <li>there is no notion of model,</li>
	 *             <li>or the author of the implementation was lazy.</li>
	 *             </ol>
	 */
	public Set<I> modelsOf(F formula);

	/***
	 * Computes the truth value (type of {@link T}) of {@link formula} under the
	 * interpretation {@link interpretation}.
	 * 
	 * @param interpretation
	 *            The interpretation
	 * @param formula
	 *            The formula to be interpreted
	 * @return The corresponding truth value.
	 * @author Kai Sauerwald
	 */
	public T eval(I interpretation, F formula);

	/***
	 * Checks where {@link I} entails {@link formula}. This must map the (possible) many valuedness of the logic to a
	 * binary decision.
	 * 
	 * @author Kai Sauerwald
	 * @param interpretation The interpretation
	 * @param formula The formula
	 * @return {@literal true} if the interpretation {@link I} entails {@link formula}, otherwise it returns {@literal false}.
	 */
	public boolean entails(I interpretation, F formula);

	/***
	 * Represents the logical entailment
	 * 
	 * @author Kai Sauerwald
	 * @param interpretation
	 * @param f1 The first operand
	 * @param f2 The second operand
	 * @return
	 * @throws UnsupportedOperationException
	 *             If the implemented logic not have (decidable) notion of logical
	 *             entailment. (Or the {@link models} throws an exception on a call with {@link f1} or {@link f2})
	 */
	public default boolean entails(F f1, F f2) {
		Set<I> mf2 = modelsOf(f2);
		Set<I> mf1 = modelsOf(f1);
		return mf2.containsAll(mf1);
	}
}
