package com.example.lyx.myapplication.ch13.webservice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyx on 2016/7/23.
 */
public class WebServiceUtil {
//    public static List<String> getCityList() {
//        String serviceNamespace = "http://WebXml.com.cn";
//        String serviceURL = "http://webservice.webxml.com.cn/WebService/WeathherWS.asmx";
//        String methodName = "getRegionProvince";
//        SoapObject request = new SoapObject(serviceNamespace, methodName);
//        SoapSerializationEnvelop envelop = new SoapSerializationEnvelop(SoapEnvelop.VER11);
//        envelop.bodyOut = request;
//        (new MarshalBase64()).register(envelop);
//
//        AndroidHttpTransport ht = new AndroidHttpTransport(serviceURL);
//        ht.debug = true;
//
//        try {
//            ht.call("http://WebXml.com.cn/getRegionProvince", envelop);
//            if (envelop.getResponse() != null) {
//                return parse(envelop.bodyIn.toString());
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private static List<String> parse(String str) {
        String temp;
        List<String> list = new ArrayList<String>();
        if (str != null && str.length() > 0) {
            int start = str.indexOf("string");
            int end = str.lastIndexOf(";");
            temp = str.substring(start, end - 3);
            String[] test = temp.split(";");
            for (int i = 0; i < test.length; i++) {
                if (i == 0) {
                    temp = test[i].substring(7);
                } else {
                    temp = test[i].substring(8);
                }
                int index = temp.indexOf(",");
                list.add(temp.substring(0,index));
            }
        }
        return list;
    }

//    public static String getWeatherMsgByCity(String cityName) {
//        String url = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx/getWeather";
//        HttpPost request = new HttpPost(url);
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("theCityCode", cityName));
//        params.add(new BasicNameValuePair("theUserID", ""));
//        String result = null;
//
//        try {
//            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
//            request.setEntity(entity);
//            HttpResponse response = new DefaultHttpClient().execute(request);
//            if (response.getStatusLine().getStatusCode() == 200) {
//                result = EntityUtils.toString(response.getEntity());
//                return parse2(result);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private static String parse2(String str) {
        String temp;
        String[] temps;
        List list = new ArrayList();
        StringBuilder sb = new StringBuilder("");
        if (str != null && str.length() > 0) {
            temp = str.substring(str.indexOf("<string>"));
            temps = temp.split("</string>");
            for (int i = 0; i < temps.length; i++) {
                sb.append(temps[i].substring(12));
                sb.append("\n");
            }
        }
        return sb.toString();
    }

}
