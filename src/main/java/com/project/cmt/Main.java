package com.project.cmt;


import bridge.MagazineBridge;
import bridge.PublisherBridge;
import entity.Magazine;
import entity.Publisher;

public class Main {
    // Method just to test different Methods
    public static void main(String[] args){


        Magazine magazine = new Magazine();
        magazine.setTitle("Paules");
        magazine.setDescription("Abel");

        MagazineBridge a = new MagazineBridge();
        PublisherBridge bridge = new PublisherBridge();
        //a.getAllMagazines();


        //a.deleteMagazin(451L);

        //Magazine magazine = a.getMagazine(101L);
        //magazine.setTitle("Updated");
        //a.updateMagazine(magazine);

        Publisher publisher = new Publisher();
        publisher.setName("Springer");
        publisher.setDeactivated(true);
        publisher.setStreet("Allee");
        publisher.setStreetnumber(15);
        publisher.setZip(76131);
        publisher.setCountry("Deutschland");
        bridge.createPublisher(publisher);

        //magazine.setPublisher(publisher);

       // a.createMagazine(magazine);


        //bridge.createPublisher(publisher);

        //Magazine b = a.getMagazine(101L);
        //Publisher c = b.getPublisher();
        //System.out.println(c.getName());




    }
}
