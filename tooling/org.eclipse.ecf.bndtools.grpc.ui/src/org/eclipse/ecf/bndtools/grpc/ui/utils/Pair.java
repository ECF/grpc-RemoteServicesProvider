package org.eclipse.ecf.bndtools.grpc.ui.utils;

import java.io.Serializable;
import java.util.Objects;

public class Pair<A, B> implements Serializable, Cloneable {

	private static final long	serialVersionUID	= 1L;

	private final A				first;
	private final B				second;

	public Pair(A first, B second) {
		assert first != null && second != null : "both parameters must be non-null";
		this.first = first;
		this.second = second;
	}

	public static <A, B> Pair<A, B> newInstance(A first, B second) {
		return new Pair<>(first, second);
	}

	public A getFirst() {
		return first;
	}

	public B getSecond() {
		return second;
	}

	@Override
	public String toString() {
		return "Pair [" + first + ", " + second + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		Pair<A, B> other = (Pair<A, B>) obj;
		return Objects.equals(first, other.first) && Objects.equals(second, other.second);
	}

	@Override
	public Pair<A, B> clone() {
		return new Pair<>(first, second);
	}
}
