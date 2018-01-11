package wirat.transreceive.CallService;

import android.util.Log;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.Map;

public class SomeActivity {

    private final String NAMESPACE = "http://tempuri.org/";
    public static String URL = "";

    public String getSerVice(String URL, String METHOD, HashMap<String, String> ParamiterValues) {
        // TODO: attempt authentication against a network service.

        String SOAP_ACTION = "http://tempuri.org/" + METHOD;
        String METHOD_NAME = METHOD;
        String TAG = METHOD;

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        PropertyInfo pi;
        for(Map.Entry<String, String> entry : ParamiterValues.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (!key.equals("")) {
                pi = new PropertyInfo();
                pi.setName(key);
                pi.setValue(value);
                pi.setType(String.class);
                request.addProperty(pi);
            }
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(URL);
        Object response = null;
        String x = "";
        try {
            httpTransport.call(SOAP_ACTION, envelope);
            response = envelope.getResponse();
            x = response.toString();
            Log.i(TAG, x);
        } catch (Exception exception) {
            response = exception.toString();
            x = response.toString();
            Log.i(TAG, x);
        }
        return x;

    }

}