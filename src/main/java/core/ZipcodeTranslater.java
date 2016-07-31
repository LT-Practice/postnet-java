package core;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;


public class ZipcodeTranslater {

    JSONObject checkZipcode(String zipcode) throws JSONException {

        JSONObject obj = new JSONObject();
//        obj.put("balance", 123);
//        JSONObject expected2 = new JSONObject("{'balance':123}");

        String formatCode = zipcode.replace("-", "");
        boolean onlyOneHyphen = zipcode.indexOf("-") == zipcode.lastIndexOf("-");
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        boolean isNumber = pattern.matcher(formatCode).matches();

        boolean type;
        if (formatCode.length() == 5 || formatCode.length() == 9 && onlyOneHyphen && isNumber) {
            obj.put("zipcode", formatCode);
            obj.put("type", new Boolean(true));
        } else {
            obj.put("zipcode", zipcode);
            obj.put("type", new Boolean(false));
        }
        return obj;


//        return ;
    }

    public static void main(String[] args) throws JSONException {
        JSONObject a = new ZipcodeTranslater().checkZipcode("45056-1234");
//        JSONObject a = new ZipcodeTranslater().checkZipcode("450561234");
        String zipcode = "450561234";
        boolean onlyOneHyphen = zipcode.indexOf("-") == zipcode.lastIndexOf("-");
        System.out.println(onlyOneHyphen);

        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        boolean yes = pattern.matcher(zipcode).matches();
        System.out.println(yes + "123");
        System.out.println(a);
    }


}
