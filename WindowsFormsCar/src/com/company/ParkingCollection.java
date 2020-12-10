package com.company;

import java.util.HashMap;

public class ParkingCollection {
    private final HashMap<String, Parking<Vehicle, IColoring>> parkingStages;
    private final int pictureWidth;
    private final int pictureHeight;

    public String[] keys() {
        return parkingStages.keySet().toArray(new String[parkingStages.keySet().size()]);
    }

    public ParkingCollection(int pictureWidth, int pictureHeight) {
        parkingStages = new HashMap<>();
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
    }

    public void addParking(String name) {
        if (parkingStages.containsKey(name)) {
            return;
        }

        parkingStages.put(name, new Parking<>(pictureWidth, pictureHeight));
    }

    public void delParking(String name) {
        parkingStages.remove(name);
    }

    public Parking<Vehicle, IColoring> get(String ind) {
        if (!parkingStages.containsKey(ind)) {
            return null;
        }
        return parkingStages.get(ind);
    }

    public ITransport get(String string, int index) {
        if (!parkingStages.containsKey(string)) {
            return null;
        }

        return parkingStages.get(string).get(index);
    }

    public int getPictureHeight() {
        return pictureHeight;
    }
    public int getPictureWidth() {
        return pictureWidth;
    }
}