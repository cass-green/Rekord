package com.noodlesandwich.rekord.testobjects;

import java.util.Collection;
import com.noodlesandwich.rekord.Key;
import com.noodlesandwich.rekord.Rekord;
import com.noodlesandwich.rekord.keys.SimpleKey;

import static com.noodlesandwich.rekord.extra.Validation.validatesItsInput;
import static com.noodlesandwich.rekord.transformers.Transformers.defaultsTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public final class Rekords {
    private Rekords() { }

    public static interface Sandvich {
        Key<Sandvich, Filling> filling = SimpleKey.named("filling");
        Key<Sandvich, Bread> bread = SimpleKey.named("bread");
        Key<Sandvich, Style> style = SimpleKey.<Sandvich, Style>named("style").that(defaultsTo(Style.Flat));

        Rekord<Sandvich> rekord = Rekord.of(Sandvich.class).accepting(filling, bread, style);

        public static enum Filling {
            Cheese,
            Ham,
            Jam,
            Lettuce
        }

        public static enum Bread {
            Brown,
            White
        }

        public static enum Style {
            Flat,
            Burger,
            Roll
        }
    }

    public static interface Wurst {
        Key<Wurst, Double> curvature = SimpleKey.named("curvature");

        Rekord<Wurst> rekord = Rekord.of(Wurst.class).accepting(curvature);
    }

    public static interface Bratwurst extends Wurst {
        Key<Bratwurst, Style> style = SimpleKey.named("style");

        Rekord<Bratwurst> rekord = Rekord.of(Bratwurst.class).accepting(Wurst.curvature, style);

        public static enum Style {
            Chopped,
            Whole
        }
    }

    public static interface Bier {
        Key<Bier, Measurement.Volume> volume = SimpleKey.named("volume");
        Key<Bier, Measurement.Length> head = SimpleKey.named("head");

        Rekord<Bier> rekord = Rekord.of(Bier.class).accepting(volume, head);
    }

    public static interface Person {
        Key<Person, String> firstName = SimpleKey.named("first name");
        Key<Person, String> lastName = SimpleKey.named("last name");
        Key<Person, Integer> age = SimpleKey.named("age");
        Key<Person, Rekord<Person>> favouritePerson = SimpleKey.named("favourite person");
        Key<Person, Rekord<Address>> address = SimpleKey.named("address");

        Rekord<Person> rekord = Rekord.of(Person.class).accepting(firstName, lastName, age, favouritePerson, address);
    }

    public static interface Address {
        Key<Address, Integer> houseNumber = SimpleKey.named("house number");
        Key<Address, String> street = SimpleKey.named("street");
        Key<Address, String> city = SimpleKey.named("city");
        Key<Address, String> postalCode = SimpleKey.named("postal code");

        Rekord<Address> rekord = Rekord.of(Address.class).accepting(houseNumber, street, city, postalCode);
    }

    public static interface Box {
        Key<Box, Integer> anyNumber = SimpleKey.named("any number");
        Key<Box, Integer> lessThanTen = anyNumber.that(validatesItsInput(is(lessThan(10))));

        Rekord<Box> rekord = Rekord.of(Box.class).accepting(anyNumber, lessThanTen);
    }

    @SuppressWarnings("UnusedDeclaration")
    public static final class Jar<T extends Jar.Contents> {
        @SuppressWarnings("unchecked")
        public static Rekord<Jar<Cookie>> ofCookies() {
            return Rekord.<Jar<Cookie>>create("Cookie Jar").accepting(contents);
        }

        private static final Key contents = SimpleKey.named("contents");
        @SuppressWarnings("unchecked")
        public static <T extends Jar.Contents> Key<Jar<T>, Collection<T>> contents() {
            return contents;
        }

        public static interface Contents { }

        public static final class Cookie implements Jar.Contents { }
    }
}