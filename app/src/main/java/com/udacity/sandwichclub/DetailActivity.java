package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        showAlsoKnow(sandwich);
        showIngredients(sandwich);
        showPlaceOfOrigin(sandwich);
        showDescription(sandwich);
    }

    private void showAlsoKnow(Sandwich sandwich) {
        TextView alsoKnownLabel = findViewById(R.id.also_know_label);
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);
        List<String> alsoKnows  = sandwich.getAlsoKnownAs();

        if (alsoKnows.size() == 0) {
            alsoKnownLabel.setVisibility(View.GONE);
            alsoKnownTv.setVisibility(View.GONE);
            return;
        }

        alsoKnownLabel.setVisibility(View.VISIBLE);
        alsoKnownTv.setVisibility(View.VISIBLE);
        int countAlsoKnow = 1;
        for (String alsoKnow: alsoKnows) {
            alsoKnownTv.append(getString(R.string.ItemMark) + alsoKnow);
            if (countAlsoKnow < alsoKnows.size()) {
                alsoKnownTv.append("\n");
            }
            countAlsoKnow++;
        }
    }

    private void showIngredients(Sandwich sandwich) {
        TextView ingredientsLabel = findViewById(R.id.ingredients_label);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        List<String> ingredients  = sandwich.getIngredients();

        if (ingredients.size() == 0) {
            ingredientsLabel.setVisibility(View.GONE);
            ingredientsTv.setVisibility(View.GONE);
            return;
        }

        ingredientsLabel.setVisibility(View.VISIBLE);
        ingredientsTv.setVisibility(View.VISIBLE);
        int countIngredients = 1;
        for (String ingredient: ingredients) {
            ingredientsTv.append(getString(R.string.ItemMark) + ingredient);
            if (countIngredients < ingredients.size()) {
                ingredientsTv.append("\n");
            }
            countIngredients++;
        }
    }

    private void showPlaceOfOrigin(Sandwich sandwich) {
        TextView placeOfOrigin = findViewById(R.id.origin_tv);
        TextView placeOfOriginLabel = findViewById(R.id.origin_label);
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            placeOfOrigin.setVisibility(View.GONE);
            placeOfOriginLabel.setVisibility(View.GONE);
            return;
        }

        placeOfOrigin.setVisibility(View.VISIBLE);
        placeOfOriginLabel.setVisibility(View.VISIBLE);
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
    }

    private void showDescription(Sandwich sandwich) {
        TextView descriptionTv = findViewById(R.id.description_tv);
        TextView descriptionLabel = findViewById(R.id.description_label);

        if (sandwich.getDescription().isEmpty()) {
            descriptionTv.setVisibility(View.GONE);
            descriptionLabel.setVisibility(View.GONE);
            return;
        }

        descriptionLabel.setVisibility(View.VISIBLE);
        descriptionTv.setVisibility(View.VISIBLE);
        descriptionTv.setText(sandwich.getDescription());
    }
}
