package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import android.content.res.Resources;
import android.provider.Settings;
import android.util.Log;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.Vector;


public class JsonUtils {

    public static final String KEY_NAME = "name";
    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_ALSO_KNOW_AS = "alsoKnownAs";
    public static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_INGREDIENTS = "ingredients";
    public static final String IMAGE_KEY = "image";

    public static Sandwich parseSandwichJson(String json) throws JSONException
    {
        JSONObject jSandwich = new JSONObject(json);
        JSONObject name = jSandwich.getJSONObject(KEY_NAME);
        String mainName = name.getString(KEY_MAIN_NAME);
        JSONArray aka = name.getJSONArray(KEY_ALSO_KNOW_AS);
        List<String> akaList = new Vector<String>();
        for (int i = 0; i < aka.length(); i++)
            akaList.add(aka.getString(i));

        String originPlace = jSandwich.getString(KEY_PLACE_OF_ORIGIN);
        String description = jSandwich.getString(KEY_DESCRIPTION);
        String image = jSandwich.getString(IMAGE_KEY);

        JSONArray inArray = jSandwich.getJSONArray(KEY_INGREDIENTS);
        List<String> ingredients = new Vector<String>();
        for (int j = 0; j < inArray.length(); j++)
            ingredients.add(inArray.getString(j));

        Sandwich sandwich = new Sandwich(mainName, akaList, originPlace, description, image, ingredients);
        return sandwich;
    }


    // Practice method for learning to use JSON
    public static String parseJsonString(String json) throws JSONException
    {
        String sandwich = "";
        //String json = getString(R.string.Ham_and_Cheese);
        JSONObject jSandwich = new JSONObject(json);
        JSONObject name = jSandwich.getJSONObject("name");
        sandwich = name.getString("mainName");

        return sandwich;
    }


}

