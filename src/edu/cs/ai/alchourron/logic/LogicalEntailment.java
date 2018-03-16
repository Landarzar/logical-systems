package edu.cs.ai.alchourron.logic;


/***
 * Instances of the interface providing the ability to check where a formula entails another
 * @author Kai Sauerwald
 * @param <R> The type of belief representations
 * @param <F> The type of formulas
 */
public interface LogicalEntailment <S extends Signature, F extends Formula<S>> {
	
	/***
	 * Answers the question where a formula is entailed by a another formula.
	 * @author Kai Sauerwald
	 * @param formula The formula
	 * @return {@literal true} if the formula {@link formula} can be inferred from {@link br} 
	 */
	public boolean entails (F f1, F f2);
	
}
