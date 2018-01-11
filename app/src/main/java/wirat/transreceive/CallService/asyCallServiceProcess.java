package wirat.transreceive.CallService;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import org.json.JSONObject;

import java.util.HashMap;

public class asyCallServiceProcess extends AsyncTask<Void, Void, JSONObject>
{
    private AsyncTaskCompleteListener<JSONObject> callback;
    String nameMethod;
    HashMap<String, String> ParamiterValues;

    Context context;
    private ProgressDialog progress;
    private String LinkService;

    public asyCallServiceProcess(String LinkService ,Context Context,AsyncTaskCompleteListener<JSONObject> cb, String nameMethod, HashMap<String, String> ParamiterValues) {
        this.nameMethod = nameMethod;
        this.ParamiterValues = ParamiterValues;
        this.context = Context;
        this.LinkService = LinkService;
        this.callback = cb;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {

        try {
            // Simulate network access.
            Thread.sleep(200);
        } catch (InterruptedException e) {
            return null;
        }

        JSONObject myjson = null;
        try {
            SomeActivity getservice = new SomeActivity();
            String strjson = getservice.getSerVice(this.LinkService,nameMethod, ParamiterValues);
            myjson = new JSONObject(strjson);

        }catch (Exception ex) {
            return new JSONObject();
        }
        return myjson;
     }

    @Override
    protected void onPostExecute(JSONObject strjson) {
        super.onPostExecute(strjson);
        progress.dismiss();
        callback.onTaskComplete(strjson);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.progress = ProgressDialog.show(this.context, "", "Loading..");
    }

    @Override
    protected void onCancelled() {
        progress.dismiss();
    }
}
