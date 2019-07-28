package net.tan3sugarless.clusteringsample.lib.data;

import net.tan3sugarless.clusteringsample.exception.DimensionNotUnifiedException;
import net.tan3sugarless.clusteringsample.exception.NullCoordinateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

class EuclideanSpaceTest {
    /**
     * 全体 null,空,要素1つ,要素複数
     * 各要素 null含む,空含む,すべて空(0次元),1次元,n次元
     * 各要素内の座標 null含む,0含む,null含まない
     * 次元チェック すべて同じ次元,異なる次元
     */
    static Stream<Arguments> testConstructorProvider() {
        //@formatter:off
        return Stream.of(
                Arguments.of(null, new NullPointerException(), 0),
                Arguments.of(Collections.emptyList(), null, 0),
                Arguments.of(asList(asList(1.5, -2.1)), null, 2),
                Arguments.of(asList(asList(1.2, 0.1), asList(0.0, 1.5)), null, 2),
                Arguments.of(asList(null, asList(0, 1.5), asList(-0.9, 0.1)), new NullPointerException(), 0),
                Arguments.of(asList(asList(-0.9, 0.1), asList(0.0, 1.5), Collections.emptyList()), new DimensionNotUnifiedException(), 0),
                Arguments.of(asList(Collections.emptyList(), Collections.emptyList(), Collections.emptyList()), null, 0),
                Arguments.of(asList(asList(1.5), asList(0.0), asList(-2.2)), null, 1),
                Arguments.of(asList(asList(1.5, 2.2, -1.9), asList(0.0, 0.0, 0.0), asList(0.9, 5.0, 2.2)), null, 3),
                Arguments.of(asList(asList(1.5, null, -1.9), asList(0.0, 0.0, 0.0), asList(0.9, 5.0, 2.2)), new NullCoordinateException(), 0),
                Arguments.of(asList(asList(1.5, 2.1, -1.9), asList(0.0, 0.0), asList(0.9, 5.0, 2.2)), new DimensionNotUnifiedException(), 0),
                Arguments.of(asList(asList(2.1, -1.9), asList(0, 0, 0), asList(0.9, 5.0, 2.2)), new DimensionNotUnifiedException(), 0)
        );
        //@formatter:on
    }

    @ParameterizedTest
    @MethodSource("testConstructorProvider")
    @DisplayName("コンストラクタのテスト")
    void testConstructor(List<List<Double>> points, RuntimeException e, int dimension) {
        if (e == null) {
            Assertions.assertDoesNotThrow(() -> new EuclideanSpace(points));

            EuclideanSpace actual = new EuclideanSpace(points);
            Assertions.assertEquals(dimension, actual.getDimension());
        } else {
            Assertions.assertThrows(e.getClass(), () -> new EuclideanSpace(points));
        }
    }

    /**
     * points : 0件/1件/2件, 0次元/1次元/2次元, 0/正/負
     * target : null/空/1次元/2次元, null含む/含まない, 0/正/負/同一座標
     */
    static Stream<Arguments> testDistanceFromTargetProvider() {
        return Stream.of(
                //@formatter:off
                Arguments.of(Collections.emptyList(), Collections.emptyList(), null, Collections.emptyList()),
                Arguments.of(Collections.emptyList(), asList(0.1), new DimensionNotUnifiedException(), Collections.emptyList()),
                Arguments.of(asList(Collections.emptyList()), Collections.emptyList(), null, asList(0.0)),
                Arguments.of(asList(Collections.emptyList()), asList(0.1), new DimensionNotUnifiedException(), Collections.emptyList()),
                Arguments.of(asList(asList(3.0)), asList(1.0), null, asList(2.0)),
                Arguments.of(asList(asList(3.0)), asList(1.0, 2.0), new DimensionNotUnifiedException(), Collections.emptyList()),
                Arguments.of(asList(asList(-1.0, 0.0)), asList(2.0, -4.0), null, asList(5.0)),
                Arguments.of(asList(asList(-1.0, 0.0)), asList(null, -4.0), new NullCoordinateException(), Collections.emptyList()),
                Arguments.of(asList(asList(-3.0, 0.0), asList(0.0, -4.0)), asList(0.0, -4.0), null, asList(5.0, 0.0))
                //@formatter:on
        );
    }

    @ParameterizedTest
    @MethodSource("testDistanceFromTargetProvider")
    @DisplayName("距離算出のテスト")
    void testDistanceFromTarget(List<List<Double>> points, List<Double> target, RuntimeException e, List<Double> distances) {
        EuclideanSpace space = new EuclideanSpace(points);

        if (e == null) {
            Assertions.assertEquals(distances, space.distanceFromTarget(target));
        } else {
            Assertions.assertThrows(e.getClass(), () -> space.distanceFromTarget(target));
        }
    }

    /**
     * points : 0次元/2次元 x 3要素
     */
    static Stream<Arguments> testGetCentroidProvider() {
        return Stream.of(
                //@formatter:off
                Arguments.of(Collections.emptyList(), Collections.emptyList()),
                Arguments.of(asList(Collections.emptyList(), Collections.emptyList(), Collections.emptyList()), Collections.emptyList()),
                Arguments.of(asList(asList(2.0, -4.0), asList(1.0, 0.0), asList(6.0, 1.0)), asList(3.0, -1.0))
                //@formatter:on
        );
    }

    @ParameterizedTest
    @MethodSource("testGetCentroidProvider")
    @DisplayName("中心算出のテスト")
    void testGetCentroid(List<List<Double>> points, List<Double> centroid) {
        EuclideanSpace space = new EuclideanSpace(points);

        Assertions.assertEquals(centroid, space.getCentroid());
    }

}
