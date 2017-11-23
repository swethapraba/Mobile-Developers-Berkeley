package com.example.android.lesson2worksheet;

/**
 * Created by JustinKim on 2/8/17.
 */

public class Company {

    //Here, we want to have a Name of the computer company, a ratingBox of whether you like the company, and
    //an image of the company. First create those variables and then instantiate those three variables.

    String companyName;
    float likeCompany;
    int imageResId;

    public Company(String companyName, float likeCompany, int imageResId) {
        this.companyName = companyName;
        this.likeCompany = likeCompany;
        this.imageResId = imageResId;
    }


}
