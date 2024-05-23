package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Get the cart items from the intent
        Intent intent = getIntent();
        ArrayList<String> cartItems = intent.getStringArrayListExtra("cartItems");

        // Get the cart items container
        LinearLayout cartItemsContainer = findViewById(R.id.cartItemsContainer);

        // Add each item to the container
        for (String item : cartItems) {
            TextView itemTextView = new TextView(this);
            itemTextView.setText(item);
            itemTextView.setTextSize(18);
            itemTextView.setPadding(0, 8, 0, 8);
            cartItemsContainer.addView(itemTextView);
        }

        // Handle checkout button click
        Button buttonCheckout = findViewById(R.id.buttonCheckout);
        buttonCheckout.setOnClickListener(v -> {
            // Navigate to the checkout page or show a checkout message
            // For this example, we'll just show a message
            Toast.makeText(CartActivity.this, "Checkout is not implemented.", Toast.LENGTH_SHORT).show();
        });
    }
}
