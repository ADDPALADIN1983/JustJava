package com.example.alangregos.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int numberOfCoffees = 0;
    int basePricePerCupOfCoffee = 5;
    boolean whippedCream = false;
//    boolean chocolate = false;
    int priceOfWhippedCream = 1;
    int priceOfChocolate = 2;
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        name = nameField.getText().toString();
        hasWhippedCream();
        hasChocolate();
        displayPrice(numberOfCoffees);
        String priceMessage = createOrderSummary(numberOfCoffees);
        displayMessage(priceMessage);
    }

    //Is the Whipped Cream check box selected
    private void hasWhippedCream() {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        whippedCream = whippedCreamCheckBox.isChecked();
    }

    //Is the chocolate check box selected
    private boolean hasChocolate() {
        boolean chocolate = false;
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        chocolate = chocolateCheckBox.isChecked();
        return chocolate;
    }

    /**
     * This method is used to increase quantity.
     */
    public void increment(View view) {
        numberOfCoffees = numberOfCoffees + 1;
        displayQuantity(numberOfCoffees);
    }

    /**
     * This method is used to decrease quantity.
     */
    public void decrement(View view) {
        if (numberOfCoffees > 1) {
            numberOfCoffees = numberOfCoffees - 1;
            displayQuantity(numberOfCoffees);
        } else {
            Toast.makeText(this, "You must order at least 1 cup.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int numberOfCoffees) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(numberOfCoffees * 5));

    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(message);
    }

    private int calculatePrice() {
        int price;
        price = numberOfCoffees * basePricePerCupOfCoffee;

        if (whippedCream == true) {
            price += numberOfCoffees * priceOfWhippedCream;
        }
        if (hasChocolate() == true) {
            price += numberOfCoffees * priceOfChocolate;
        }
        return price;

    }

    private String createOrderSummary(int quantity) {
        String message = "Name: " + name;
        if (whippedCream == true) {
            message += "\nWith whipped cream.";
        }
        if (hasChocolate() == true) {
            message += "\nWith chocolate.";
        }
        message += "\nQuantity: " + quantity + "\nTotal: $ " + calculatePrice() + "\nThank You!";

        return message;
}
}
