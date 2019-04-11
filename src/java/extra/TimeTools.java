package extra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TimeTools {
    
    public static String getTimeFrom(long seconds) {
        return getTimeFrom(seconds, false);
    }
    
    public static String getTimeFrom(long seconds, boolean fullTime) {
        seconds = seconds < 1 ? 1 : seconds;
        ArrayList<Long> tokenK = new ArrayList<>();
        ArrayList<String> tokenV = new ArrayList<>();
        String times = "";
        
        tokenK.add(31536000L);  tokenV.add("año");
        tokenK.add(2592000L);   tokenV.add("mes");
        tokenK.add(604800L);    tokenV.add("semana");
        tokenK.add(86400L);     tokenV.add("día");
        tokenK.add(3600L);      tokenV.add("hora");
        tokenK.add(60L);        tokenV.add("minuto");
        tokenK.add(1L);         tokenV.add("segundo");
        
        for(int i=0; i<7; i++) {
            Long k = tokenK.get(i);
            String v = tokenV.get(i);
            
            if (seconds >= k) {
                int nou = (int) Math.floor(seconds / k);
                String plural = (v.equals("mes")) ? "es" : "s";
                if (fullTime) {
                    if (times.isEmpty()) {
                        times += nou + " " + v + ((nou > 1) ? plural : "");
                    } else {
                        times += ", " + nou + " " + v + ((nou > 1) ? plural : "");
                    }
                } else {
                    return nou + " " + v + ((nou > 1) ? plural : "");
                }
                seconds -= k * nou;
            }
        }
        return times;
    }

}
