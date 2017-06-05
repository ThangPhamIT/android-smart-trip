package Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linh on 8/13/2015.
 */
public class LatLngJSONParser {
    public static List<Double> readfromAddress(String src){
        List<Double> list=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(src);
            JSONArray array=jsonObject.getJSONArray("results");
            JSONObject result=array.getJSONObject(0);
            JSONObject loocation=result.getJSONObject("geometry").getJSONObject("location");
            list.add(loocation.getDouble("lat"));
            list.add(loocation.getDouble("lng"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
