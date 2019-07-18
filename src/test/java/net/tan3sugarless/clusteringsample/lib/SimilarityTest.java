package net.tan3sugarless.clusteringsample.lib;

import net.tan3sugarless.clusteringsample.lib.data.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.stream.Stream;

@SpringBootTest
public class SimilarityTest {

    @Autowired
    private Similarity target;

    /**
     * ユークリッド距離のテストパターン
     *
     * 観点
     * ・0,それ以外
     * ・正の値、負の値
     */
    static Stream<Arguments> testEuclideanDistanceProvider(){
        return Stream.of(
            Arguments.of(1,0,0,1,1.4142)
        );
    }
    //@ParameterizedTest
    @MethodSource("testEuclideanDistanceProvider")
    @DisplayName("ユークリッド距離のテスト")
    void testEuclideanDistance(double xa, double ya, double xb, double yb, double expected){
        BigDecimal bd = new BigDecimal(target.euclideanDistance(Point.of(xa,ya), Point.of(xb,yb)));
        Assertions.assertEquals(expected , bd.setScale(3));
    }
}
