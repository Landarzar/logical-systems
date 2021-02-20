package edu.cs.ai.alchourron.logic.logics.predicatelogics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.cs.ai.alchourron.logic.Signature;
import edu.cs.ai.alchourron.logic.syntax.formula.ClassicalQuantifier;
import edu.cs.ai.alchourron.logic.syntax.structure.BiImplicationLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.FalsumLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.ImplicationLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.QuantificationLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.PredicateLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.ClassicalConnectivesLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.VariableTermLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.VerumLogicSignature;

/***
 * Represents a propositional signature, which uses Elements of the type Psym as
 * symbol supply.
 * 
 * @author Kai Sauerwald
 *
 * @param <R> Relation Symbols
 * @param <K> Function Symbols
 */
public class FOSignature<R extends Enum<R>, K extends Enum<K>, V>
		implements Signature, VerumLogicSignature, FalsumLogicSignature, ClassicalConnectivesLogicSignature,
		PredicateLogicSignature<R, K>, VariableTermLogicSignature<V>,
		QuantificationLogicSignature<ClassicalQuantifier, V>, ImplicationLogicSignature, BiImplicationLogicSignature {

	/***
	 * The symbols of the predications
	 * 
	 * @author Kai Sauerwald
	 */
	ArrayList<R> predicateSymbols;

	/***
	 * The arity of the predicates
	 * 
	 * @author Kai Sauerwald
	 */
	ArrayList<Integer> predicateArity;

	/***
	 * The symbols of the functions
	 * 
	 * @author Kai Sauerwald
	 */
	ArrayList<K> functionSymbols;

	/***
	 * The arity of the functions
	 * 
	 * @author Kai Sauerwald
	 */
	ArrayList<Integer> functionArity;

	/***
	 * Constructs a new signature
	 * 
	 * @author Kai Sauerwald
	 * @param predicateSymbols The symbols of this signature
	 */
	public FOSignature(R[] symbR, Integer[] arityR, K[] symbF, Integer[] arityF) {
		if (symbR.length != arityR.length)
			throw new IllegalArgumentException("symbR.length != arityR.length");
		if (symbF.length != arityF.length)
			throw new IllegalArgumentException("symbR.length != arityR.length");

		this.predicateSymbols = new ArrayList<>(symbR.length);
		this.predicateArity = new ArrayList<>(arityR.length);
		for (int i = 0; i < symbR.length; i++) {
			R v = symbR[i];
			this.predicateSymbols.add(v);
		}

		this.functionSymbols = new ArrayList<>(symbF.length);
		this.functionArity = new ArrayList<>(arityF.length);
		for (int i = 0; i < symbF.length; i++) {
			K v = symbF[i];
			this.functionSymbols.add(v);
		}
	}

	/***
	 * Constructs a new signature
	 * 
	 * @author Kai Sauerwald
	 * @param symbols The symbols of predicates
	 * @param arity   The arity of predicates
	 */
	public FOSignature(List<R> symbols, List<Integer> arity) {
		this.predicateSymbols = new ArrayList<>(symbols);
	}
	
	@Override
	public List<R> getPredicateSymbols() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableList(predicateSymbols);
	}
	
	@Override
	public int getPredicateArity(R predicatesymbol) {
		int idx = predicateSymbols.indexOf(predicatesymbol);
		if(idx == -1) 
			throw new IllegalArgumentException("" + predicatesymbol + " is not part of this signature");
		return predicateArity.get(idx);
	}
	
	@Override
	public List<K> getFunctionSymbols() {
		return Collections.unmodifiableList(functionSymbols);
	}
	
	@Override
	public int getFunctionArity(K functionsymbol) {
		int idx = functionSymbols.indexOf(functionsymbol);
		if(idx == -1) 
			throw new IllegalArgumentException("" + functionsymbol + " is not part of this signature");
		return functionArity.get(idx);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return predicateSymbols.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof FOSignature))
			return false;

		FOSignature<R, K, V> sig = (FOSignature<R, K, V>) o;

		return this.predicateSymbols.equals(sig.predicateSymbols);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "<" + predicateSymbols.toString() + "," + functionSymbols + ">";
	}

	@Override
	public String toLaTeX() {
		return predicateSymbols.toString();
	}
}
