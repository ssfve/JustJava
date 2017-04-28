package com.example.android.justjava;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava; 
 */

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */


    int numberOfCoffees = 2;
    boolean hasWhippedCream = false;
    boolean hasChocolate = false;

    public void submitOrder(View view) {

        String customer_name = getTextFromEditText();
        display(numberOfCoffees);
        if (customer_name.equals("")){
            customer_name = getResources().getString(R.string.default_name);
        }

        createOrderSummary(customer_name, numberOfCoffees);
        //displayPrice(calculatePrice(numberOfCoffees));
        //displayMessage(message);
    }

    public void onCheckboxClickedWhippedCream(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.checkBoxWhippedCream);
        // Is the view now checked?
        hasWhippedCream = whippedCreamCheckBox.isChecked();
    }

    public void onCheckboxClickedChocolate(View view) {
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.checkBoxChocolate);
        // Is the view now checked?
        hasChocolate = chocolateCheckBox.isChecked();
    }

    public String getTextFromEditText() {
        EditText editText = (EditText) findViewById(R.id.editText);
        return editText.getText().toString();
    }
    /**
     * Create summary of the order.
     *
     * @return text summary
     */
    private String createOrderSummary(String customer_name, int numberOfCoffees){

        String text_hint = getResources().getString(R.string.text_hint);
        String toppings_1 = getResources().getString(R.string.toppings_1);
        String toppings_2 = getResources().getString(R.string.toppings_2);
        String quantity_title = getResources().getString(R.string.quantity_title);
        String total_title = getResources().getString(R.string.total_title);
        String thank_you = getResources().getString(R.string.thank_you);
        String addCreamText = "";
        String addChocolateText = "";

        if (hasWhippedCream){
            addCreamText = getResources().getString(R.string.add_text);
        }else{
            addCreamText = getResources().getString(R.string.not_add_text);
        }
        if (hasChocolate){
            addChocolateText = getResources().getString(R.string.add_text);
        }else{
            addChocolateText = getResources().getString(R.string.not_add_text);
        }
        String message = text_hint + ": " + customer_name;
        message += "\n" + toppings_1 + "? " + addCreamText;
        message += "\n" + toppings_2 + "? " + addChocolateText;
        message += "\n" + quantity_title + ": " + numberOfCoffees;
        message += "\n" + total_title + ": " + NumberFormat.getCurrencyInstance().format(calculatePrice(numberOfCoffees));
        message += "\n" + thank_you + "!";

//        Uri geoLocation = Uri.parse("geo:47.6,-122.3");
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(geoLocation);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }

        String email_subject = getResources().getString(R.string.email_subject);
        String client_chooser_pop = getResources().getString(R.string.client_chooser_pop);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        String[] tos={"ssfvessfve@163.com"};
        String[] ccs={""};
        intent.putExtra(Intent.EXTRA_EMAIL, tos);
        intent.putExtra(Intent.EXTRA_CC, ccs);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.putExtra(Intent.EXTRA_SUBJECT, email_subject);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, client_chooser_pop));

        //return;
        return message;

    }


    public void Increment(View view) {
        String top_check = getResources().getString(R.string.top_check);

        if (numberOfCoffees == 100) {
            // Show an error message as a toast
            Toast.makeText(this, top_check, Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        numberOfCoffees += 1;
        display(numberOfCoffees);
        //displayPrice(calculatePrice(numberOfCoffees));
    }

    public void Decrement(View view) {
        String bottom_check = getResources().getString(R.string.bottom_check);

        if (numberOfCoffees == 1) {
            // Show an error message as a toast
            Toast.makeText(this, bottom_check, Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        numberOfCoffees -= 1;
        display(numberOfCoffees);
        //displayPrice(calculatePrice(numberOfCoffees));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        //TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        //priceTextView.setText(message);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        //TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        //priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(int quantity) {
        int basePrice = 5;
        if (hasWhippedCream) {
            basePrice += 1;
        }
        if (hasChocolate) {
            basePrice += 2;
        }
        int price = quantity * basePrice;
        return price;
    }

}