package com.example.idarkduck.shopify;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by david on 2017-12-28.
 */

public class Product implements Serializable {

    private String ID;
    private String productName;
    private String htmlBody;
    private String vendor;
    private String productType;
    private String createdAt;
    private String handle;
    private String updatedAt;
    private String publishedAt;
    private String templateSuffix;
    private String publishedScope;
    private String tags;
    private String image;
    private ArrayList<ProductVariant> variants;

    public Product(String ID, String productName) {
        this.ID = ID;
        this.productName = productName;
    }

    public String toString(){
        String s="";
        s+= " ID: " + ID +'\n';
        s+= " body_html: " + htmlBody +'\n';
        s+= " vendor: " + htmlBody +'\n';
        s+= " productType: " + htmlBody +'\n';
        s+= " createdAt: " + htmlBody +'\n';
        s+= " handle: " + handle +'\n';
        s+= " updated_at: " + updatedAt +'\n';
        s+= " published_at: " + publishedAt +'\n';
        s+= " template_suffix: " + templateSuffix+'\n';
        s+= " published_scope: " + publishedScope+'\n';
        s+= " tags: " + tags +'\n';
        s+= " image: " + image;
        return s;
    }
    public String toStringOriginal(){
        String s="";
        s+= " ID: " + ID;
        s+= " body_html: " + htmlBody;
        s+= " vendor: " + htmlBody;
        s+= " productType: " + htmlBody;
        s+= " createdAt: " + htmlBody;
        s+= " handle: " + handle;
        s+= " updated_at: " + updatedAt;
        s+= " published_at: " + publishedAt;
        s+= " template_suffix: " + templateSuffix;
        s+= " published_scope: " + publishedScope;
        s+= " tags: " + tags;
        s+= " image: " + image;
        return s;
    }

    //Getters
    public String getID() {
        return ID;
    }

    public String getProductName() {
        return productName;
    }

    public String getHtmlBody() {
        return htmlBody;
    }

    public String getVendor() {
        return vendor;
    }

    public String getProductType() {
        return productType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getHandle() {
        return handle;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getTemplateSuffix() {
        return templateSuffix;
    }

    public String getPublishedScope() {
        return publishedScope;
    }

    public String getTags() {
        return tags;
    }
    public String getImage(){
        return image;
    }

    public ArrayList<ProductVariant> getVariants() {
        return variants;
    }
    //setters
    public void setID(String s) {
        ID=s;
    }
    public void setProductName(String s) {
        productName =s;
    }

    public void setHtmlBody(String s) {
        htmlBody =s;
    }

    public void setVendor(String s) {
        vendor =s;
    }

    public void setProductType(String s) {
        productType =s;
    }

    public void setCreatedAt(String s) {
        createdAt =s;
    }

    public void setHandle(String s) {
        handle =s;
    }

    public void setUpdatedAt(String s) {
        updatedAt =s;
    }

    public void setPublishedAt(String s) {
        publishedAt =s;
    }
    public void setTemplateSuffix(String s) {
        templateSuffix =s;
    }

    public void setPublishedScope(String s) {
        publishedScope =s;
    }

    public void setTags(String s) {
        tags =s;
    }
    public void setImage(String s){
        image =s;
    }

    public void addVariant(ProductVariant variant) {
        if (variant == null) {
            return;
        }

        if (variants == null) {
            variants = new ArrayList<ProductVariant>();
        }

        variants.add(variant);
    }
}
