
/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.deexi.justjava;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deexi.justjava.R;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int numberOfCoffee = 0;
    boolean chocolateStatus = false;
    boolean whippedCreamStatus = false;
    double total = 0.0;
    String name = null;
    String outputText = new String ();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView t = new TextView(this);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder1(View view) {

        display(numberOfCoffee);
        total = numberOfCoffee*10;
        double chocoPrice = 0;
        double whipPrice = 0;

        if(chocolateStatus){
            chocoPrice+= 2.5*numberOfCoffee;
        }
        if(whippedCreamStatus){
            whipPrice+=3.5*numberOfCoffee;
        }
        getPrice(total+chocoPrice+whipPrice);

    }
    public String nameInput(){
        EditText enteredName = (EditText) findViewById(R.id.name_id);
        name = enteredName.getText().toString();
        return name;
    }
    public void getPrice(double number) {
        TextView price = (TextView) findViewById(R.id.price_text_view);
        String symbol = NumberFormat.getCurrencyInstance().format(number);
        if(whippedCreamStatus && chocolateStatus){
            outputText =nameInput()+ getString(R.string.total)+" = " + symbol +"\n"+getString(R.string.thank_you)+" :)"+"\n"+ getString(R.string.with_cream_chocolate);
        }else if(whippedCreamStatus && !(chocolateStatus)){
            outputText = nameInput()+getString(R.string.total) +"= " + NumberFormat.getCurrencyInstance().format(number)+"\n"+getString(R.string.thank_you)+" :)"+"\n "+getString(R.string.enjoy_with_cream);
        }else if(!(whippedCreamStatus) && chocolateStatus){
            outputText = nameInput()+ getString(R.string.total)+" = " + NumberFormat.getCurrencyInstance().format(number)+"\n"+getString(R.string.thank_you)+" :)"+"\n"+getString(R.string.with_chocolate);
        }else {
            outputText = nameInput() +getString(R.string.total)+" = " + NumberFormat.getCurrencyInstance().format(number)+"\n"+getString(R.string.thank_you)+" :)"+"\n"+ getString(R.string.enjoy_your_coffee);
        }
        price.setText(outputText);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    private void display(String number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    public void addCoffee(View v1) {

        TextView moreCoffee = (TextView) findViewById(R.id.quantity_text_view);
        numberOfCoffee = Integer.parseInt(moreCoffee.getText().toString());
        if (numberOfCoffee>=10 ) {
            displayWrongEntry();}
            else {
            numberOfCoffee++;
            display(numberOfCoffee);
        }
    }

    public void subCoffee(View v2) {
        TextView moreCoffee = (TextView) findViewById(R.id.quantity_text_view);
        numberOfCoffee = Integer.parseInt(moreCoffee.getText().toString());

        if (numberOfCoffee <= 0 ) {
            displayWrongEntry();
        } else {
            numberOfCoffee--;
            display(numberOfCoffee);
        }


    }

    public void displayWrongEntry() {
        Context context = getApplicationContext();
        CharSequence text ;
        if(numberOfCoffee>=10){
        text = getString(R.string.less_than_zero);
        }
        else {
             text = getString(R.string.more_than_ten);
        }
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    public void withChocolate(View v){
        CheckBox chocostatus = (CheckBox) findViewById(R.id.chocolate_checkbox);
        chocolateStatus = chocostatus.isChecked();
        //display("With chocolate");
        imageForDisplay();
    }
    public void withWhippedCream(View v){
        CheckBox whippedStatus = (CheckBox) findViewById(R.id.whippedCream_checkBox);
        whippedCreamStatus = whippedStatus.isChecked();
        //display("With Whipped Cream");
        imageForDisplay();
    }

    public void mailIntent(View view){
        Intent sampleMailIntent = new Intent(Intent.ACTION_SENDTO);
        sampleMailIntent.setData(Uri.parse("mailto:"));
        sampleMailIntent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.more_than_ten));
        sampleMailIntent.putExtra(Intent.EXTRA_TEXT,outputText);
        if(sampleMailIntent.resolveActivity(getPackageManager())!= null){
            startActivity(sampleMailIntent);
        }
    }
    public void imageForDisplay(){
        ImageView coffeeImage = findViewById(R.id.output_image);
        if(whippedCreamStatus && chocolateStatus){
            coffeeImage.setImageResource(R.mipmap.withcreamandchocolate);
        }else if(whippedCreamStatus){
            coffeeImage.setImageResource(R.mipmap.withcreamonly);
        }else if(chocolateStatus){
            coffeeImage.setImageResource(R.mipmap.withchocolateonly);
        }else {
            coffeeImage.setImageResource(R.mipmap.onlycoffee);
        }

    }
}