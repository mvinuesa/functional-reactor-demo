package com.mvinuesa.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * This class test {@link Stream} elements
 */
@Slf4j
class FunctionalStreamTest {


    @Test
    @DisplayName("testFunction")
    void testFunction() {
        Stream.of("Pepe", "Juan", "Pepito")
            .filter(name -> name.startsWith("Pep"))
            .map(name -> name + 1)
            .forEach(newName -> LOGGER.info(newName));

        var optional = Optional
            .of(1)
            .map(integer -> 2 + integer);
        assertEquals(3, optional.orElse(0));

        assertEquals(2, Stream.of("Pepe", "Juan", "Pepito")
            .filter(s -> s.startsWith("Pep"))
            .count());
    }

    @Test
    @DisplayName("testFunction2")
    void testFunction2() {
        var optional = Optional.of(1).map(add2());
        assertEquals(3, optional.orElse(0));
    }

    @Test
    @DisplayName("testFunction3")
    void testFunction3() {
        Function<Integer, Integer> add2 = integer -> 2 + integer;
        var optional = Optional.of(1).map(add2);
        assertEquals(3, optional.orElse(0));
    }

    private Function<Integer, Integer> add2() {
        return integer -> 2 + integer;
    }

    @Test
    @DisplayName("testFunction4")
    void testFunction4() {
        UnaryOperator<Integer> add2 = integer -> 2 + integer;
        var optional = Optional.of(1).map(add2);
        assertEquals(3, optional.orElse(0));
    }
}
