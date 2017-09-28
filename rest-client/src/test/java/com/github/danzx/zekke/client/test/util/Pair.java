package com.github.danzx.zekke.client.test.util;

import java.util.Objects;

public class Pair<T0, T1> {

    public final T0 _0;
    public final T1 _1;

    public static <T0, T1> Pair<T0, T1> pairOf(T0 t0, T1 t1) {
        return new Pair<>(t0, t1);
    }

    public Pair(T0 t0, T1 t1) {
        _0 = t0;
        _1 = t1;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;
        return isPairEqualTo((Pair<?, ?>) other);
    }

    protected boolean isPairEqualTo(Pair<?, ?> other) {
        return Objects.equals(_0, other._0) &&
                Objects.equals(_1, other._1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_0, _1);
    }

    @Override
    public String toString() {
        return "<" + _0 +  ", " + _1 +  '>';
    }
}
