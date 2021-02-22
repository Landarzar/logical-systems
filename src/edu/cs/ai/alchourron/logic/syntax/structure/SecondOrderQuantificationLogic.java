package edu.cs.ai.alchourron.logic.syntax.structure;

import java.util.Set;

import edu.cs.ai.alchourron.logic.Signature;

/***
 * Decribes such logics which have in some way second order variables for
 * predicates
 * 
 * @author Kai Sauerwald
 *
 * @param <P> The type for predicate variables
 */
public interface SecondOrderQuantificationLogic<P, QSO> extends Signature {

	public Set<QSO> getSOQuantifier();
}
