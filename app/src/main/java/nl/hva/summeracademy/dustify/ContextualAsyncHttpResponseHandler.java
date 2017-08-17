package nl.hva.summeracademy.dustify;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;

public abstract class ContextualAsyncHttpResponseHandler extends AsyncHttpResponseHandler {
    protected Context context;

    public ContextualAsyncHttpResponseHandler(Context context) {
        this.context = context;
    }
}
