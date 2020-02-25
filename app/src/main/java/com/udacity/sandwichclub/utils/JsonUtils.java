package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static Sandwich parseSandwichJson(String json) {
        String mainName = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> alsoKnownAs = new ArrayList<String>();
        List<String> ingredients = new ArrayList<String>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject name = jsonObject.getJSONObject("name");
            mainName = name.getString("mainName");
            placeOfOrigin = jsonObject.getString("placeOfOrigin");
            description = jsonObject.getString("description");
            image = jsonObject.getString("image");

            JSONArray alsoKnownData = name.getJSONArray("alsoKnownAs");
            for(int i = 0; i < alsoKnownData.length(); i++) {
                alsoKnownAs.add(alsoKnownData.get(i).toString());
            }

            JSONArray ingredientsData = jsonObject.getJSONArray("ingredients");
            for(int i = 0; i < ingredientsData.length(); i++) {
                ingredients.add(ingredientsData.get(i).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
