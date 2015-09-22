package eclectic.dininghall;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Jared on 4/30/2015.
 */
public class WebParser {

    public static String postToMenus(String meal, String hall) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://living.sas.cornell.edu/dine/whattoeat/menus.cfm#menu");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //get current date time with Date()
        Date date = new Date();
        String dateString = dateFormat.format(date);

        //Post Data
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
        nameValuePair.add(new BasicNameValuePair("menudates", dateString));
        nameValuePair.add(new BasicNameValuePair("menuperiod", meal));
        nameValuePair.add(new BasicNameValuePair("menulocations", hall));


        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            // log exception
            e.printStackTrace();
        }

        //making POST request.
        try {
            HttpResponse response = httpClient.execute(httpPost);
            // write response to log
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            Log.d("Http Post Response:", responseString);
            return parseWebPage(responseString);
        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }
        return "Unable to get menu";
    }

    private static String parseWebPage(String text) {
        Document doc = Jsoup.parse(text);
        Element body = doc.body();
        Element container = body.getElementById("CS_CCF_4430_51083");
        Elements headers = body.getElementsByClass("menuCatHeader");
        Elements items = body.getElementsByClass("menuItem");

        String menuText = "";
        for (Element elem : container.getAllElements()) {
            if (elem.className().equals("menuCatHeader")) {
                menuText += elem.text() + "\n";
            } else if (elem.className().equals("menuItem")) {
                menuText += "\t\t\t" + elem.text() + "\n";
            }
        }

        return menuText;
    }
}
