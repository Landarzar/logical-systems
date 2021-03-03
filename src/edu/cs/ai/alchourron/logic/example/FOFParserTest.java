package edu.cs.ai.alchourron.logic.example;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.logics.predicatelogics.FOSignature;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaAND;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaOR;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaPredicate;
import edu.cs.ai.alchourron.logic.syntax.terms.FunctionTerm;
import edu.cs.ai.alchourron.logic.syntax.terms.Term;
import edu.cs.ai.parser.MappingConfig;
import edu.cs.ai.parser.ParserBridge;

import java.util.List;

class MappingConfigTest implements MappingConfig<
        Formula<FOSignature<String, String, String>>,
        Term<FOSignature<String, String, String>>
> {
    public Term<FOSignature<String, String, String>> atomicTerm(String name, List<Term<FOSignature<String, String, String>>> arguments) {
        return new FunctionTerm<>(name, arguments.size(), arguments);
    }

    public Formula<FOSignature<String, String, String>> equality(Term<FOSignature<String, String, String>> left, Term<FOSignature<String, String, String>> right) {
        return new FormulaPredicate<>("=", List.of(left, right));
    }

    public Formula<FOSignature<String, String, String>> inequality(Term<FOSignature<String, String, String>> left, Term<FOSignature<String, String, String>> right) {
        return new FormulaPredicate<>("!=", List.of(left, right));
    }

    public Formula<FOSignature<String, String, String>> AND(Formula<FOSignature<String, String, String>> left, Formula<FOSignature<String, String, String>> right) {
        return new FormulaAND<>(List.of(left, right));
    }

    public Formula<FOSignature<String, String, String>> OR(Formula<FOSignature<String, String, String>> left, Formula<FOSignature<String, String, String>> right) {
        return new FormulaOR<>(List.of(left, right));
    }
}

public class FOFParserTest {
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        MappingConfigTest config = new MappingConfigTest();

        ParserBridge<Formula<FOSignature<String, String, String>>,
                        Term<FOSignature<String, String, String>>> parserBridge = new ParserBridge<>(config);

        List<Formula<FOSignature<String, String, String>>> formulas = parserBridge.parseFile("./src/edu/cs/ai/alchourron/logic/example/ALG021+1.p");

        System.out.println(formulas);
    }
}
