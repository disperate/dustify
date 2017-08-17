package nl.hva.summeracademy.dustify;


import android.content.Context;
import android.util.Log;

import cz.msebera.android.httpclient.Header;

public class GetLuftDatenResponseHandler extends ContextualAsyncHttpResponseHandler {

    public GetLuftDatenResponseHandler(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        Log.d("Dustify", "Sucessfull REST call");
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.d("Dustify", "Failed REST call");
    }
}
