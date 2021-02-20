package edu.cs.ai.alchourron.logic.syntax.structure;

import java.util.List;

/**
 * Describes logics, where there propositions as elements of the language
 * 
 * @author Kai Sauerwald
 *
 * @param <P> The objects which propositionals are represented by.
 */
public interface PropositionLogicSignature<P> extends AtomLogicSignature {
	
	/***
	 * Provides a set of Propositions of that signature.
	 * @author Kai Sauerwald
	 */
	public List<P> getPropositions();

}
