package io.github.boogiemonster1o1.sei.api;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Represents an operation that accepts five input arguments and returns no
 * result. This is the five-arity specialization of {@link Consumer},
 * {@link BiConsumer}, {@link TriConsumer} or {@link TetraConsumer}.
 * Unlike most other functional interfaces,
 * {@code PentaConsumer} is expected to operate via side-effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object, Object, Object, Object, Object)}.
 *
 * @param <R> the type of the first argument to the operation
 * @param <S> the type of the second argument to the operation
 * @param <T> the type of the third argument to the operation
 * @param <U> the type of the fourth argument to the operation
 * @param <V> the type of the fifth argument to the operation
 *
 * @see Consumer
 * @see BiConsumer
 * @see TriConsumer
 * @see TetraConsumer
 */
@FunctionalInterface
public interface PentaConsumer<R, S, T, U, V> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param r the first input argument
     * @param s the second input argument
     * @param t the third input argument
     * @param u the fourth input argument
     * @param v the fifth input argument
     */
    void accept(R r, S s, T t, U u, V v);

    /**
     * Returns a composed {@code PentaConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code PentaConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default PentaConsumer<R, S, T, U, V> andThen(PentaConsumer<? super R, ? super S, ? super T, ? super U, ? super V> after) {
        Objects.requireNonNull(after);

        return (r, s, t, u, v) -> {
            this.accept(r, s, t, u, v);
            after.accept(r, s, t, u, v);
        };
    }
}

