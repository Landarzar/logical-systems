package cs.ai.logic.propositional;

import java.util.Set;

import cs.ai.logic.Formula;
import cs.ai.logic.LogicalSystem;
import cs.ai.logic.syntax.LogicalAND;
import cs.ai.logic.syntax.SyntacticElement;

/***
 * Logical System representing propositional logic over symbol space of type
 * {@link <V>}
 * 
 * @author Kai Sauerwald
 *
 * @param <V>
 *            The symbol space over which the signature are definable
 * @param <F>
 *            The type for formula
 */
public class PropositionaLogic<V, F extends Formula<PropositionalSignature<V>>> implements
		LogicalSystem<Boolean, PropositionalSignature<V>, F, PropositionalInterpretation<V, PropositionalSignature<V>>> {

	/*
	 * (non-Javadoc)
	 * @see cs.ai.logic.LogicalSystem#validFormula(cs.ai.logic.Formula)
	 */
	@Override
	public boolean validFormula(F formula) {
		SyntacticElement<PropositionalSignature<V>> syntacton = formula.getSyntax();
		return validSyntaxTree(syntacton);
	}
	
	private boolean validSyntaxTree(SyntacticElement<PropositionalSignature<V>> syntacton) {
		if (syntacton instanceof LogicalAND<?>) {
			LogicalAND<PropositionalSignature<V>> new_name = (LogicalAND<PropositionalSignature<V>>) syntacton;
			
		}
		
		return false;
	}

	@Override
	public Set<PropositionalInterpretation<V, PropositionalSignature<V>>> models(F formula) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean eval(PropositionalInterpretation<V, PropositionalSignature<V>> interpretation, F formula) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean entails(PropositionalInterpretation<V, PropositionalSignature<V>> interpretation, F formula) {
		// TODO Auto-generated method stub
		return false;
	}

}
