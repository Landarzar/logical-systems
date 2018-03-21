package edu.cs.ai.alchourron.logic.propositional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.LogicalAND;
import edu.cs.ai.alchourron.logic.syntax.LogicalOR;
import edu.cs.ai.alchourron.logic.syntax.Predicate;
import edu.cs.ai.alchourron.logic.syntax.SyntacticElement;
import edu.cs.ai.alchourron.logic.syntax.Term;

/***
 * Represents propositional formula.
 * 
 * @author Kai Sauerwald
 *
 * @param <PSym>
 *            The type of symbols over which the signature is defined
 */
public abstract class PropositionalFormula<PSym> implements Formula<PropositionalSignature<PSym>>, SyntacticElement<PropositionalSignature<PSym>> {
	

	public static class PropositionalAtom<PSym> extends PropositionalFormula<PSym> implements Predicate<PSym, PropositionalSignature<PSym>> {

		protected PSym symbol;
		protected PropositionalSignature<PSym> signature;

		/**
		 * Constructs a new instance of this class
		 * 
		 * @author Kai Sauerwald
		 */
		public PropositionalAtom(PropositionalSignature<PSym> signature, PSym sign) {
			this.symbol = sign;
			this.signature = signature;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.Formula#getSyntaxTree()
		 */
		@Override
		public SyntacticElement<PropositionalSignature<PSym>> getSyntaxTree() throws UnsupportedOperationException {
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.Formula#getSignature()
		 */
		@Override
		public PropositionalSignature<PSym> getSignature() {
			return signature;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#stringify()
		 */
		@Override
		public String stringify() {
			return symbol.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.syntax.Predicate#getSymbol()
		 */
		@Override
		public PSym getSymbol() {
			return symbol;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.syntax.Predicate#getTerms()
		 */
		@Override
		public List<Term<PropositionalSignature<PSym>>> getTerms() {
			return List.of();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.syntax.Predicate#getArity()
		 */
		@Override
		public int getArity() {
			return 0;
		}
	}

	public static class PropositionalAND<PSym> extends PropositionalFormula<PSym> implements LogicalAND<PropositionalSignature<PSym>> {

		protected PropositionalSignature<PSym> signature;
		ArrayList<PropositionalFormula<PSym>> operands;

		/**
		 * Constructs a new instance of this class
		 * 
		 * @author Kai Sauerwald
		 */
		public PropositionalAND(PropositionalSignature<PSym> signature, Collection<PropositionalFormula<PSym>> operands) {
			this.signature = signature;
			this.operands = new ArrayList<>(operands);
		}
		

		/**
		 * Constructs a new instance of this class
		 * 
		 * @author Kai Sauerwald
		 */
		@SafeVarargs
		public PropositionalAND(PropositionalSignature<PSym> signature, PropositionalFormula<PSym>... operands) {
			this.signature = signature;
			this.operands = new ArrayList<>();
			for (PropositionalFormula<PSym> op : operands) {
				this.operands.add(op);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.Formula#getSyntaxTree()
		 */
		@Override
		public SyntacticElement<PropositionalSignature<PSym>> getSyntaxTree() throws UnsupportedOperationException {
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.Formula#getSignature()
		 */
		@Override
		public PropositionalSignature<PSym> getSignature() {
			return signature;
		}

		/*
		 * (non-Javadoc)
		 * @see edu.cs.ai.alchourron.logic.syntax.LogicalOperator#getOperands()
		 */
		@Override
		public List<? extends SyntacticElement<PropositionalSignature<PSym>>> getOperands() {
			return operands;
		}

		/*
		 * (non-Javadoc)
		 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isAtom()
		 */
		@Override
		public boolean isAtom() {
			return false;
		}
	}

	public static class PropositionalOR<PSym> extends PropositionalFormula<PSym> implements LogicalOR<PropositionalSignature<PSym>> {

		protected PropositionalSignature<PSym> signature;
		ArrayList<PropositionalFormula<PSym>> operands;

		/**
		 * Constructs a new instance of this class
		 * 
		 * @author Kai Sauerwald
		 */
		public PropositionalOR(PropositionalSignature<PSym> signature, Collection<PropositionalFormula<PSym>> operands) {
			this.signature = signature;
			this.operands = new ArrayList<>(operands);
		}
		

		/**
		 * Constructs a new instance of this class
		 * 
		 * @author Kai Sauerwald
		 */
		@SafeVarargs
		public PropositionalOR(PropositionalSignature<PSym> signature, PropositionalFormula<PSym>... operands) {
			this.signature = signature;
			this.operands = new ArrayList<>();
			for (PropositionalFormula<PSym> op : operands) {
				this.operands.add(op);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.Formula#getSyntaxTree()
		 */
		@Override
		public SyntacticElement<PropositionalSignature<PSym>> getSyntaxTree() throws UnsupportedOperationException {
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.Formula#getSignature()
		 */
		@Override
		public PropositionalSignature<PSym> getSignature() {
			return signature;
		}

		/*
		 * (non-Javadoc)
		 * @see edu.cs.ai.alchourron.logic.syntax.LogicalOperator#getOperands()
		 */
		@Override
		public List<? extends SyntacticElement<PropositionalSignature<PSym>>> getOperands() {
			return operands;
		}

		/*
		 * (non-Javadoc)
		 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isAtom()
		 */
		@Override
		public boolean isAtom() {
			return false;
		}
	}

	public static class PropositionalNEG<PSym> extends PropositionalFormula<PSym> implements LogicalAND<PropositionalSignature<PSym>> {

		protected PropositionalSignature<PSym> signature;
		protected  PropositionalFormula<PSym> operand;

		/**
		 * Constructs a new instance of this class
		 * 
		 * @author Kai Sauerwald
		 */
		public PropositionalNEG(PropositionalSignature<PSym> signature, PropositionalFormula<PSym> operand) {
			this.signature = signature;
			this.operand = operand;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.Formula#getSyntaxTree()
		 */
		@Override
		public SyntacticElement<PropositionalSignature<PSym>> getSyntaxTree() throws UnsupportedOperationException {
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.Formula#getSignature()
		 */
		@Override
		public PropositionalSignature<PSym> getSignature() {
			return signature;
		}

		/*
		 * (non-Javadoc)
		 * @see edu.cs.ai.alchourron.logic.syntax.LogicalOperator#getOperands()
		 */
		@Override
		public List<? extends SyntacticElement<PropositionalSignature<PSym>>> getOperands() {
			return List.of(operand);
		}

		/*
		 * (non-Javadoc)
		 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isAtom()
		 */
		@Override
		public boolean isAtom() {
			return false;
		}
	}

}