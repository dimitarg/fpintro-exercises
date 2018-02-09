package org.example.chapter2;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;


import static org.example.Util.error;
import static org.example.Util.notImplemented;

public abstract class ConsList<A>
{

    public static <A> ConsList<A> empty()
    {
        return new Nil<>();
    }

    public static <A> ConsList<A> cons(A head, ConsList<A> tail)
    {
        return new Cons<>(head, tail);
    }

    public abstract boolean isEmpty();

    public abstract A head();

    public abstract ConsList<A> tail();

    public int length()
    {
        return isEmpty() ? 0 : 1 + tail().length();
    }

    public ConsList<A> append(A element)
    {
        return isEmpty() ? ConsList.cons(element, empty()) : ConsList.cons(head(), tail().append(element));

    }

    public ConsList<A> reverse()
    {
        return isEmpty() ? this : tail().reverse().append(head());
    }

    public ConsList<A> takeWhile(Function<A, Boolean> predicate)
    {
        if (isEmpty())
        {
            return this;
        }

        if (predicate.apply(head()))
        {
            return ConsList.cons(head(), tail().takeWhile(predicate));
        }

        return ConsList.empty();
    }

    public ConsList<A> dropWhile(Function<A, Boolean> predicate)
    {
        if (isEmpty())
        {
            return this;
        }

        if (predicate.apply(head()))
        {
            return tail().dropWhile(predicate);
        }

        return this;
    }

    public ConsList<A> filter(Function<A, Boolean> predicate)
    {
        if (isEmpty())
        {
            return this;
        }

        if (predicate.apply(head()))
        {
            return ConsList.cons(head(), tail().filter(predicate));
        }

        return tail().filter(predicate);
    }

    public <B> ConsList<B> map(Function<A, B> function)
    {
        return isEmpty() ? ConsList.empty() : ConsList.cons(function.apply(head()), tail().map(function));
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }

        if (obj instanceof ConsList<?>)
        {
            ConsList<A> other = (ConsList<A>) obj;
            if (other.isEmpty() && isEmpty()) {
                return true;
            }

            if (other.isEmpty() || isEmpty()) {
                return false;
            }

            if (other.head().equals(head())) {
                return tail().equals(other.tail());
            }
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return isEmpty() ? 0 : Objects.hash(head(), tail());
    }

    private static final class Cons<A> extends ConsList<A>
    {
        public final A head;
        public final ConsList<A> tail;

        public Cons(A head, ConsList<A> tail)
        {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public boolean isEmpty()
        {
            return false;
        }

        @Override
        public A head()
        {
            return head;
        }

        @Override
        public ConsList<A> tail()
        {
            return tail;
        }
    }


    private static final class Nil<A> extends ConsList<A>
    {

        public Nil()
        {
        }

        @Override
        public boolean isEmpty()
        {
            return true;
        }

        @Override
        public A head()
        {
            throw error("head() on Nil");
        }

        @Override
        public ConsList<A> tail()
        {
            throw error("tail() on Nil");
        }
    }
}


