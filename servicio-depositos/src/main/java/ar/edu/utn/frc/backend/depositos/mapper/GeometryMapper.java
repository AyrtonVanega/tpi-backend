package ar.edu.utn.frc.backend.depositos.mapper;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class GeometryMapper {

    private final GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);

    @Named("toPoint")
    public Point toPoint(double lat, double lon) {
        return factory.createPoint(new Coordinate(lon, lat));
    }

    @Named("toLat")
    public double toLat(Point point) {
        return point.getY();
    }

    @Named("toLon")
    public double toLon(Point point) {
        return point.getX();
    }
}
