package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMenu;
    private MenuAdapter menuAdapter;
    private TextView textViewClientName;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewMenu = findViewById(R.id.recyclerViewMenu);
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));

        textViewClientName = findViewById(R.id.textViewClientName);
        db = FirebaseFirestore.getInstance();

        // Get the current user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Get the user's document from Firestore
            db.collection("users").document(currentUser.getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Get the name from the document
                                String name = document.getString("name");
                                // Set the client's name to the TextView
                                textViewClientName.setText("Welcome, " + name);
                            }
                        }
                    });
        }

        List<MenuItem> menuItems = getMenuItems();

        menuAdapter = new MenuAdapter(menuItems);
        recyclerViewMenu.setAdapter(menuAdapter);

        // Handle item click
        menuAdapter.setOnItemClickListener(item -> {
            Intent intent = new Intent(MainActivity.this, ItemDescriptionActivity.class);
            intent.putExtra("itemName", item.getName());
            intent.putExtra("itemIngredients", item.getIngredients());
            intent.putExtra("itemPrice", item.getPrice());
            startActivity(intent);
        });
    }

    public void openProfilePage(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private List<MenuItem> getMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        // Updated menu items with ingredients
        menuItems.add(new MenuItem("Hamburger", "Tomatoes, Mozzarella", "$10"));
        menuItems.add(new MenuItem("Lamburger", "Tomatoes, Lamb", "$12"));
        menuItems.add(new MenuItem("Cheeseburger", "Cheese, Vegetables", "$9"));
        menuItems.add(new MenuItem("Classic", "Cheese, Hamburger", "$10"));
        return menuItems;
    }
}
