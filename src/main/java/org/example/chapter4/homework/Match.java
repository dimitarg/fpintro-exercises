package org.example.chapter4.homework;

public class Match<T, T1>
{
    public final T left;
    public final T1 right;

    public Match(T left, T1 right)
    {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString()
    {
        return "Match{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
