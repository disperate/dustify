package nl.hva.summeracademy.dustify;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class LuftDatenRestClient {
    private static final String BASE_URL = "https://stormy-beyond-84782.herokuapp.com/latestsensordata";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(BASE_URL, params, responseHandler);
    }
}
