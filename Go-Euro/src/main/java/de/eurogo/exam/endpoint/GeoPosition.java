package de.eurogo.exam.endpoint;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeName;

/**
 * JSON Geo Position entity
 *
 * Created by haykhayryan on 8/31/15.
 */
@JsonTypeName("geo_position")
public class GeoPosition {

    @JsonProperty
    private String latitude;

    @JsonProperty
    private String longitude;

    public GeoPosition() {
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "geo_position:{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
