package org.leibnizcenter.cfg.rule;

import org.leibnizcenter.cfg.algebra.semiring.dbl.DblSemiring;
import org.leibnizcenter.cfg.algebra.semiring.dbl.LogSemiring;
import org.leibnizcenter.cfg.category.Category;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;

/**
 * Factory for {@link Rule rules}
 *
 * Created by Maarten on 31-7-2016.
 */
public class RuleFactory {
    private final DblSemiring semiring;

    public RuleFactory(DblSemiring semiring) {
        this.semiring = semiring;
    }

    @SuppressWarnings("unused")
    public RuleFactory() {
        this(LogSemiring.get());
    }

    /**
     * Instantiates a new rule with a probability score of one (whatever that means for the given semiring)
     */
    public Rule newRule(NonTerminal LHS, Category... RHS) {
        return newRuleWithRawProbability(semiring.one(), LHS, RHS);
    }

    /**
     * Instantiates a new rule with given probability <strong>as a probability between 0 and 1</strong>. The
     * semiring will take care in converting the number.
     */
    public Rule newRule(double probability, NonTerminal LHS, Category... RHS) {
        return newRuleWithRawProbability(semiring.fromProbability(probability), LHS, RHS);
    }

    public SynchronizingRule newErrorRule(double probability, NonTerminal LHS, Category... RHS) {
        return newErrorRuleWithRawProbability(semiring.fromProbability(probability), LHS, RHS);
    }

    private SynchronizingRule newErrorRuleWithRawProbability(double probability, NonTerminal lhs, Category[] rhs) {
        return new SynchronizingRule(probability, lhs, rhs);
    }

    /**
     * Instantiates a new rule with given probability <strong>as a probability between 0 and 1</strong>. The
     * semiring will take care in converting the number.
     */
    private Rule newRuleWithRawProbability(double probability, NonTerminal LHS, Category... RHS) {
        return new Rule(probability, LHS, RHS);
    }
}
