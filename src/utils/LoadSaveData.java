/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.Crew;
import model.Flight;
import model.Reservation;

/**
 *
 * @author 2004d
 */
public class LoadSaveData {

    public static <T extends Serializable> T loadFromFile(String fileName, boolean debug) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            T object = (T) ois.readObject();
            if (debug) {
                System.out.println("Data loaded successfully from " + fileName);
            }
            return object;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
            return null;
        }
    }

    public static <T extends Serializable> void saveToFile(String fileName, T object, boolean debug) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(object);
            if (debug) {
                System.out.println("Data saved successfully to " + fileName);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static class Zip implements Serializable {

        private List<List> data = new ArrayList<>();

        public Zip(List... data) {
            for (List list : data) {
                this.data.add(list);
            }
        }

        public List<List> unZip() {
            return this.data;
        }
    }
}
