package com.qa.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private int id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private Rating rating;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Rating{
        private double rate;
        private int count;
    }
    //We create above for--
//    {
//        "id": 1,
//            "title": "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
//            "price": 109.95,
//            "description": "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
//            "category": "men's clothing",
//            "image": "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_t.png",
//            "rating": {
//        "rate": 3.9,
//                "count": 120
//    }
//    }
}
