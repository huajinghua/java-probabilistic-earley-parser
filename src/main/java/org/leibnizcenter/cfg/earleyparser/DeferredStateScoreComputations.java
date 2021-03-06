package org.leibnizcenter.cfg.earleyparser;

import org.leibnizcenter.cfg.algebra.semiring.dbl.ExpressionSemiring;
import org.leibnizcenter.cfg.algebra.semiring.dbl.Resolvable;
import org.leibnizcenter.cfg.earleyparser.chart.state.State;
import org.leibnizcenter.cfg.grammar.AtomFactory;
import org.leibnizcenter.cfg.grammar.Grammar;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class DeferredStateScoreComputations {
    public final Map<State, ExpressionWrapper> states;
    private final ExpressionSemiring semiring;
    private AtomFactory atoms;

    public DeferredStateScoreComputations(Grammar grammar) {
        this.states = new HashMap<>();
        this.semiring = grammar.semiring;
        this.atoms = grammar.atoms;
    }

    public ExpressionWrapper getOrCreate(State state,
                                         double default_) {
        if (this.states.containsKey(state)) {
            return this.states.get(state);
        } else {
            final ExpressionWrapper expressionWrapper = new ExpressionWrapper(default_);
            this.states.put(state, expressionWrapper);
            return expressionWrapper;
        }
    }

    public void plus(State s, Resolvable addValue) {
        ExpressionWrapper current = this.getOrCreate(
                s,
                this.semiring.zero()
        );

        current.setExpression(
                current.hasExpression()
                        ? semiring.plus(addValue, current.getExpression())
                        : semiring.plus(addValue, current.getLiteral())
        );
        this.states.put(s, current);
    }
}
