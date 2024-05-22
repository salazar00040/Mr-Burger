package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainPageActivity extends AppCompatActivity {

    private String selectedCategory = "Hamburger";

    private TextView textViewClientName;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button categoryHamburger = findViewById(R.id.categoryHamburger);
        Button categoryLamb = findViewById(R.id.categoryLamb);
        Button categoryCheese = findViewById(R.id.categoryCheese);
        Button categoryPepperoni = findViewById(R.id.categoryPepperoni);
        Button detailsButton = findViewById(R.id.detailsButton);

        categoryHamburger.setOnClickListener(view -> updateContent("Hamburger"));
        categoryLamb.setOnClickListener(view -> updateContent("Lamb"));
        categoryCheese.setOnClickListener(view -> updateContent("Cheese"));
        categoryPepperoni.setOnClickListener(view -> updateContent("Pepperoni"));
        detailsButton.setOnClickListener(view -> goToDetails(selectedCategory));

        textViewClientName = findViewById(R.id.clientName);
        db = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            db.collection("users").document(currentUser.getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String name = document.getString("name");
                                textViewClientName.setText(name);
                            }
                        }
                    });
        }
    }

    private void updateContent(String category) {
        selectedCategory = category;
        ImageView subCategoryImage = findViewById(R.id.subCategoryImage);
        TextView subCategoryTitle = findViewById(R.id.subCategoryTitle);
        TextView subCategoryPrice = findViewById(R.id.subCategoryPrice);

        switch (category) {
            case "Hamburger":
                subCategoryImage.setImageResource(R.drawable.burger1);
                subCategoryTitle.setText("Hamburger");
                subCategoryPrice.setText("Starting from 12.50 $");
                break;
            case "Lamb":
                subCategoryImage.setImageResource(R.drawable.burger2);
                subCategoryTitle.setText("Lamb Burger");
                subCategoryPrice.setText("Starting from 15.25 $");
                break;
            case "Cheese":
                subCategoryImage.setImageResource(R.drawable.burger3);
                subCategoryTitle.setText("Cheese Burger");
                subCategoryPrice.setText("Starting from 10.25 $");
                break;
            case "Pepperoni":
                subCategoryImage.setImageResource(R.drawable.burger4);
                subCategoryTitle.setText("Pepperoni Burger");
                subCategoryPrice.setText("Starting from 16.25 $");
                break;
        }
    }

    private void goToDetails(String category) {
        Intent intent = new Intent(this, ItemDescriptionActivity.class);
        intent.putExtra("itemName", category + " Burger");
        intent.putExtra("itemPrice", "Starting from " + getPrice(category));
        intent.putExtra("itemDescription", getDescription(category));
        startActivity(intent);
    }

    private String getPrice(String category) {
        switch (category) {
            case "Hamburger":
                return "12.50 $";
            case "Lamb":
                return "15.25 $";
            case "Cheese":
                return "10.25 $";
            case "Pepperoni":
                return "16.25 $";
            default:
                return "0.00 $";
        }
    }

    private String getDescription(String category) {
        switch (category) {
            case "Hamburger":
                return "Patty of ground beef, Two halves of a bun, Slices of raw onion, lettuce, Mayonnaise, bacon, Salad, and other ingredients and flavor can be added on request.";
            case "Lamb":
                return "Patty of ground lamb, Two halves of a bun, Slices of raw onion, lettuce, Mayonnaise, bacon, Salad, and other ingredients and flavor can be added on request.";
            case "Cheese":
                return "Patty of ground beef, Two halves of a bun, Slices of raw onion, lettuce, Mayonnaise, cheese, Salad, and other ingredients and flavor can be added on request.";
            case "Pepperoni":
                return "Patty of ground beef, Two halves of a bun, Slices of raw onion, lettuce, Mayonnaise, pepperoni, Salad, and other ingredients and flavor can be added on request.";
            default:
                return "No description available.";
        }
    }
}
