package app.clirnet.com.clirnetapp.app;

/**
 * Created by ${Ashish} on 30-04-2016.
 */


import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class JsonParsor {

    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    public JsonParsor() {

    }


    public String makeServiceCall(String url) {

        URL urlCon;
        String result = null;
        try {

            urlCon = new URL(url);
            Log.d("URL:", url);
            HttpURLConnection connection=(HttpURLConnection)urlCon.openConnection();

            connection.setRequestMethod("GET");
            if (Build.VERSION.SDK != null
                    && Build.VERSION.SDK_INT > 13) {
                connection.setRequestProperty("Connection", "close");
            }
            connection.connect();

            InputStream inputStream = connection.getInputStream();

            result = convertInputStreamToString(inputStream);


            System.out.println();

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d("Response Error: ",e.getMessage());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
//			Log.d("Response Error : ",e.getMessage().toString());
        }

        return result;

    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line;
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

}
