package com.example.alangregos.justjava;

import android.content.Intent;
import android.net.Uri;
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

    int numberOfCoffees = 1;
    int basePricePerCupOfCoffee = 5;
    int priceOfWhippedCream = 1;
    int priceOfChocolate = 2;
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__main);
    }

    /**
     * This method is called when the order button is clicked and displays content to the apps screen.
     */
    public void submitOrder(View view) {
        EditText nameField = findViewById(R.id.name_field);
        name = nameField.getText().toString();
        hasWhippedCream();
        hasChocolate();
//        displayPrice(numberOfCoffees);
        String priceMessage = createOrderSummary();
//        displayMessage(priceMessage);  // commetted out after testing to implement push to email.


        /*
        Code to push order out to an email system. EXTRA_SUBJECT is the subject line and EXTRA_TEXT is the body of the email
        Commented out for testing because the virtual device doesn't have a functional email system to use in testing.
         */
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getText(R.string.order_summary_email_subject) + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    //Is the Whipped Cream check box selected
    private boolean hasWhippedCream() {
        boolean whippedCream;
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_check_box);
        whippedCream = whippedCreamCheckBox.isChecked();
        return whippedCream;
    }

    //Is the chocolate check box selected
    private boolean hasChocolate() {
        boolean chocolate;
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_check_box);
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
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int numberOfCoffees) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(numberOfCoffees * basePricePerCupOfCoffee));

    }

    /**
     * This method displays the given text on the screen. Used for virtual testing.
     */
    private void displayMessage(String message) {
        TextView priceTextView = findViewById(R.id.order_summary_text_view);
        priceTextView.setText(message);
    }

    private int calculatePrice() {
        int price;
        price = numberOfCoffees * basePricePerCupOfCoffee;

        if (hasWhippedCream()) {
            price += numberOfCoffees * priceOfWhippedCream;
        }
        if (hasChocolate()) {
            price += numberOfCoffees * priceOfChocolate;
        }
        return price;

    }

    private String createOrderSummary() {
        String message = getString(R.string.order_summary_name) + " " + name;
        if (hasWhippedCream()) {
            message += "\n" + getString(R.string.order_summary_whipped_cream);
        }
        if (hasChocolate()) {
            message += "\n" + getString(R.string.order_summary_chocolate);
        }
        message += "\n" + getString(R.string.order_summary_quantity) + " " + numberOfCoffees + "\n"
                + getString(R.string.order_summary_price) + " " + NumberFormat.getCurrencyInstance().format(calculatePrice())
                + "\n" + getString(R.string.thank_you);

        return message;
    }
}
