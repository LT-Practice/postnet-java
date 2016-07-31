package core;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;


public class ZipcodeTranslater {

    //all
    JSONObject zipcodeToBarcode(String zipcode) throws JSONException {
        JSONObject checkedZipcode = checkZipcode(zipcode);
        String[] barcodeArray = zipcodeTransformToBarcode(checkedZipcode);
        String barcode = buildBarcode(barcodeArray);
        boolean type = Boolean.parseBoolean(checkedZipcode.getString("type"));
        JSONObject result = new JSONObject();
        result.put("barcode", barcode);
        result.put("type", type);
        return result;

    }

    //#5
    JSONObject checkZipcode(String zipcode) throws JSONException {

        JSONObject obj = new JSONObject();

        String formatCode = zipcode.replace("-", "");
        boolean onlyOneHyphen = zipcode.indexOf("-") == zipcode.lastIndexOf("-");
        Pattern pattern = Pattern.compile("^[-+]?[\\d]*$");
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

    //#6
    String[] zipcodeTransformToBarcode(JSONObject checkedZipcode) throws JSONException {
        String formattedZipcode = formatZipcode(checkedZipcode);
        String[] zipcodeArray = buildZipcodeArray(formattedZipcode);
        String[] zipcodeArrayWithCheckDigit = addCheckDigit(zipcodeArray);
        String[] barcodeArray = matchBarcode(zipcodeArrayWithCheckDigit);
        return barcodeArray;
    }
    //#6-1
    String formatZipcode(JSONObject checkedZipcode) throws JSONException {

        return checkedZipcode.getString("zipcode").replace("-", "");
    }

    //#6-2
    String[] buildZipcodeArray(String formattedZipcode) {
        return formattedZipcode.split("");

    }

    //#6-3
    String[] addCheckDigit(String[] zipcodeArray) {
        int sumResult = 0;
        for (String aZipcodeArray : zipcodeArray) {
            sumResult += Integer.parseInt(aZipcodeArray);
        }

        String checkDigit;
        if (sumResult % 10 != 0) {
            checkDigit = String.valueOf((10 - sumResult % 10));
        } else {
            checkDigit = String.valueOf((0));
        }
        String[] zipcodeArrayWithCheckDigit = new String[zipcodeArray.length + 1];
        System.arraycopy(zipcodeArray, 0, zipcodeArrayWithCheckDigit, 0, zipcodeArray.length);
        zipcodeArrayWithCheckDigit[zipcodeArray.length] = checkDigit;

        return zipcodeArrayWithCheckDigit;
    }

    //#6-4
    String[] matchBarcode(String[] zipcodeArrayWithCheckDigit) throws JSONException {
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
        String[] barcodeArray = new String[zipcodeArrayWithCheckDigit.length];
        for (int i = 0; i < zipcodeArrayWithCheckDigit.length; i++) {
            for (int j = 0; j < obj.length(); j++) {
                if (obj.getJSONObject(j).getString("key").contains(zipcodeArrayWithCheckDigit[i])) {
                    barcodeArray[i] = obj.getJSONObject(j).getString("digit");
                }
            }
        }

        return barcodeArray;
    }
    //#7
    String buildBarcode(String[] barcodeArray){

        String barcode = "";
        for (String aBarcodeArray : barcodeArray) {
            barcode += aBarcodeArray;
        }
        return "|"+barcode+"|";
    }


    public static void main(String[] args) throws JSONException {
        ZipcodeTranslater a = new ZipcodeTranslater();
        JSONObject ssss = a.zipcodeToBarcode("12345");
        System.out.println(ssss);

    }


}
