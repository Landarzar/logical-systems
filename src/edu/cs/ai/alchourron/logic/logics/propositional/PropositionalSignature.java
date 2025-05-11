package edu.cs.ai.alchourron.logic.logics.propositional;

import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.Signature;
import edu.cs.ai.alchourron.logic.semantics.interpretations.PropositionalInterpretation;
import edu.cs.ai.alchourron.logic.semantics.interpretations.PropositionalTeam;
import edu.cs.ai.alchourron.logic.syntax.formula.*;
import edu.cs.ai.alchourron.logic.syntax.structure.*;
import edu.cs.ai.math.combinatorics.PowerSet;

/***
 * Represents a propositional signature, which uses Elements of the type Psym as
 * symbol supply.
 * 
 * @author Kai Sauerwald
 *
 * @param <P> The type of the symbols
 */
public class PropositionalSignature<P> implements Signature, VerumLogicSignature, FalsumLogicSignature,
		ClassicalConnectivesLogicSignature, PropositionLogicSignature<P>, ImplicationLogicSignature,
		BiImplicationLogicSignature, Iterable<PropositionalInterpretation<P>> {
	
	public static <P> Set<P> getAtoms(Formula<PropositionalSignature<P>> formula){
		if (formula instanceof FormulaPropositionalAtom<?,?>) {
			FormulaPropositionalAtom<P,PropositionalSignature<P>> f = (FormulaPropositionalAtom<P,PropositionalSignature<P>>) formula;
			return Set.of(f.getSymbol());
		}
		else if (formula instanceof LogicalOperator<?>) {
			LogicalOperator<PropositionalSignature<P>> f = (LogicalOperator<PropositionalSignature<P>>) formula;
			Set<P> set = new CopyOnWriteArraySet<P>();
			f.getOperands().forEach(o -> set.addAll(getAtoms(o)));
			return set;
		}
		else if (formula instanceof FormulaFalsum<?>) {
			return Set.of();
		}
		else if (formula instanceof FormulaVerum<?>) {
			return Set.of();
		}

		// This happens if none of the special cases matches
		throw new InvalidParameterException("The given formula object is not a valid propositional formula: "+ formula);
	}

	/***
	 * The symbols of this signature
	 * 
	 * @author Kai Sauerwald
	 */
	ArrayList<P> symbols;

	/***
	 * Constructs a new propositional signature
	 * 
	 * @author Kai Sauerwald
	 * @param symbols The symbols of this signature
	 */
	public PropositionalSignature(P... symbols) {
		this.symbols = new ArrayList<>();
		for (int i = 0; i < symbols.length; i++) {
			P v = symbols[i];
			this.symbols.add(v);
		}
	}
	

	/**
	 * Creates an iterator, that iterates over all interpretations under this
	 * signature.
	 * 
	 * @author Kai Sauerwald
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<PropositionalTeam<P>> iteratorTeams() {
		return  PowerSet.stream(StreamSupport.stream(this.spliterator(), false)
				    .collect(Collectors.toUnmodifiableSet())).map(s -> new PropositionalTeam<>(this, s) ).iterator();
	}

	/***
	 * Constructs a new propositional signature
	 * 
	 * @author Kai Sauerwald
	 * @param symbols The symbols of this signature
	 */
	public PropositionalSignature(Collection<P> symbols) {
		this.symbols = new ArrayList<>(symbols);
	}
	
	/***
	 * Constructs a new propositional signature
	 * 
	 * @author Kai Sauerwald
	 * @param symbols The symbols of this signature
	 */
	public PropositionalSignature(Formula<PropositionalSignature<P>> formula) {
		this(getAtoms(formula));
	}

//	/***
//	 * Returns the set of the symbols of this signature
//	 * 
//	 * @author Kai Sauerwald
//	 */
//	public List<P> getSymbols() {
//		return Collections.unmodifiableList(symbols);
//	}
	
	/***
	 * Returns the set of the symbols of this signature
	 * 
	 * @author Kai Sauerwald
	 */
	@Override
	public Set<P> getPropositions() {
		return Collections.unmodifiableSet(new HashSet<>(symbols));
	}

	/**
	 * Returns a {@code Stream} of all interpretations under this signature
	 * 
	 * @author Kai Sauerwald.
	 */
	public Stream<PropositionalInterpretation<P>> stream() {
		return StreamSupport.stream(Spliterators.spliterator(iterator(), /* initial size */ 0L, Spliterator.IMMUTABLE),
				false);
	}

	/**
	 * Creates an iterator, that iterates over all interpretations under this
	 * signature.
	 * 
	 * @author Kai Sauerwald
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<PropositionalInterpretation<P>> iterator() {
		String cmp = "";
		for (int i = 0; i < symbols.size(); i++) {
			cmp += "1";
		}
		final String cmp2 = cmp;

		PropositionalSignature<P> sig = this;
		return new Iterator<PropositionalInterpretation<P>>() {

			BigInteger bigint = BigInteger.ZERO;
			String max = cmp2;

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Iterator#hasNext()
			 */
			@Override
			public boolean hasNext() {
				return bigint.compareTo(new BigInteger(max, 2)) <= 0;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Iterator#next()
			 */
			@Override
			public PropositionalInterpretation<P> next() {
				LinkedList<P> temp = new LinkedList<>();
				for (int i = 0; i < symbols.size(); i++) {
					if (bigint.testBit(i))
						temp.add(symbols.get(i));
				}

				bigint = bigint.add(BigInteger.ONE);
				return new PropositionalInterpretation<P>(sig, temp);
			}

		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return symbols.hashCode();
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
		if (!(o instanceof PropositionalSignature))
			return false;

		PropositionalSignature<P> sig = (PropositionalSignature<P>) o;

		return this.symbols.equals(sig.symbols);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return symbols.toString();
	}

	@Override
	public String toLaTeX() {
		return symbols.toString();
	}
}
