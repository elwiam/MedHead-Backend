package services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NominatimService {

    private static final String NOMINATIM_API_URL = "https://nominatim.openstreetmap.org/search?format=json&q=";

    public Coordinates geocodeAddress(String address) {
        String url = NOMINATIM_API_URL + address.replace(" ", "+");
        RestTemplate restTemplate = new RestTemplate();
        NominatimResponse[] responses = restTemplate.getForObject(url, NominatimResponse[].class);
        if (responses != null && responses.length > 0) {
            NominatimResponse response = responses[0];
            double latitude = Double.parseDouble(response.getLat());
            double longitude = Double.parseDouble(response.getLon());
            return new Coordinates(latitude, longitude);
        }
        return null;
    }

    private static class NominatimResponse {
        private String lat;
        private String lon;

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }
    }
}
