package net.tan3sugarless.clusteringsample.lib;

import net.tan3sugarless.clusteringsample.lib.data.Point;
import org.springframework.stereotype.Component;

/**
 * 類似度・非類似度の算出ライブラリ
 */
@Component
public class Similarity {

    /**
     * 2点間のユークリッド距離を算出する
     *
     * √((xa-xb)^2+(ya-yb)^2)
     *
     * @param a 点aの2次元座標を表すPointオブジェクト
     * @param b 点bの2次元座標を表すPointオブジェクト
     * @return 2点間のユークリッド距離
     */
    public double euclideanDistance(Point a, Point b){
      return Math.sqrt(Math.pow(a.getX()-b.getX(),2) + Math.pow(a.getY()-b.getY(),2));
    }
}
