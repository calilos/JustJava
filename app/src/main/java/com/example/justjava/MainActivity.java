/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100){
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1){
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 2 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


        displayMessage(priceMessage);
    }

    /**
     * Calculates the price of the order
     *
     * @param addWhippedCream is whether or not the user want whipped cream topping
     * @param addChocolate is whether or not the user want chocolate topping
     * @return total price
     *
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // Price of 1 cup of coffe
        int basePrice = 5;

        // Add $1 if the user wants whipped cream
        if (addWhippedCream){
            basePrice = basePrice + 1;
        }

        // Add $2 if the user wants chocolate
        if (addChocolate){
            basePrice = basePrice + 2;
        }

        // Calculate the total order price by multiplying by quantity
        return quantity * basePrice;

    }
    /**
     * Create summary of the order
     * @param price of the order
     * @param addWhippedCream is whether or not the user want whipped cream topping
     * @param addChocolate is whether or not the user want chocolate topping
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    public void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * Creates a custom greeting message based on the name
     *
     * @return text greeting
     */
    private String createCustomGreeting(String firstName, String lastName) {
        return "Welcome, " + firstName + "" + lastName + "!";
    }

    private String createCalendarEventReminder(String eventName, String location, int minutesAway) {
        String reminder = "You hace an upcoming event in " + minutesAway + " minutes.";
        reminder = reminder + " It is " + eventName + " held at " + location + ".";
        return reminder;
    }

    private int deductPoints(int pointsUsed) {
        // Everyone starts with 100 points
        int numberOfPoints = 100 - pointsUsed;
        return numberOfPoints;
    }

    private String findTotalTripLength(int distanceOfFirstTrip, int distanceOfSecondTrip, int distanceOfThirdTrip) {
        // Assume we need 2 miles to go to our friend's home (where the trip will star)
        int totalLength = 2;

        // Then start adding in the trip length.
        totalLength = totalLength + distanceOfFirstTrip + distanceOfSecondTrip + distanceOfThirdTrip;

        //From a string to display the total trip length
        String message = "The total trip will be: " + totalLength + " miles.";
        return message;
    }
}