package nl.hva.summeracademy.dustify;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class LuftDatenRestClient {
    private static final String BASE_URL = "http://api.luftdaten.info/static/v1/data.json";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(AsyncHttpResponseHandler responseHandler) {
        client.get(BASE_URL, null, responseHandler);
    }
}
