package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {


        String mainName = null;
        List<String> alsoKnownAs = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> ingredients = null;

        try {
            JSONObject sandwich = new JSONObject(json);
            JSONObject name = sandwich.getJSONObject("name");
            mainName = name.getString("mainName");

            JSONArray arrayAlsoKnownAs = name.getJSONArray("alsoKnownAs");
            alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < arrayAlsoKnownAs.length(); i++)
            {
                alsoKnownAs.add(arrayAlsoKnownAs.getString(i));
            }


            placeOfOrigin = sandwich.getString("placeOfOrigin");
            description = sandwich.getString("description");
            image = sandwich.getString("image");

            JSONArray arrayIngredients = sandwich.getJSONArray("ingredients");
            ingredients = new ArrayList<>();
            for (int i = 0; i < arrayIngredients.length(); i++)
            {
                ingredients.add(arrayIngredients.getString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        return sandwich;

    }
}
