package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ItemDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);

        Intent intent = getIntent();
        if (intent != null) {
            String itemName = intent.getStringExtra("itemName");
            String itemPrice = intent.getStringExtra("itemPrice");
            // Display item name and price in the activity
            TextView itemNameTextView = findViewById(R.id.itemNameTextView);
            TextView itemPriceTextView = findViewById(R.id.itemPriceTextView);
            itemNameTextView.setText(itemName);
            itemPriceTextView.setText(itemPrice);
        }

        // Setup button to return to main menu
        Button returnButton = findViewById(R.id.buttonReturn);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainActivity
                Intent returnIntent = new Intent(ItemDescriptionActivity.this, MainActivity.class);
                startActivity(returnIntent);
                finish(); // Optional: Finish the ItemDescriptionActivity to prevent it from being in the back stack
            }
        });
    }
}
