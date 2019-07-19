package net.tan3sugarless.clusteringsample.lib.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;
import net.tan3sugarless.clusteringsample.exception.DimensionNotUnifiedException;

import java.util.List;

/**
 * ユークリッド距離空間上の座標集合
 */
@Getter
@ToString
@EqualsAndHashCode
@Value
public class EuclideanSpace {

    private final List<List<Double>> points;

    /**
     * n次元座標のリストをセットする
     *
     * DimensionNotUnifiedException
     * 座標の次元が統一されていないリストをセットした
     *
     * NullPointerException
     * nullデータ、もしくはnull要素を含むデータを渡した
     *
     * @param points : n次元座標のリスト
     */
    public EuclideanSpace(List<List<Double>> points){
        if(points.stream().mapToInt(List::size).distinct().count()!=1){
            throw new DimensionNotUnifiedException();
        }
        this.points = points;
    }
}
