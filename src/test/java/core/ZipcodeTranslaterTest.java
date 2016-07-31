package core;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;


public class ZipcodeTranslaterTest {
    ZipcodeTranslater zipcodeTranslater = new ZipcodeTranslater();

    @Test
    public void zipcodeTransformToBarcode() throws Exception {
        JSONObject checkedZipcode = new JSONObject("{'zipcode':'12345','type':true}");

        String[] barcodeArray = zipcodeTranslater.zipcodeTransformToBarcode(checkedZipcode);

        String[] expected = {":::||", "::|:|", "::||:", ":|::|", ":|:|:", ":|:|:"};

        assertArrayEquals(expected, barcodeArray);
    }


    @Test
    public void buildBarcode() throws Exception {
        String[] barcodeArray = {":::||", "::|:|", "::||:", ":|::|", ":|:|:", ":|:|:"};

        String barcode = zipcodeTranslater.buildBarcode(barcodeArray);

        String expected = "|:::||::|:|::||::|::|:|:|::|:|:|";

        assertEquals(expected, barcode);
    }


    @Test
    public void matchBarcode() throws Exception {
        String[] zipcodeArrayWithCheckDigit = {"1", "2", "3", "4", "5", "5"};

        String[] barcodeArray = zipcodeTranslater.matchBarcode(zipcodeArrayWithCheckDigit);

        String[] expected = {":::||", "::|:|", "::||:", ":|::|", ":|:|:", ":|:|:"};

        assertArrayEquals(expected, barcodeArray);
    }


    //6-3
    @Test
    public void addCheckDigit() throws Exception {
        String[] zipcodeArray = {"1", "2", "3", "4", "5"};

        String[] zipcodeArrayWithCheckDigit = zipcodeTranslater.addCheckDigit(zipcodeArray);

        String[] expected = {"1", "2", "3", "4", "5", "5"};

        assertArrayEquals(expected, zipcodeArrayWithCheckDigit);

    }


    //#6-2
    @Test
    public void buildZipcodeArray() throws Exception {
        String formattedZipcode = "450561234";

        String[] zipcodeArray = zipcodeTranslater.buildZipcodeArray(formattedZipcode);

        String[] expected = {"4", "5", "0", "5", "6", "1", "2", "3", "4"};

        assertArrayEquals(expected, zipcodeArray);

    }


    //#6-1
    @Test
    public void formatZipcode() throws Exception {
        JSONObject checkedZipcode = new JSONObject("{'zipcode':'45056-1234','type':true}");

        String formattedZipcode = zipcodeTranslater.formatZipcode(checkedZipcode);

        String expected = "450561234";

        assertEquals(expected, formattedZipcode);


    }

    @Test
    public void formatZipcode2() throws Exception {
        JSONObject checkedZipcode = new JSONObject("{'zipcode':'45056','type':true}");

        String formattedZipcode = zipcodeTranslater.formatZipcode(checkedZipcode);

        String expected = "45056";

        assertEquals(expected, formattedZipcode);


    }

    //#5
    @Test
    public void checkZipcode() throws Exception {
        String zipcode = "45056-1234";

        JSONObject checkedZipcode = zipcodeTranslater.checkZipcode(zipcode);
//        String expected = "{\"zipcode\":\"45056-1234\",\"type\":true}";
        JSONObject expected = new JSONObject("{'zipcode':'450561234','type':true}");
        assertEquals(expected.toString(), checkedZipcode.toString());

    }

    @Test
    public void checkZipcode2() throws Exception {
        String zipcode = "45056:";
        ZipcodeTranslater zipcodeTranslater = new ZipcodeTranslater();

        JSONObject checkedZipcode = zipcodeTranslater.checkZipcode(zipcode);

        JSONObject expected = new JSONObject("{'zipcode':'45056:','type':false}");

        assertEquals(expected.toString(), checkedZipcode.toString());

    }

    @Test
    public void checkZipcode3() throws Exception {
        String zipcode = "45056null";
        ZipcodeTranslater zipcodeTranslater = new ZipcodeTranslater();

        JSONObject checkedZipcode = zipcodeTranslater.checkZipcode(zipcode);

        JSONObject expected = new JSONObject("{'zipcode':'45056null','type':false}");

        assertEquals(expected.toString(), checkedZipcode.toString());

    }

    @Test
    public void checkZipcode4() throws Exception {
        String zipcode = "45056";
        ZipcodeTranslater zipcodeTranslater = new ZipcodeTranslater();

        JSONObject checkedZipcode = zipcodeTranslater.checkZipcode(zipcode);

        JSONObject expected = new JSONObject("{'zipcode':'45056','type':true}");

        assertEquals(expected.toString(), checkedZipcode.toString());

    }

    @Test
    public void checkZipcode5() throws Exception {
        String zipcode = "4505-";
        ZipcodeTranslater zipcodeTranslater = new ZipcodeTranslater();

        JSONObject checkedZipcode = zipcodeTranslater.checkZipcode(zipcode);

        JSONObject expected = new JSONObject("{'zipcode':'4505-','type':false}");

        assertEquals(expected.toString(), checkedZipcode.toString());

    }


}