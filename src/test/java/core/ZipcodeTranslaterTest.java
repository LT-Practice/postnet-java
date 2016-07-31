package core;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;


public class ZipcodeTranslaterTest {
    @Test
    public void checkZipcode() throws Exception {
        String zipcode = "45056-1234";
        ZipcodeTranslater zipcodeTranslater = new ZipcodeTranslater();

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