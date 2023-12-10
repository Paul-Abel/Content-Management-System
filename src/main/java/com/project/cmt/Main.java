package com.project.cmt;


import bridge.MagazineBridge;
import entity.Magazine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args){


        Magazine magazine = new Magazine();
        magazine.setTitle("Paul");
        magazine.setDescription("Abel");

        MagazineBridge a = new MagazineBridge();


        a.createMagazine(magazine);
        //a.deleteMagazin(451L);

        //magazine = a.getMagazine(101L);
        //magazine.setTitle("Updated");
        //a.updateMagazine(magazine);



    }
}
