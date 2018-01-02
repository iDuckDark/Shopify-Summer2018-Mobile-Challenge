package com.example.idarkduck.shopify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class ProductViewActivity extends AppCompatActivity {

    private Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        product = (Product) getIntent().getSerializableExtra("product");

        //remove action bar
        //https://stackoverflow.com/questions/25863676/android-activity-without-actionbar
        // getActionBar not used because crashed > getSupportActionBar
        this.getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        this.getSupportActionBar().hide();

        //Remove title bar =====================
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // ==============end
        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_product_view);

        // set name, description, and ID
        ((TextView) findViewById(R.id.txtName)).setText(product.getProductName());
        ((TextView) findViewById(R.id.txtProductID)).setText("Product ID: " + product.getID());
        ((TextView) findViewById(R.id.txtDescription)).setText(product.getHtmlBody());

        // add different colour options
        Spinner spnColour = (Spinner) findViewById(R.id.spnColours);
        ArrayAdapter<String> clrAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item);

        final ProductVariant[] productVariants = product.getVariants().toArray(new ProductVariant[product.getVariants().size()]);

        for (int i = 0; i < productVariants.length; i++) {
            clrAdapter.add(productVariants[i].getProductName());
        }
        spnColour.setAdapter(clrAdapter);

        spnColour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //this method is used to update the view with product information whenever a different colour is selected
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //update price
                ((TextView) findViewById(R.id.txtPrice)).setText(("$") + productVariants[i].getPrice());

                //update ID
                ((TextView) (findViewById(R.id.txtID))).setText("ID: " + productVariants[i].getID());

                //update stock level
                TextView txtStock = (TextView) findViewById(R.id.txtStockLevel);
                if (productVariants[i].getInventoryQuantity() == 0) {
                    txtStock.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    txtStock.setText("0 currently in stock");
                } else {
                    txtStock.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                    txtStock.setText(productVariants[i].getInventoryQuantity() + " currently in stock");
                }

                //update inventory ID
                ((TextView) findViewById(R.id.txtInventoryID)).setText("Inventory ID: " + productVariants[i].getInventoryItemID());

                //update weight
                ((TextView) findViewById(R.id.txtWeight)).setText("Weight: " + productVariants[i].getWeight() + " " + productVariants[i].getWeightUnit());

                //update taxable
                if (productVariants[i].getTaxable()) {
                    ((TextView) findViewById(R.id.txtTaxable)).setText("Taxable: Yes");
                } else {
                    ((TextView) findViewById(R.id.txtTaxable)).setText(("Taxable: No"));
                }

                //update shipping included
                if (productVariants[i].getRequiresShipping()) {
                    ((TextView) findViewById(R.id.txtShippingIncluded)).setText("Shipping Included: No");
                } else {
                    ((TextView) findViewById(R.id.txtShippingIncluded)).setText("Shipping Included: Yes");
                }

                //update created date
                ((TextView) findViewById(R.id.txtCreatedDate)).setText("Created: " + productVariants[i].getCreatedAt());

                //update last updated
                ((TextView) findViewById(R.id.txtLastUpdated)).setText("Last Updated: " + productVariants[i].getUpdatedAt());
            }

            //not needed for this application
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //add picture
        Picasso.with(getApplicationContext()).load(product.getImage()).into((ImageView) findViewById(R.id.imgProductImage));

    }
}