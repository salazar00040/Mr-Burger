package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ItemDescriptionActivity extends AppCompatActivity {

    private int quantity = 1;
    private ArrayList<String> cartItems = new ArrayList<>();
    private double itemBasePrice;
    private TextView textQuantity;
    private TextView totalPriceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);

        // Get the passed data from the intent
        Intent intent = getIntent();
        String itemName = intent.getStringExtra("itemName");
        String itemPrice = intent.getStringExtra("itemPrice");
        String itemDescription = intent.getStringExtra("itemDescription");

        // Parse the item price to get the base price
        itemBasePrice = Double.parseDouble(itemPrice.replaceAll("[^\\d.]", ""));

        // Set the data to the views
        TextView itemTitle = findViewById(R.id.itemTitle);
        TextView itemPriceView = findViewById(R.id.itemPrice);
        TextView itemDescriptionView = findViewById(R.id.itemDescription);
        totalPriceView = findViewById(R.id.totalPrice);
        textQuantity = findViewById(R.id.textQuantity);
        itemTitle.setText(itemName);
        itemPriceView.setText("Starting from " + itemPrice);
        itemDescriptionView.setText(itemDescription);
        updateTotalPrice();

        // Back to main menu button
        Button buttonBackToMain = findViewById(R.id.buttonBackToMain);
        buttonBackToMain.setOnClickListener(v -> {
            Intent backToMainIntent = new Intent(ItemDescriptionActivity.this, MainPageActivity.class);
            startActivity(backToMainIntent);
            finish();
        });

        // Quantity buttons
        Button increaseQuantity = findViewById(R.id.increaseQuantity);
        Button decreaseQuantity = findViewById(R.id.decreaseQuantity);

        increaseQuantity.setOnClickListener(v -> {
            quantity++;
            textQuantity.setText(String.valueOf(quantity));
            updateTotalPrice();
        });

        decreaseQuantity.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                textQuantity.setText(String.valueOf(quantity));
                updateTotalPrice();
            }
        });

        // Add to cart button
        Button buttonAddToCart = findViewById(R.id.buttonAddToCart);
        buttonAddToCart.setOnClickListener(v -> {
            String cartItem = itemName + " x" + quantity + " - Total: " + totalPriceView.getText().toString();
            cartItems.add(cartItem);

            Intent cartIntent = new Intent(ItemDescriptionActivity.this, CartActivity.class);
            cartIntent.putStringArrayListExtra("cartItems", cartItems);
            startActivity(cartIntent);
        });
    }

    private void updateTotalPrice() {
        double totalPrice = itemBasePrice * quantity;
        totalPriceView.setText("Total: " + String.format("%.2f", totalPrice) + " $");
    }
}
