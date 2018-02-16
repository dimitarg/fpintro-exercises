package org.example.chapter4.homework;

import fj.F;
import fj.data.Either;
import fj.data.List;
import fj.data.Validation;

public class PersonValidator
{
    public static Validation<List<Fail>, Match<Person, Person>> match(Validation<List<Fail>, Person> first, Validation<List<Fail>, Person> second)
    {
        Validation<List<List<Fail>>, Match<Person, Person>> accumulated = first.accumulate(second, (p1, p2) -> new Match(p1, p2));

        Validation<List<Fail>, Match<Person, Person>> pair = leftMap(accumulated, e -> List.join(e));

        return pair.bind(p -> areOldEnough(p).accumulate(areMaleAndFemale(p), (p1, p2) -> p1));
    }

    private static Validation<Fail, Match<Person, Person>> areOldEnough(Match<Person, Person> pair)
    {
        return (pair.left.age < 21 || pair.right.age < 21) ? Validation.fail(Fail.UNDERAGE) : Validation.success(pair);
    }

    private static Validation<Fail, Match<Person, Person>> areMaleAndFemale(Match<Person, Person> pair)
    {
        if ((pair.left.gender == Gender.F && pair.right.gender == Gender.M)
                || (pair.left.gender == Gender.M && pair.right.gender == Gender.F))
        {

            return Validation.success(pair);
        }

        return Validation.fail(Fail.NON_HETERO_PAIR);
    }

    private static <E, A, E1> Validation<E1, A> leftMap(Validation<E, A> v, F<E, E1> f) {
        Either<E, A> either = v.toEither();
        Either<E1, A> mapped = either.left().map(e -> f.f(e));

        return Validation.validation(mapped);
    }
}
