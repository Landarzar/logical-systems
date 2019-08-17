package edu.cs.ai.alchourron.logic.conditional.propositional.de_finetti;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import edu.cs.ai.alchourron.logic.conditional.Conditional;
import edu.cs.ai.alchourron.logic.propositional.PropositionalFormula;
import edu.cs.ai.alchourron.logic.propositional.PropositionalInterpretation;
import edu.cs.ai.alchourron.logic.propositional.PropositionalLogic;
import edu.cs.ai.alchourron.logic.propositional.PropositionalSignature;
import edu.cs.ai.combinatorics.PowerSet;

/***
 * Implements the three valued semantics by de Finetti for propositional logic.
 * 
 * @author Kai Sauerwald
 *
 * @param <PSym> Type of symbols for the signature
 */
public class DeFinettiSemantics<PSym>
		implements Iterable<Conditional<PropositionalSignature<PSym>, PropositionalFormula<PSym>>> {

	private PropositionalSignature<PSym> signature;
	private PropositionalLogic<PSym> logic = new PropositionalLogic<PSym>();

	public DeFinettiSemantics(PropositionalSignature<PSym> signature) {
		this.signature = signature;
	}

	public PropositionalSignature<PSym> getSignature() {
		return signature;
	}

	/***
	 * Get all Verifying models for a conditional
	 * @author Kai Sauerwald
	 * @param conditional
	 */
	public Set<PropositionalInterpretation<PSym, PropositionalSignature<PSym>>> getVerifications(
			Conditional<PropositionalSignature<PSym>, PropositionalFormula<PSym>> conditional) {
		return logic.modelsOf(conditional.getAntecendence().And(conditional.getConsequence()));
	}

	/***
	 * Get all falsifying models for a conditional
	 * @author Kai Sauerwald
	 * @param conditional
	 */
	public Set<PropositionalInterpretation<PSym, PropositionalSignature<PSym>>> getFalsifications(
			Conditional<PropositionalSignature<PSym>, PropositionalFormula<PSym>> conditional) {
		return logic.modelsOf(conditional.getAntecendence().And(conditional.getConsequence().Neg()));
	}

	/***
	 * Get all not applicable models for a conditional
	 * @author Kai Sauerwald
	 * @param conditional
	 */
	public Set<PropositionalInterpretation<PSym, PropositionalSignature<PSym>>> getNotApplicable(
			Conditional<PropositionalSignature<PSym>, PropositionalFormula<PSym>> conditional) {
		return logic.modelsOf(conditional.getAntecendence().Neg());
	}

	/***
	 * Get all applicable models for a conditional
	 * @author Kai Sauerwald
	 * @param conditional
	 */
	public Set<PropositionalInterpretation<PSym, PropositionalSignature<PSym>>> getApplicable(
			Conditional<PropositionalSignature<PSym>, PropositionalFormula<PSym>> conditional) {
		return logic.modelsOf(conditional.getAntecendence());
	}

	/***
	 * Says where intp verifies the conditional.
	 * 
	 * @author Kai Sauerwald
	 * @param intp        The interpretation
	 * @param conditional The conditional
	 */
	public boolean isVerification(PropositionalInterpretation<PSym, PropositionalSignature<PSym>> intp,
			Conditional<PropositionalSignature<PSym>, PropositionalFormula<PSym>> conditional) {
		return logic.satisfies(intp, conditional.getAntecendence().And(conditional.getConsequence()));
	}

	/***
	 * Says where intp falsifies the conditional.
	 * 
	 * @author Kai Sauerwald
	 * @param intp        The interpretation
	 * @param conditional The conditional
	 */
	public boolean isFalsification(PropositionalInterpretation<PSym, PropositionalSignature<PSym>> intp,
			Conditional<PropositionalSignature<PSym>, PropositionalFormula<PSym>> conditional) {
		return logic.satisfies(intp, conditional.getAntecendence().And(conditional.getConsequence().Neg()));
	}

	/***
	 * Says where intp is not applicable for the conditional.
	 * 
	 * @author Kai Sauerwald
	 * @param intp        The interpretation
	 * @param conditional The conditional
	 */
	public boolean isNotApplicable(PropositionalInterpretation<PSym, PropositionalSignature<PSym>> intp,
			Conditional<PropositionalSignature<PSym>, PropositionalFormula<PSym>> conditional) {
		return logic.satisfies(intp, conditional.getAntecendence().Neg());
	}

	/***
	 * Says where intp is applicable for the conditional.
	 * 
	 * @author Kai Sauerwald
	 * @param intp        The interpretation
	 * @param conditional The conditional
	 */
	public boolean isApplicable(PropositionalInterpretation<PSym, PropositionalSignature<PSym>> intp,
			Conditional<PropositionalSignature<PSym>, PropositionalFormula<PSym>> conditional) {
		return !isNotApplicable(intp, conditional);
	}

	/**
	 * Returns a {@code Stream} of all Conditionals (up to equivalence) under this
	 * signature
	 * 
	 * @author Kai Sauerwald.
	 */
	public Stream<Conditional<PropositionalSignature<PSym>, PropositionalFormula<PSym>>> stream() {
		return StreamSupport.stream(Spliterators.spliterator(iterator(), /* initial size */ 0L, Spliterator.IMMUTABLE),
				false);
	}

	/***
	 * Iterator which presents all different conditionals up to equivalence.
	 */
	@Override
	public Iterator<Conditional<PropositionalSignature<PSym>, PropositionalFormula<PSym>>> iterator() {

		// TODO Auto-generated method stub
		return new Iterator<Conditional<PropositionalSignature<PSym>, PropositionalFormula<PSym>>>() {
			private Iterator<Set<PropositionalInterpretation<PSym, PropositionalSignature<PSym>>>> itrAnt = PowerSet
					.iterator(signature.stream().collect(Collectors.toSet()));
			private Iterator<Set<PropositionalInterpretation<PSym, PropositionalSignature<PSym>>>> itrCons = null;

			private Set<PropositionalInterpretation<PSym, PropositionalSignature<PSym>>> currAnt = null;

			@Override
			public boolean hasNext() {
				if (!itrAnt.hasNext() && itrCons != null && !itrCons.hasNext())
					return false;
				return true;
			}

			@Override
			public Conditional<PropositionalSignature<PSym>, PropositionalFormula<PSym>> next() {
				if (!hasNext())
					throw new NoSuchElementException();

				if (currAnt == null)
					currAnt = itrAnt.next();

				if (itrCons == null)
					itrCons = PowerSet.iterator(signature.stream().collect(Collectors.toSet()));
				else if (!itrCons.hasNext()) {
					currAnt = itrAnt.next();
					itrCons = PowerSet.iterator(signature.stream().collect(Collectors.toSet()));
				}

				return new Conditional<PropositionalSignature<PSym>, PropositionalFormula<PSym>>(
						logic.getCharacterisingFormula(signature, currAnt),
						logic.getCharacterisingFormula(signature, itrCons.next()));
			}
		};
	}

}
