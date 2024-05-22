package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ItemDescriptionActivity extends AppCompatActivity {

    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);

        // Get the passed data from the intent
        Intent intent = getIntent();
        String itemName = intent.getStringExtra("itemName");
        String itemPrice = intent.getStringExtra("itemPrice");
        String itemDescription = intent.getStringExtra("itemDescription");

        // Set the data to the views
        TextView itemTitle = findViewById(R.id.itemTitle);
        TextView itemPriceView = findViewById(R.id.itemPrice);
        TextView itemDescriptionView = findViewById(R.id.itemDescription);
        itemTitle.setText(itemName);
        itemPriceView.setText(itemPrice);
        itemDescriptionView.setText(itemDescription);

        Button buttonBackToMain = findViewById(R.id.buttonBackToMain);
        buttonBackToMain.setOnClickListener(v -> {
            Intent backToMainIntent = new Intent(ItemDescriptionActivity.this, MainPageActivity.class);
            startActivity(backToMainIntent);
            finish();
        });

        Button buttonIncreaseQuantity = findViewById(R.id.decreaseQuantity);
        Button buttonDecreaseQuantity = findViewById(R.id.increaseQuantity);
        TextView textQuantity = findViewById(R.id.textQuantity);

        buttonIncreaseQuantity.setOnClickListener(v -> {
            quantity++;
            textQuantity.setText(String.valueOf(quantity));
        });

        buttonDecreaseQuantity.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                textQuantity.setText(String.valueOf(quantity));
            }
        });

        Button buttonAddToCart = findViewById(R.id.buttonAddToCart);
        buttonAddToCart.setOnClickListener(v -> {
            // Handle adding to cart
            // You can pass the item details and quantity to the cart activity or database here
        });
    }
}
