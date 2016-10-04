package com.example.android.justjava;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.jar.Attributes;

import static android.icu.text.NumberFormat.getCurrencyInstance;

public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //This method is called when the order button is clicked
    public void submitOrder(View view)
    {
        EditText nameField = (EditText) findViewById(R.id.editName);
        String name = nameField.getText().toString();



        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream =  whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate =  chocolateCheckBox.isChecked();

        //Log.v("MainActivity", "Has whipped cream?" + hasWhippedCream);
        int price = calculatePrice(hasWhippedCream,hasChocolate);
        //Log.v("MainActivity", "The price is " + price);
        String priceMessage = createOrderSummary(name,price,hasWhippedCream,hasChocolate);
        //priceMessage = priceMessage + "\nThank you";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto"));//only emails should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT , "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if(intent.resolveActivity(getPackageManager()) != null )
        {
            startActivity(intent);
        }

        //displayMessage(priceMessage);
        //displayPrice(quantity*5);
    }

    /**
     * Calculates the price of the order
     * addWhippedCream is whether or not the user wants whipped cream topping
     * addChocolate is whether or not the user wants chocolate topping
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream,boolean addChocolate)
    {
        int basePrice = 5;

        if(addWhippedCream)
        {
            basePrice = basePrice + 1;
        }
        if(addChocolate)
        {
            basePrice = basePrice + 2;
        }

       // int price = quantity * 5;
        return quantity * basePrice;
    }

    private String createOrderSummary(String name,int price,boolean addWhippedCream,boolean addChocolate)
    {
        String priceMessage = "Name : " + name;
        priceMessage+= "\nAdd whipped cream? " + addWhippedCream;
        priceMessage+= "\nAdd chocolate? " + addChocolate;
        priceMessage = priceMessage + "\nQuantity "+ quantity;
        priceMessage = priceMessage + "\nTotal:$ " + price;
        priceMessage = priceMessage + "\nThank you! ";
        return priceMessage;
    }
    //This method displays the quantity value on the screen
    private void display(int number)
    {
        TextView quantity_text = (TextView) findViewById(R.id.quantity_text_view);
        quantity_text.setText("" + number);
    }


    //This method displays the given quantity value on the screen
    /**private void displayPrice(int number)
    {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(getCurrencyInstance().format(number));
    }

     */

    //This method is called when the plus button is clicked
    public void increment(View view)
    {

        if(quantity == 100)
        {
            //Show an error message as a toast
            Toast.makeText(MainActivity.this,"You cannot have more than 100 coffees",Toast.LENGTH_LONG).show();
            //Exist this method early because there's nothing left to do
            return;
        }
        quantity = quantity +1;
        display(quantity);
    }


    //This method is called when the minus button is clicked
    public void decrement(View view)
    {

        if(quantity  == 1)
        {
            Toast.makeText(MainActivity.this,"You cannot have less than 1 coffee",Toast.LENGTH_LONG).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }
}
