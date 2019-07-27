package net.tan3sugarless.clusteringsample.lib.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;
import net.tan3sugarless.clusteringsample.exception.DimensionNotUnifiedException;
import net.tan3sugarless.clusteringsample.exception.NullCoordinateException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
     * <p>
     * DimensionNotUnifiedException
     * 座標の次元が統一されていないリストをセットした
     * <p>
     * NullCoordinateException
     * 座標の数値にnullが含まれていた
     * <p>
     * NullPointerException
     * nullデータ、もしくはnull要素を含むデータを渡した
     *
     * @param points : n次元座標のリスト
     */
    public EuclideanSpace(List<List<Double>> points) {
        if (points.stream().mapToInt(List::size).distinct().count() > 1) {
            throw new DimensionNotUnifiedException();
        }
        if (points.stream().anyMatch(point -> point.stream().anyMatch(Objects::isNull))) {
            throw new NullCoordinateException();
        }

        this.points = points;
        this.dimension = points.stream().findFirst().map(List::size).orElse(0);
    }

    /**
     * 任意の座標からの、このインスタンスに格納された各座標の距離を算出する
     *
     * @param target 各座標からの距離を出したい基準点の座標
     * @return targetからのユークリッド距離を表すリスト
     * @throws DimensionNotUnifiedException : targetとインスタンスの次元が異なる
     * @throws NullCoordinateException      : targetにnullを含む
     */
    public List<Double> distanceFromTarget(List<Double> target) {
        if (target.size() != dimension) {
            throw new DimensionNotUnifiedException();
        }

        if (target.stream().anyMatch(Objects::isNull)) {
            throw new NullCoordinateException();
        }

        return points.stream().map(point -> {
            double squareOfDistance = 0.0;
            for (int i = 0; i < target.size(); i++) {
                squareOfDistance += Math.pow(point.get(i) - target.get(i), 2);
            }

            return Math.sqrt(squareOfDistance);
        }).collect(Collectors.toList());
    }
}
