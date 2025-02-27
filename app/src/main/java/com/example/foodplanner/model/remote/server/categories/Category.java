package com.example.foodplanner.model.remote.server.categories;

public class Category {
    private String strCategory;
    private String strCategoryThumb;

    public Category(String strCategory, String strCategoryThumb) {
        this.strCategory = strCategory;
        this.strCategoryThumb = strCategoryThumb;
    }

    public String getStrCategory() {
        return strCategory;
    }

    @Override
    public String toString() {
        return "Category{" +
                "strCategory='" + strCategory + '\'' +
                ", strCategoryThumb='" + strCategoryThumb + '\'' +
                '}';
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrCategoryThumb() {
        return strCategoryThumb;
    }

    public void setStrCategoryThumb(String strCategoryThumb) {
        this.strCategoryThumb = strCategoryThumb;
    }
}
