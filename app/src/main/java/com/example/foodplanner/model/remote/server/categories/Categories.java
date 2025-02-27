package com.example.foodplanner.model.remote.server.categories;

import java.util.List;

public class Categories {
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "categories=" + categories +
                '}';
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}