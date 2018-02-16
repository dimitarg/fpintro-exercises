package org.example.chapter4.homework;

import fj.data.List;
import fj.data.Validation;

import java.util.Scanner;

import static org.example.chapter4.homework.Validator.*;

public class Demo
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        Validation<List<Fail>, Person> first = unsafeReadPerson(sc);
        Validation<List<Fail>, Person> second = unsafeReadPerson(sc);

        Validation<List<Fail>, Match<Person, Person>> match = PersonValidator.match(first, second);
        System.out.println(match);
    }

    private static Validation<List<Fail>, Person> unsafeReadPerson(Scanner sc)
    {
        Validation<Fail, Integer> idV = parseInt(getNext("ID: ", sc));

        return idV.accumulate(
                isValidString(getNext("Name: ", sc)),
                parseInt(getNext("Age: ", sc)).bind(a -> Validator.isPositive(a)),
                parseGender(getNext("Gender /m, f, other/: ", sc)),
                (id, name, age, gender) -> new Person(id, name, age, gender));
    }

    private static String getNext(String message, Scanner sc) {
        System.out.println(message);
        return sc.nextLine();
    }
}
