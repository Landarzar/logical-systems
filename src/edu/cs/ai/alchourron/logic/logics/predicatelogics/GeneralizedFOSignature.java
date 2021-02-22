package edu.cs.ai.alchourron.logic.logics.predicatelogics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import edu.cs.ai.alchourron.logic.Signature;
import edu.cs.ai.alchourron.logic.syntax.formula.ClassicalQuantifier;
import edu.cs.ai.alchourron.logic.syntax.structure.BiImplicationLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.FalsumLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.FunctionTermLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.ImplicationLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.QuantificationLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.PredicateLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.ClassicalConnectivesLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.VariableTermLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.VerumLogicSignature;

/***
 * Represents a first order like signature
 * 
 * @author Kai Sauerwald
 *
 * @param <R> Relation Symbols
 * @param <K> Function Symbols
 * @param <V> Variable Type
 */
public class GeneralizedFOSignature<R, K, V, Q>
		implements Signature, VerumLogicSignature, FalsumLogicSignature, ClassicalConnectivesLogicSignature,
		PredicateLogicSignature<R>, FunctionTermLogicSignature<K>, VariableTermLogicSignature<V>,
		QuantificationLogicSignature<Q, V>, ImplicationLogicSignature, BiImplicationLogicSignature {

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

	private Set<Q> quantifieres;

	/***
	 * Constructs a new signature
	 * 
	 * @author Kai Sauerwald
	 * @param predicateSymbols The symbols of this signature
	 */
	public GeneralizedFOSignature(Set<Q> quantifieres, R[] symbR, Integer[] arityR, K[] symbF, Integer[] arityF) {
		if (symbR.length != arityR.length)
			throw new IllegalArgumentException("symbR.length != arityR.length");
		if (symbF.length != arityF.length)
			throw new IllegalArgumentException("symbR.length != arityR.length");
		
		this.quantifieres = quantifieres;

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
	public GeneralizedFOSignature(Set<Q> quantifieres, List<R> symbols, List<Integer> arity) {
		this.predicateSymbols = new ArrayList<>(symbols);
		this.quantifieres = quantifieres;
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
	public Set<K> getFunctionSymbols() {
		return Collections.unmodifiableSet(new HashSet<>(functionSymbols));
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

	@Override
	public Set<Q> getQuantifier() {
		return this.quantifieres;
	}

	@Override
	public int hashCode() {
		return Objects.hash(functionArity, functionSymbols, predicateArity, predicateSymbols, quantifieres);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof GeneralizedFOSignature))
			return false;
		GeneralizedFOSignature other = (GeneralizedFOSignature) obj;
		return Objects.equals(functionArity, other.functionArity)
				&& Objects.equals(functionSymbols, other.functionSymbols)
				&& Objects.equals(predicateArity, other.predicateArity)
				&& Objects.equals(predicateSymbols, other.predicateSymbols)
				&& Objects.equals(quantifieres, other.quantifieres);
	}
}
