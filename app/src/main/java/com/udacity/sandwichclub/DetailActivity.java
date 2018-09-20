package com.udacity.sandwichclub;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar titleBar = getActionBar();
        try {
            titleBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.darkMustard)));
        } catch (Exception e) { /* don't do anything about it */ }

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
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    private void populateUI(Sandwich theSandwich) {

        List<String> namesList = theSandwich.getAlsoKnownAs();
        StringBuilder nicknames = new StringBuilder();
        for (String s : namesList) {
            nicknames.append(s);
            nicknames.append(", ");
        }
        if (nicknames.length() > 2) {
            if (nicknames.charAt(nicknames.length() - 2) == ',')
                nicknames.deleteCharAt(nicknames.length() - 2);
        }
        TextView aka_label = (TextView) findViewById(R.id.aka);
        if (nicknames.length() == 0)
            aka_label.setVisibility(View.INVISIBLE);
        else
            aka_label.setVisibility(View.VISIBLE);
        TextView aka = (TextView) findViewById(R.id.also_known_tv);
        aka.setText(nicknames.toString());

        TextView ingredients = (TextView) findViewById(R.id.ingredients_tv);
        List<String> ingredientsList = theSandwich.getIngredients();
        StringBuilder iBuilder = new StringBuilder();
        int numberOfItems = ingredientsList.size();
        if (! ingredientsList.isEmpty()) {
            for (String i : ingredientsList) {
                iBuilder.append(i);
                if (i.equals(ingredientsList.get(numberOfItems - 1)))
                    iBuilder.append("");    // don't append anything after the last item
                else if (i.equals(ingredientsList.get(numberOfItems - 2)) && numberOfItems != 2)
                    iBuilder.append(", and ");
                else if (i.equals(ingredientsList.get(numberOfItems - 2)) && numberOfItems == 2)
                    iBuilder.append(" and ");
                else
                    iBuilder.append(", ");
            }
        }
        ingredients.setText(iBuilder.toString());

        TextView placeOfOrigin = (TextView) findViewById(R.id.origin_tv);
        placeOfOrigin.setText(theSandwich.getPlaceOfOrigin());

        TextView description = (TextView) findViewById(R.id.description_tv);
        description.setText(theSandwich.getDescription());

    }
}
