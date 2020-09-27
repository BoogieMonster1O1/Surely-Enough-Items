package io.github.boogiemonster1o1.sei.api;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Represents an operation that accepts two input arguments and returns no
 * result.  This is the three-arity specialization of {@link Consumer},
 * {@link BiConsumer} or {@link TriConsumer}. Unlike most other functional
 * interfaces, {@code QuadConsumer} is expected to operate via side-effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object, Object, Object, Object)}.
 *
 * @param <S> the type of the first argument to the operation
 * @param <T> the type of the second argument to the operation
 * @param <U> the type of the third argument to the operation
 * @param <V> the type of the fourth argument to the operation
 *
 * @see Consumer
 * @see BiConsumer
 * @see TriConsumer
 */
@FunctionalInterface
public interface QuadConsumer<S, T, U, V> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param s the first input argument
     * @param t the second input argument
     * @param u the third input argument
     * @param v the fourth input argument
     */
    void accept(S s, T t, U u, V v);

    /**
     * Returns a composed {@code QuadConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code QuadConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default QuadConsumer<S, T, U, V> andThen(QuadConsumer<? super S, ? super T, ? super U, ? super V> after) {
        Objects.requireNonNull(after);

        return (s, t, u, v) -> {
            this.accept(s, t, u, v);
            after.accept(s, t, u, v);
        };
    }
}

