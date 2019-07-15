package net.tan3sugarless.clusteringsample.lib.data;

import lombok.*;

/**
 * 直交座標を表すクラス
 */
@Getter
@RequiredArgsConstructor(staticName="of")
@ToString
@EqualsAndHashCode
public class Point {
    private final double x;
    private final double y;
}
