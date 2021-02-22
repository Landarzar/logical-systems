package edu.cs.ai.alchourron.logic;

import java.util.Set;
import java.util.function.Function;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <T> The type of truth values
 * @param <S> The type of signature
 * @param <F> The type of formula
 * @param <I> The type of interpretations
 */
public interface ModelTheoreticLogic<I extends Interpretation<S>, F extends Formula<S>, T, S extends Signature> {

	/***
	 * Checks where the given Formula is (syntactically) valid in the Logical
	 * System.
	 * 
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
	 * @throws UnsupportedOperationException Maybe:
	 *                                       <ol>
	 *                                       <li>There is no (suitable) way to represent
	 *                                       the set of all models of the formula,
	 *                                       or</li>
	 *                                       <li>there is no notion of model,</li>
	 *                                       <li>or the author of the implementation
	 *                                       was lazy.</li>
	 *                                       </ol>
	 */
	public default Set<I> modelsOf(F formula, S signature) throws UnsupportedOperationException {
		throw new UnsupportedOperationException(
				"This logic does not support enumeration of models, or it is not implemeted (yet).");
	};

	/***
	 * Computes the truth value (type of {@link T}) of {@link formula} under the
	 * interpretation {@link interpretation}.
	 * 
	 * @param interpretation The interpretation
	 * @param formula        The formula to be interpreted
	 * @return The corresponding truth value.
	 * @author Kai Sauerwald
	 */
	public T eval(I interpretation, F formula);

	/***
	 * Checks where {@link I} entails {@link formula}. This must map the (possible)
	 * many valuedness of the logic to a binary decision.
	 * 
	 * The version for closed formula.
	 * 
	 * @author Kai Sauerwald
	 * @param interpretation The interpretation {@link I}
	 * @param formula        The formula
	 * @return {@literal true} if the interpretation {@link I} entails
	 *         {@link formula}, otherwise it returns {@literal false}. 
	 * @throws UnsupportedOperationException If this model theory may provides no satisfaction relation.
	 */
	public boolean satisfies(I interpretation, F formula);

	/***
	 * Represents the (monotonic) logical entailment
	 * 
	 * @author Kai Sauerwald
	 * @param signature
	 * @param f1        The first operand
	 * @param f2        The second operand
	 * @return
	 * @throws UnsupportedOperationException If the implemented logic not have
	 *                                       (decidable) notion of logical
	 *                                       entailment. (Or the {@link models}
	 *                                       throws an exception on a call with
	 *                                       {@link f1} or {@link f2})
	 */
	public default boolean entails(F f1, F f2,S signature) {
		Set<I> mf2 = modelsOf(f2,signature);
		Set<I> mf1 = modelsOf(f1,signature);
		return mf2.containsAll(mf1);
	}
}
