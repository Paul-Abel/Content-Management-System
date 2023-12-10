package com.project.cmt;


import bridge.MagazineBridge;
import entity.Magazine;

public class Main {
    // Method just to test different Methods
    public static void main(String[] args){


        //Magazine magazine = new Magazine();
        //magazine.setTitle("Paules");
        //magazine.setDescription("Abel");

        MagazineBridge a = new MagazineBridge();
        a.getAllMagazines();


        //a.createMagazine(magazine);
        //a.deleteMagazin(451L);

        Magazine magazine = a.getMagazine(101L);
        magazine.setTitle("Updated");
        a.updateMagazine(magazine);



    }
}
