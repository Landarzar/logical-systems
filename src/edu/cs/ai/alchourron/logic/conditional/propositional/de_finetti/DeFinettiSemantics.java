package edu.cs.ai.alchourron.logic.conditional.propositional.de_finetti;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import edu.cs.ai.alchourron.logic.conditional.Conditional;
import edu.cs.ai.alchourron.logic.propositional.PropositionalFormula;
import edu.cs.ai.alchourron.logic.propositional.PropositionalInterpretation;
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

	public DeFinettiSemantics(PropositionalSignature<PSym> signature) {
		this.signature = signature;
	}

	public PropositionalSignature<PSym> getSignature() {
		return signature;
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

				if (itrCons == null)
					itrCons = PowerSet.iterator(signature.stream().collect(Collectors.toSet()));

				return new Conditional<PropositionalSignature<PSym>, PropositionalFormula<PSym>>(
						signature.getCharacterisingFormula(itrAnt.next()),
						signature.getCharacterisingFormula(itrCons.next()));
			}
		};
	}

}
