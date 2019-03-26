package extra;

import org.json.JSONObject;

public class tets {
    public static void main(String[] args) {
        JSONObject json = GeoAPI.search("Sierra Hermosa, Tecámac, México");
        System.out.println(json);
    }
}
