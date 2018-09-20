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

    public static Sandwich parseSandwichJson(String json) throws JSONException
    {
        JSONObject jSandwich = new JSONObject(json);
        JSONObject name = jSandwich.getJSONObject("name");
        String mainName = name.getString("mainName");
        JSONArray aka = name.getJSONArray("alsoKnownAs");
        List<String> akaList = new Vector<String>();
        for (int i = 0; i < aka.length(); i++)
            akaList.add(aka.getString(i));

        String originPlace = jSandwich.getString("placeOfOrigin");
        String description = jSandwich.getString("description");
        String image = jSandwich.getString("image");

        JSONArray inArray = jSandwich.getJSONArray("ingredients");
        List<String> ingredients = new Vector<String>();
        for (int j = 0; j < inArray.length(); j++)
            ingredients.add(inArray.getString(j));

        Sandwich sandwich = new Sandwich(mainName, akaList, originPlace, description, image, ingredients);
        return sandwich;
    }

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

