package net.tan3sugarless.clusteringsample.lib.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;
import net.tan3sugarless.clusteringsample.exception.DimensionNotUnifiedException;
import net.tan3sugarless.clusteringsample.exception.NullCoordinateException;
import net.tan3sugarless.clusteringsample.exception.UnexpectedCentroidException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
     * @param points : n次元座標のリスト
     * @throws DimensionNotUnifiedException : 座標の次元が統一されていないリストをセットした
     * @throws NullCoordinateException      : 座標の数値にnullが含まれていた
     * @throws NullPointerException         : nullデータ、もしくはnull要素を含むデータを渡した
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

        return points.stream().map(point ->
                Math.sqrt(
                        IntStream
                                .range(0, target.size())
                                .boxed()
                                .mapToDouble(i -> Math.pow(point.get(i) - target.get(i), 2))
                                .sum()
                )
        ).collect(Collectors.toList());
    }

    /**
     * インスタンスに所属する各点の中心点となる座標を求める
     * <p>
     * 中心は算術平均とする
     *
     * <pre>
     *     n次元空間において、i番,(x11+x21+....+xm1)/m,目の点のj番目の要素をxijとして、     *
     * [x11, x12,...,x1n],[x21, x22,...,x2n]
     * とm個の点が与えられているとき、中心点の座標は、
     *
     * [(x11+x21+....+xm1)/m,(x12+x22+....+xm2)/m,...,(x1n+x2n+....+xmn)/m]
     * となる。
     *
     * という計算を行う。
     * </pre>
     *
     * @return 中心点の座標
     * @throws UnexpectedCentroidException 基本あり得ない
     */
    public List<Double> getCentroid() {
        return IntStream
                .range(0, dimension)
                .boxed()
                .map(i -> points.stream().mapToDouble(point -> point.get(i)).average().orElseThrow(UnexpectedCentroidException::new))
                .collect(Collectors.toList());
    }
}
