package net.tan3sugarless.clusteringsample.lib.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;
import net.tan3sugarless.clusteringsample.exception.DimensionNotUnifiedException;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@Value
public class EuclideanSpace {

    private final List<List<Double>> points;

    public EuclideanSpace(List<List<Double>> points){
        long dimension = points.get(0).size();
        points.stream().map(List::size).forEach(n ->{
            if(dimension!=n){
                throw new DimensionNotUnifiedException();
            }
        });
        this.points = points;
    }
}
