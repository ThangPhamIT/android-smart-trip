package Model;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Linh on 8/13/2015.
 */
public class GeoCodingAsyncTask extends AsyncTask<String,Void,List<Double>> {
    @Override
    protected List<Double> doInBackground(String... params) {
        List<Double> location=new ArrayList<>();
        String address=params[0];
        /*for(int i=0;i<address.length()-1;++i){
            int k=address.charAt(i+1);
            if(k==20){
                address.
            }
        }*/
        address=address.replaceAll(" ","+");
        String link="https://maps.googleapis.com/maps/api/geocode/json?address=" + address;
        try {
            URL url=new URL(link);
            URLConnection urlConnection=url.openConnection();
            HttpsURLConnection httpsURLConnection= (HttpsURLConnection) urlConnection;
            if(httpsURLConnection.getResponseCode()== HttpURLConnection.HTTP_OK){
                InputStream inputStream=httpsURLConnection.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer=new StringBuffer();
                String line="";
                while ((line=br.readLine())!=null){
                    stringBuffer.append(line);
                }
                String src=stringBuffer.toString();
                br.close();
                location=LatLngJSONParser.readfromAddress(src);

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }

}
