package com.example.idarkduck.shopify;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by david on 2017-12-28.
 */

/**
 * Created by iDarkDuck on 2018-1-1.
 */

public class ProductAdapter extends ArrayAdapter<Product> implements Filterable{

    private final Context context;
    private ArrayList<Product> products;
    //for search
    private ArrayList<Product> filteredList;
    private FriendFilter friendFilter;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        super(context, R.layout.product_adapter_layout, products);
        this.context = context;
        this.products = products;
        this.filteredList=products;
    }

    public ArrayList<Product> getFilteredList(){
        return filteredList;
    }
    public ArrayList<Product> getProducts(){
        return products;
    }
    public ArrayList<Product> getSearchedProducts(){
        return searchedProducts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //reference
        //final ViewHolder holder;
        // Get the data item for this position
        Product product = getItem(position);
        //filteredList.add(product.getProductName());
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_adapter_layout, parent, false);
            //reference used
//            holder = new ViewHolder();
//            holder.name = (TextView) convertView.findViewById(R.id.txtName);
//            holder.description = (TextView) convertView.findViewById(R.id.txtDescription);
//            holder.pictureHolder= convertView.findViewById(R.id.imgProduct);
//            convertView.setTag(holder);
        }
//        else {
//            // get view holder back
//            holder = (ViewHolder) convertView.getTag();
//        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.txtName);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.txtDescription);
        ImageView picture = convertView.findViewById(R.id.imgProduct);
        // Populate the data into the template view using the data object
        tvName.setText(product.getProductName());
        tvDescription.setText(product.getHtmlBody());

        Picasso.with(this.context).load(product.getImage()).into(picture);

        //added to search the product names?
        getFilter();

        // Return the completed view to render on screen
        return convertView;
    }

    //http://www.androidbegin.com/tutorial/android-search-listview-using-filter/
    // Filter Class ============================================================================
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        filteredList.clear();
        if (charText.length() == 0) {
            filteredList.addAll(products);
        }
        else {
            for (Product p : products) {
                if (p.getProductName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    filteredList.add(p);
                }
            }
        }
        notifyDataSetChanged();
    }
    //============================================================================================
    //reference
    //https://stackoverflow.com/questions/43426987/searching-of-listview-item-on-action-bar

    static class ViewHolder {
        TextView description;
        TextView name;
        ImageView pictureHolder;
    }
    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Product getItem(int i) {
        return filteredList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public Filter getFilter() {
        if (friendFilter == null) {
            friendFilter = new FriendFilter();
        }

        return friendFilter;
    }
    private ArrayList<Product> searchedProducts =new ArrayList<>();
    private class FriendFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<Product> tempList = new ArrayList<Product>();

                // search content in friend list
                for (Product p : products) {
                    if (p.getProductName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(p);
                        searchedProducts.add(p);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = products.size();
                filterResults.values = products;
            }

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<Product>) results.values;
            notifyDataSetChanged();
        }
    }
}
