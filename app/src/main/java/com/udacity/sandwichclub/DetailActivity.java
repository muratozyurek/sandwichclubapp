package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView ingredientsIv;
    private ImageView image_iv;
    private TextView main_name_tv;
    private TextView also_known_tv;
    private TextView origin_tv;
    private TextView description_tv;
    private TextView ingredients_tv;

    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        main_name_tv =  findViewById(R.id.main_name_tv);
        also_known_tv = findViewById(R.id.also_known_tv);
        origin_tv = findViewById(R.id.origin_tv);
        description_tv = findViewById(R.id.description_tv);
        ingredients_tv = findViewById(R.id.ingredients_tv);

        sandwich = null;

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        if(sandwich.getMainName() != null && !sandwich.getMainName().trim().equals(""))
            main_name_tv.setText(sandwich.getMainName());
        else
            main_name_tv.setText("N/A");

        if(sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0) {
            for (int i = 0; i < sandwich.getAlsoKnownAs().size() - 1; i++)
                also_known_tv.append(sandwich.getAlsoKnownAs().get(i) + ",\n");
            also_known_tv.append(sandwich.getAlsoKnownAs().get(sandwich.getAlsoKnownAs().size() - 1));
        }
        else
            also_known_tv.append("N/A");

        if(sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().trim().equals(""))
            origin_tv.setText(sandwich.getPlaceOfOrigin());
        else
            origin_tv.setText("N/A");


        if(sandwich.getDescription() != null && !sandwich.getDescription().trim().equals(""))
            description_tv.setText(sandwich.getDescription());
        else
            description_tv.setText("N/A");

        if(sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            for (int i = 0; i < sandwich.getIngredients().size() - 1; i++)
                ingredients_tv.append(sandwich.getIngredients().get(i) + ",\n");
            ingredients_tv.append(sandwich.getIngredients().get(sandwich.getIngredients().size() - 1));
        }
        else
            ingredients_tv.append("N/A");
    }
}
