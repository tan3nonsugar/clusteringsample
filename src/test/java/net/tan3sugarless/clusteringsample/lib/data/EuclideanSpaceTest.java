package net.tan3sugarless.clusteringsample.lib.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class EuclideanSpaceTest {

    //全体 null,空,要素1つ,要素複数
    //各要素 null含む,空含む,1次元,n次元
    //次元チェック すべて同じ次元,異なる次元
    static Stream<Arguments> testConstructorProvider(){
        return Stream.of(


        );
    }

    // @ParameterizedTest
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
