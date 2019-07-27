package net.tan3sugarless.clusteringsample.lib.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;
import net.tan3sugarless.clusteringsample.exception.DimensionNotUnifiedException;
import net.tan3sugarless.clusteringsample.exception.NullCoordinateException;

import java.util.List;
import java.util.Objects;

/**
 * ユークリッド距離空間上の座標集合
 */
@Getter
@ToString
@EqualsAndHashCode
@Value
public class EuclideanSpace {

    private final List<List<Double>> points;
    private final int dimension;

    /**
     * n次元座標のリストと次元をセットする
     *
     * DimensionNotUnifiedException
     * 座標の次元が統一されていないリストをセットした
     *
     * NullCoordinateException
     * 座標の数値にnullが含まれていた
     *
     * NullPointerException
     * nullデータ、もしくはnull要素を含むデータを渡した
     *
     * @param points : n次元座標のリスト
     */
    public EuclideanSpace(List<List<Double>> points){
        if(points.stream().mapToInt(List::size).distinct().count()>1){
            throw new DimensionNotUnifiedException();
        }
        if(points.stream().anyMatch(point -> point.stream().anyMatch(Objects::isNull))){
            throw new NullCoordinateException();
        }

        this.points = points;
        this.dimension = points.stream().findFirst().map(List::size).orElse(0);
    }
}
