package core;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;


public class ZipcodeTranslater {

    //#5
    JSONObject checkZipcode(String zipcode) throws JSONException {

        JSONObject obj = new JSONObject();

        String formatCode = zipcode.replace("-", "");
        boolean onlyOneHyphen = zipcode.indexOf("-") == zipcode.lastIndexOf("-");
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        boolean isNumber = pattern.matcher(formatCode).matches();

        if (formatCode.length() == 5 || formatCode.length() == 9 && onlyOneHyphen && isNumber) {
            obj.put("zipcode", formatCode);
            obj.put("type", Boolean.TRUE);
        } else {
            obj.put("zipcode", zipcode);
            obj.put("type", Boolean.FALSE);
        }

        return obj;
    }

    //#6-1
    String formatZipcode(JSONObject checkedZipcode) throws JSONException {
        String formattedZipcode = checkedZipcode.getString("zipcode").replace("-", "");

        return formattedZipcode;
    }

    //#6-2
    String[] buildZipcodeArray(String formattedZipcode) {
        return formattedZipcode.split("");

    }

    //#6-3
    String[] addCheckDigit(String[] zipcodeArray) {
//        int[] zipcodeNumbers = new int[];
        int sumResult = 0;
        for (int i = 0; i < zipcodeArray.length; i++) {
            sumResult += Integer.parseInt(zipcodeArray[i]);
        }

        String checkDigit;
        if (sumResult % 10 != 0) {
            checkDigit = String.valueOf((10 - sumResult % 10));
        } else {
            checkDigit = String.valueOf((0));
        }
        String[] zipcodeArrayWithCheckDigit = new String[zipcodeArray.length + 1];
        for (int i = 0; i < zipcodeArray.length; i++) {
            zipcodeArrayWithCheckDigit[i] = zipcodeArray[i];
        }
        zipcodeArrayWithCheckDigit[zipcodeArray.length] = checkDigit;

        return zipcodeArrayWithCheckDigit;
    }

    //#6-4
    String[] matchBarcode(String[] zipcodeArrayWithCheckDigit) throws JSONException {
//        {'key': '0', 'digit': '||:::'},
//        {'key': '1', 'digit': ':::||'},
//        {'key': '2', 'digit': '::|:|'},
//        {'key': '3', 'digit': '::||:'},
//        {'key': '4', 'digit': ':|::|'},
//        {'key': '5', 'digit': ':|:|:'},
//        {'key': '6', 'digit': ':||::'},
//        {'key': '7', 'digit': '|:::|'},
//        {'key': '8', 'digit': '|::|:'},
//        {'key': '9', 'digit': '|:|::'},
        JSONArray obj = new JSONArray("[" +
                "{'key': '0', 'digit': '||:::'},\n" +
                "{'key': '1', 'digit': ':::||'},\n" +
                "{'key': '2', 'digit': '::|:|'},\n" +
                "{'key': '3', 'digit': '::||:'},\n" +
                "{'key': '4', 'digit': ':|::|'},\n" +
                "{'key': '5', 'digit': ':|:|:'},\n" +
                "{'key': '6', 'digit': ':||::'},\n" +
                "{'key': '7', 'digit': '|:::|'},\n" +
                "{'key': '8', 'digit': '|::|:'},\n" +
                "{'key': '9', 'digit': '|:|::'}]");
        String s = obj.getJSONObject(1).getString("key");
        String[] barcodeArray = new String[zipcodeArrayWithCheckDigit.length];
//        for (int i = 0; i < zipcodeArrayWithCheckDigit.length; i++) {
//            for (int j = 0; j < obj.length(); j++) {
//                System.out.println(zipcodeArrayWithCheckDigit[i]);
//                if (obj.getJSONObject(j).getString("key") == zipcodeArrayWithCheckDigit[i]) {
//                    barcodeArray[i] = obj.getJSONObject(i).getString("digit");
//                }
//            }
//            System.out.println(barcodeArray[i]);
//        }
        for (int i = 0; i < zipcodeArrayWithCheckDigit.length; i++) {
            for (int j = 0; j < obj.length(); j++) {
                if (obj.getJSONObject(j).getString("key").contains(zipcodeArrayWithCheckDigit[i])) {
//                    System.out.println(obj.getJSONObject(j).getString("digit"));
                    barcodeArray[i] = obj.getJSONObject(j).getString("digit");
                }
            }
//            System.out.println(barcodeArray[i]);
        }

        return barcodeArray;
    }


    public static void main(String[] args) throws JSONException {
        String[] zipcodeArrayWithCheckDigit = {"5", "2", "3", "4", "5", "5"};

        ZipcodeTranslater A = new ZipcodeTranslater();
//        String s=A.matchBarcode(zipcodeArrayWithCheckDigit);
//        System.out.println(s);
        A.matchBarcode(zipcodeArrayWithCheckDigit);
    }

    //


}
