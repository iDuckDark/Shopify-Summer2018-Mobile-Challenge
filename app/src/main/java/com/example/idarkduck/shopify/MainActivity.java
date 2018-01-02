package com.example.idarkduck.shopify;

import android.app.DownloadManager;
import android.app.SearchManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    OkHttpClient client;
    String bodyResponse;
    String address = "https://shopicruit.myshopify.com/admin/products.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6";
    boolean searching = false;
    TextView test;
    ArrayList<Product> products;
    ArrayList<Product> originalProducts;
    ProductAdapter productAdapter;
    ProgressBar progressBar;
    ImageView testImage;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //option 2
        bodyResponse = "";
        client =  new OkHttpClient();

        //Before accessing the internet on a mobile phone
        //You need permission to use the hardware to do that
        DownloadFilesTask download = new DownloadFilesTask();
        download.execute(address);

        //Toast.makeText(getApplicationContext(),bodyResponse,Toast.LENGTH_LONG).show();
        test = findViewById(R.id.testTextView);
        //test.setText(bodyResponse);
        test.setMovementMethod(new ScrollingMovementMethod());
        products = new ArrayList<>();
        progressBar = findViewById(R.id.progressBar);
        testImage = findViewById(R.id.imageViewTest);
    }
    @Override
    public void onBackPressed() {
        if (searching) {
            // Clear search.
            //backSearch.setQuery("", true);
            resetProductAdapter();
        }
        //optional check to prevent users from exit app if there is info
        //&& backSearch.getQuery().toString().equals("")
        if(backSearch !=null ){
            if(backSearch.getQuery().toString().equals("")){
                super.onBackPressed();
            }
            else{
                backSearch.setQuery("", true);
            }
            //super.onBackPressed();
            //Toast.makeText(getApplicationContext(),"back search",Toast.LENGTH_LONG).show();
        }
        else if(!searching){
            super.onBackPressed();
        }
    }
    SearchView backSearch;
    //For searching Products
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();
        backSearch = (SearchView) item.getActionView();
        //
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //
        //searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchProductAdapter(query);
                //Toast.makeText(getApplicationContext(),query,Toast.LENGTH_LONG).show();
                //adapter.filter(query);
                //adapter.getFilter().filter(query);
                //setProductAdapter(adapter.getProducts());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(getApplicationContext(),newText,Toast.LENGTH_LONG).show();
                searchProductAdapter(newText);

                if (newText.isEmpty())
                    searching = false;
                else
                    searching = true;

                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //Toast.makeText(MainActivity.this, "close", Toast.LENGTH_SHORT).show();
                //backSearch.setQuery("", true);
                resetProductAdapter();
                //setProductAdapter();
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    //======== END ====================================For searching Products
    private void searchProductAdapter(String name) {
        if(name.equals("")){
            return;
        }
        products = new ArrayList<>();
        for(int i=0; i < originalProducts.size();i++){
            if (originalProducts.get(i).getProductName().toLowerCase().contains(name.toString().toLowerCase())){
                //Toast.makeText(getApplicationContext(),Integer.toString(i),Toast.LENGTH_LONG).show();
                products.add(originalProducts.get(i));
            }
        }
        //init adapters again
        setProductAdapter(products);
    }

    private void resetProductAdapter() {
        products = new ArrayList<>(originalProducts);
        //init adapters
        adapter = new ProductAdapter(this, products);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lstItems);
        listView.setAdapter(adapter);

    }

    private void setProductAdapter() {
        //init adapters
        adapter = new ProductAdapter(this, products);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lstItems);
        listView.setAdapter(adapter);
    }
    private void setProductAdapter(ArrayList<Product> p) {
        //init adapters
        adapter = new ProductAdapter(this, p);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lstItems);
        listView.setAdapter(adapter);
    }


    private void readJson() {
        try {
            JSONObject jsonObj = new JSONObject(bodyResponse);
            //String title = (String) jsonObj.get("title");
            JSONArray arr = jsonObj.getJSONArray("products");
            //String title ="";
            for (int i = 0; i < arr.length(); i++) {
                //create entire object of JSON file at index i
                JSONObject o = arr.getJSONObject(i);
                //find the keys associated
                String ID = o.getString("id");
                String productName = o.getString("title");
                String htmlBody = o.getString("body_html");
                String vendor = o.getString("vendor");
                String productType = o.getString("product_type");
                String createdAt = o.getString("created_at");
                String handle = o.getString("handle");
                String updatedAt = o.getString("updated_at");
                String publishedAt = o.getString("published_at");
                String templateSuffix = o.getString("template_suffix");
                String publishedScope = o.getString("published_scope");
                String tags = o.getString("tags");

                String image = o.getString("image");
                JSONObject imageObject = new JSONObject(image);
                String imageURL = imageObject.getString("src");

                //make a new product using setters and add it to the array
                Product newProduct = new Product(ID, productName);
                newProduct.setHtmlBody(htmlBody);
                newProduct.setVendor(vendor);
                newProduct.setProductType(productType);
                newProduct.setCreatedAt(createdAt);
                newProduct.setHandle(handle);
                newProduct.setUpdatedAt(updatedAt);
                newProduct.setPublishedAt(publishedAt);
                newProduct.setTemplateSuffix(templateSuffix);
                newProduct.setPublishedScope(publishedScope);
                newProduct.setTags(tags);
                newProduct.setImage(imageURL);

                JSONArray variants = o.getJSONArray("variants");

                //find all variants of current product
                for (int j = 0; j < variants.length(); j++) {
                    JSONObject variant = variants.getJSONObject(j);

                    //find associated keys
                    String variantID = variant.getString("id");
                    String variantName = variant.getString("title");
                    String price = variant.getString("price");
                    String sku = variant.getString("sku");
                    int position = variant.getInt("position");
                    String inventoryPolicy = variant.getString("inventory_policy");
                    String compareAtPrice = variant.getString("compare_at_price");
                    String fulfillmentService = variant.getString("fulfillment_service");
                    String inventoryManagement = variant.getString("inventory_management");
                    String option1 = variant.getString("option1");
                    String option2 = variant.getString("option2");
                    String option3 = variant.getString("option3");
                    String variantCreatedAt = variant.getString("created_at");
                    String variantUpdatedAt = variant.getString("updated_at");
                    boolean taxable = variant.getBoolean("taxable");
                    String barcode = variant.getString("barcode");
                    int grams = variant.getInt("grams");
                    String imageID = variant.getString("image_id");
                    int inventoryQuantity = variant.getInt("inventory_quantity");
                    double weight = variant.getDouble("weight");
                    String weightUnit = variant.getString("weight_unit");
                    String inventoryItemID = variant.getString("inventory_item_id");
                    int oldInventoryQuantity = variant.getInt("old_inventory_quantity");
                    boolean requiresShipping = variant.getBoolean("requires_shipping");

                    //create variant product
                    ProductVariant var = new ProductVariant(variantID, ID, variantName);

                    var.setPrice(price);
                    var.setSku(sku);
                    var.setPosition(position);
                    var.setInventoryPolicy(inventoryPolicy);
                    var.setCompareAtPrice(compareAtPrice);
                    var.setFulfillmentService(fulfillmentService);
                    var.setInventoryManagement(inventoryManagement);
                    var.setOption1(option1);
                    var.setOption2(option2);
                    var.setOption3(option3);
                    var.setCreatedAt(variantCreatedAt);
                    var.setUpdatedAt(variantUpdatedAt);
                    var.setTaxable(taxable);
                    var.setBarcode(barcode);
                    var.setGrams(grams);
                    var.setImageID(imageID);
                    var.setInventoryQuantity(inventoryQuantity);
                    var.setWeight(weight);
                    var.setWeightUnit(weightUnit);
                    var.setInventoryItemID(inventoryItemID);
                    var.setOldInventoryQuantity(oldInventoryQuantity);
                    var.setRequiresShipping(requiresShipping);

                    //add variant to main product
                    newProduct.addVariant(var);
                }
                products.add(newProduct);
                //title += key + " NEXT  ";
            }
            //test.setText(title);
            originalProducts = new ArrayList<>(products);
            //productAdapter = new ProductAdapter(getApplicationContext(), products);

            //
            ListView lstItems = (ListView) findViewById(R.id.lstItems);
            lstItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent viewProduct = new Intent(getApplicationContext(), ProductViewActivity.class);
                    viewProduct.putExtra("product", products.get(i));
                    startActivity(viewProduct);
                }
            });

            //============DO NOT REMOVE TESTING PURPOSES ONLY====================
            //test.setText("Success");
            //test.setText(products.get(0).toString());
            //test.setText(Integer.toString(products.size()));  //test size 50
            //test.setTextSize(18);
            //Picasso.with(getApplicationContext()).load(products.get(0).getImage()).into(testImage);
            //======================END================================
        } catch (JSONException e) {
            e.printStackTrace();
            test.setText(" Please contact developer. Failed Error: " + e);
        }
    }

    private void disableTest() {
        test.setVisibility(View.GONE);
        testImage.setVisibility(View.GONE);
    }

    private class DownloadFilesTask extends AsyncTask<String, Integer, String> {

        protected String doInBackground(String... urls) {
            Downloader d = new Downloader(address);
            //added for progress bar
            for (int i = 0; i < 100; i++) {
                publishProgress(i);
            }
            //
            bodyResponse = d.getBodyRespose();
            return d.getBodyRespose();
        }

        //Maybe need to be implemented to avoid crashes
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            test.setText(" Loading ... ");
            progressBar.setProgress(progress[0]);
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //showDialog("Downloaded " + result + " bytes");
            if (result != null) {
                test.setText(result);
                readJson();
                disableTest();
                setProductAdapter();
                progressBar.setVisibility(View.GONE);
            }
        }

        //not needed actually but who knows
        protected void onPreExcecute() {
            super.onPreExecute();
            progressBar.setMax(100);
        }
    }
}