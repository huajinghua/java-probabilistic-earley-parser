package org.leibnizcenter.cfg.earleyparser;

import org.leibnizcenter.cfg.algebra.semiring.dbl.ExpressionSemiring;
import org.leibnizcenter.cfg.algebra.semiring.dbl.Resolvable;
import org.leibnizcenter.cfg.earleyparser.chart.state.State;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class DeferredStateScoreComputations {
    public final Map<State, ExpressionWrapper> states;
    private final ExpressionSemiring semiring;

    public DeferredStateScoreComputations(ExpressionSemiring semiring) {
        this.states = new HashMap<>();
        this.semiring = semiring;
    }

    public ExpressionWrapper getOrCreate(State state,
                                         Resolvable default_) {
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
                this.semiring.ZERO_EXPRESSION
        );
        current.setExpression(semiring.plus(addValue, current.getExpression()));
        this.states.put(s, current);
    }
}
