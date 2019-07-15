package net.tan3sugarless.clusteringsample.controller;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class DemoTest {

    static Stream<Arguments> demoProvider(){
        return Stream.of(
                Arguments.of(1,1,2),
                Arguments.of(2,2,4)
        );
    }

    @ParameterizedTest
    @MethodSource("demoProvider")
    @DisplayName("Parameterizedテストのデモ")
    void demo(int i, int j, int expected){
        Assertions.assertEquals(expected,i+j);
    }

    @Test
    void fail(){
        Assertions.fail();
    }
}
