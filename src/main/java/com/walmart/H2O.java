package com.walmart;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;

public class H2O {

    private enum State { EMPTY, O, H, H2, HO, FULL}
    private final AtomicReference<State> state = new AtomicReference<>(State.EMPTY);

    public void hydrogen(Runnable releaseHydrogen) {
        doTransition(releaseHydrogen, H2O::hTransition);
    }

    public void oxygen(Runnable releaseOxygen) {
        doTransition(releaseOxygen, H2O::oTransition);
    }

    private static State hTransition(State prevState) {
        switch (prevState) {
            case EMPTY: return State.H;
            case O: return State.HO;
            case H: return State.H2;
            case HO: return State.FULL;
            default: return prevState;
        }
    }

    private static State oTransition(State prevState) {
        switch (prevState) {
            case EMPTY: return State.O;
            case H: return State.HO;
            case H2: return State.FULL;
            default: return prevState;
        }
    }

    private void doTransition(Runnable release, UnaryOperator<State> transition) {
        while (true) {
            State curr = state.get();
            State next = transition.apply(curr);

            if (curr == next) {
                // no state update, just chill..
                Thread.yield();
                continue;
            }

            if (state.compareAndSet(curr, next)) {
                release.run();
                if (next == State.FULL) {
                    // the "last of the pack" thread that brings state to FULL will execute this
                    state.set(State.EMPTY);
                }
                return;
            }
        }
    }
}
