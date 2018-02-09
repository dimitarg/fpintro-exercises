package org.example.chapter3;

import java.util.Objects;
import java.util.function.BiFunction;
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
        return foldLeft((x, y) -> x + 1, 0);
    }

    public ConsList<A> append(A a)
    {
        return foldRight((x, y) -> cons(x, y), cons(a, empty()));
    }

    public ConsList<A> reverse()
    {
        return foldLeft((x, y) -> cons(y, x), empty());
    }

    public ConsList<A> filter(Predicate<A> predicate)
    {
        return foldRight((current, result) -> predicate.test(current) ? cons(current, result) : result, empty());
    }

    public <B> ConsList<B> map(Function<A, B> function)
    {
        return foldRight((current, result) -> cons(function.apply(current), result), empty());
    }

    public boolean forAll(Predicate<A> predicate)
    {
        return foldRight((current, result) -> result && predicate.test(current), true);
    }

    public boolean exists(Predicate<A> predicate)
    {
        return foldRight((current, result) -> result || predicate.test(current), false);
    }

    public <B> B foldRight2(BiFunction<A, B, B> f, B z)
    {
        return this.reverse().foldLeft((result, current) -> f.apply(current, result), z);
    }

    public static <A> ConsList<A> fromArray(A... array)
    {
        ConsList<A> consList = empty();
        for (int count = array.length - 1; count >= 0; count--)
        {
            consList = cons(array[count], consList);
        }

        return consList;
    }

    public <B> B foldLeft(BiFunction<B, A, B> f, B z)
    {
        B res = z;
        ConsList<A> xs = this;
        while (!xs.isEmpty())
        {
            res = f.apply(res, xs.head());
            xs = xs.tail();
        }

        return res;
    }

    public <B> B foldRight(BiFunction<A, B, B> f, B z)
    {
        return isEmpty() ? z : f.apply(head(), tail().foldRight(f, z));

    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (obj instanceof ConsList<?>)
        {
            ConsList<A> other = (ConsList<A>) obj;
            if (other.isEmpty() && isEmpty())
            {
                return true;
            }

            if (other.isEmpty() || isEmpty())
            {
                return false;
            }

            if (other.head().equals(head()))
            {
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

        @Override
        public String toString()
        {
            return "Cons{" + "head=" + head + ", tail=" + tail + '}';
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

        @Override
        public String toString()
        {
            return "Nil()";
        }
    }
}


