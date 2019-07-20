package net.tan3sugarless.clusteringsample.lib.data;

import net.tan3sugarless.clusteringsample.exception.DimensionNotUnifiedException;
import net.tan3sugarless.clusteringsample.exception.NullCoordinateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class EuclideanSpaceTest {

    //全体 null,空,要素1つ,要素複数
    //各要素 null含む,空含む,すべて空(0次元),1次元,n次元
    //各要素内の座標 null含む,0含む,null含まない
    //次元チェック すべて同じ次元,異なる次元
    static Stream<Arguments> testConstructorProvider(){
        return Stream.of(
            Arguments.of(null,new NullPointerException()),
            Arguments.of(Collections.emptyList(),null),
            Arguments.of(Arrays.asList(Arrays.asList(1.5,-2.1)),null),
            Arguments.of(Arrays.asList(Arrays.asList(1.2,0.1),Arrays.asList(0.0,1.5)),null),
            Arguments.of(Arrays.asList(null,Arrays.asList(0,1.5),Arrays.asList(-0.9,0.1)),new NullPointerException()),
            Arguments.of(Arrays.asList(Arrays.asList(-0.9,0.1),Arrays.asList(0.0,1.5),Collections.emptyList()),new DimensionNotUnifiedException()),
            Arguments.of(Arrays.asList(Collections.emptyList(),Collections.emptyList(),Collections.emptyList()),null),
            Arguments.of(Arrays.asList(Arrays.asList(1.5),Arrays.asList(0.0),Arrays.asList(-2.2)),null),
            Arguments.of(Arrays.asList(Arrays.asList(1.5,2.2,-1.9),Arrays.asList(0.0,0.0,0.0),Arrays.asList(0.9,5.0,2.2)),null),
            Arguments.of(Arrays.asList(Arrays.asList(1.5,null,-1.9),Arrays.asList(0.0,0.0,0.0),Arrays.asList(0.9,5.0,2.2)),new NullCoordinateException()),
            Arguments.of(Arrays.asList(Arrays.asList(1.5,2.1,-1.9),Arrays.asList(0.0,0.0),Arrays.asList(0.9,5.0,2.2)),new DimensionNotUnifiedException()),
            Arguments.of(Arrays.asList(Arrays.asList(2.1,-1.9),Arrays.asList(0,0,0),Arrays.asList(0.9,5.0,2.2)),new DimensionNotUnifiedException())

        );
    }

    @ParameterizedTest
    @MethodSource("testConstructorProvider")
    @DisplayName("コンストラクタのテスト")
    void testConstructor(List<List<Double>> points, RuntimeException e){
        if(e==null){
            Assertions.assertDoesNotThrow(()->new EuclideanSpace(points));
        }else{
            Assertions.assertThrows(e.getClass(),()->new EuclideanSpace(points));
        }
    }
}
