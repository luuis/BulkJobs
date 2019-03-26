package extra;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.JSONException;
import org.json.JSONObject;

public class GeoAPI {
    private static final String PRIVATE_TOKEN = "115f61c25a5832";
    
    public static JSONObject search(String query) {
        JSONObject json = new JSONObject();
        try {
            query = URLEncoder.encode(query, "UTF-8");
            String callback = "https://us1.locationiq.com/v1/search.php?key=" + PRIVATE_TOKEN +
                    "&q=" + query + "&format=json";
            System.out.println(callback);
            String stream = new Scanner(new URL(callback).openStream(), "UTF-8").useDelimiter("\\A").next();
            json = new JSONObject("{response:" + stream + "}");
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
        }
        return json;
    }
    
    public static JSONObject reverse(String latitude, String longitude) {
        JSONObject json = new JSONObject();
        try {
            String callback = "https://us1.locationiq.com/v1/reverse.php?key=" + PRIVATE_TOKEN +
                    "&lat=" + latitude + "&lon=" + longitude + "&format=json";
            String stream = new Scanner(new URL(callback).openStream(), "UTF-8").useDelimiter("\\A").next();
            json = new JSONObject(stream);
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
        }
        return json;
    }
}
