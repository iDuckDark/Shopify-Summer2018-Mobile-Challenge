package com.example.idarkduck.shopify;

import java.util.ArrayList;

/**
 * Created by david on 2017-12-31.
 */

public class ProductVariant extends Product {

    private String productID;
    private String price;
    private String sku;
    private int position;
    private String inventoryPolicy;
    private String compareAtPrice;
    private String fulfillmentService;
    private String inventoryManagement;
    private String option1;
    private String option2;
    private String option3;
    private boolean taxable;
    private String barcode;
    private int grams;
    private String imageID;
    private int inventoryQuantity;
    private double weight;
    private String weightUnit;
    private String inventoryItemID;
    private int oldInventoryQuantity;
    private boolean requiresShipping;


    public ProductVariant(String ID, String productID, String productName) {
        super(ID, productName);
        this.productID = productID;
    }

    //getters
    public String getProductID() {
        return productID;
    }

    public String getPrice() {
        return price;
    }

    public String getSku() {
        return sku;
    }

    public int getPosition() {
        return position;
    }

    public String getInventoryPolicy() {
        return inventoryPolicy;
    }

    public String getCompareAtPrice() {
        return compareAtPrice;
    }

    public String getFulfillmentService() {
        return fulfillmentService;
    }

    public String getInventoryManagement() {
        return inventoryManagement;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public boolean getTaxable() {
        return taxable;
    }

    public String getBarcode() {
        return barcode;
    }

    public int getGrams() {
        return grams;
    }

    public String getImageID() {
        return imageID;
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public double getWeight() {
        return weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public String getInventoryItemID() {
        return inventoryItemID;
    }

    public int getOldInventoryQuantity() {
        return oldInventoryQuantity;
    }

    public boolean getRequiresShipping() {
        return requiresShipping;
    }

    //setters
    public void setProductID(String id) {
        productID = id;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setPosition(int pos) {
        position = pos;
    }

    public void setInventoryPolicy(String policy) {
        inventoryPolicy = policy;
    }

    public void setCompareAtPrice(String compareAt) {
        compareAtPrice = compareAt;
    }

    public void setFulfillmentService(String service) {
        fulfillmentService = service;
    }

    public void setInventoryManagement(String management) {
        inventoryManagement = management;
    }

    public void setOption1(String option) {
        option1 = option;
    }

    public void setOption2(String option) {
        option2 = option;
    }

    public void setOption3(String option) {
        option3 = option;
    }

    public void setTaxable(boolean taxable) {
        this.taxable = taxable;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setGrams(int g) {
        grams = g;
    }

    public void setImageID(String ID) {
        imageID = ID;
    }

    public void setInventoryQuantity(int quantity) {
        inventoryQuantity = quantity;
    }

    public void setWeight(double wgt) {
        weight = wgt;
    }

    public void setWeightUnit(String unit) {
        weightUnit = unit;
    }

    public void setInventoryItemID(String ID) {
        inventoryItemID = ID;
    }

    public void setOldInventoryQuantity(int quantity) {
        oldInventoryQuantity = quantity;
    }

    public void setRequiresShipping(boolean shippingRequired) {
        requiresShipping = shippingRequired;
    }
}
