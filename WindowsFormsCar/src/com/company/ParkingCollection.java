package com.company;

import java.io.*;
import java.util.HashMap;

public class ParkingCollection {
    private final HashMap<String, Parking<Vehicle, IColoring>> parkingStages;
    private final String separator = ":";
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

    public boolean SaveData(String name, String selectedName) {
        if (!parkingStages.containsKey(selectedName)) {
            return false;
        }
        try {
            File file = new File(name);
            if (file.exists()) {
                if (!file.delete())
                    return false;
            }
            if (!file.createNewFile()) {
                return false;
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write("Parking" + separator + selectedName);
            bw.newLine();
            ITransport car;
            for (int i = 0; (car = parkingStages.get(selectedName).getNext(i)) != null; i++) {
                if (car.getClass() == Car.class) {
                    bw.write("Car" + separator);
                }
                if (car.getClass() == CrawlerCar.class) {
                    bw.write("CrawlerCar" + separator);
                }
                bw.write(car.toString());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public boolean SaveData(String name) {
        try {
            File file = new File(name);
            if (file.exists()) {
                if (!file.delete())
                    return false;
            }
            if (!file.createNewFile()) {
                return false;
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write("ParkingCollection");
            bw.newLine();

            parkingStages.forEach((key, value) -> {
                try {
                    bw.write("Parking" + separator + key);
                    bw.newLine();
                    ITransport car;
                    for (int i = 0; (car = value.getNext(i)) != null; i++) {
                        if (car.getClass() == Car.class) {
                            bw.write("Car" + separator);
                        }
                        if (car.getClass() == CrawlerCar.class) {
                            bw.write("CrawlerCar" + separator);
                        }
                        bw.write(car.toString());
                        bw.newLine();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            bw.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public boolean LoadData(String name) {
        try {
            File file = new File(name);
            if (!file.exists()) {
                return false;
            }
            boolean head = true;
            String line;
            Vehicle car = null;
            String key = "";
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                line = line.replace("\r", "");
                if (head) {
                    if (line.contains("Parking")) {
                        key = line.split(separator)[1];
                        if (parkingStages.containsKey(key)) {
                            parkingStages.get(key).clearPlaces();
                        } else {
                            parkingStages.put(key, new Parking<>(pictureWidth, pictureHeight));
                        }
                        head = false;
                    } else {
                        return false;
                    }
                } else {
                    if (line.equals("")) {
                        continue;
                    }
                    if (line.split(separator)[0].equals("Сar")) {
                        car = new Car(line.split(separator)[1]);
                    } else if (line.split(separator)[0].equals("CrawlerCar")) {
                        car = new CrawlerCar(line.split(separator)[1]);
                    }
                    boolean result = parkingStages.get(key).add(car);
                    if (!result) {
                        return false;
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public boolean LoadAData(String name, boolean all) {
        try {
            File file = new File(name);
            if (!file.exists()) {
                return false;
            }
            boolean head = true;
            String line;
            Vehicle car = null;
            String key = "";
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                line = line.replace("\r", "");
                if (head) {
                    if (line.contains("ParkingCollection")) {
                        parkingStages.clear();
                        head = false;
                    } else {
                        return false;
                    }
                } else {
                    if (line.contains("Parking")) {
                        key = line.split(separator)[1];
                        parkingStages.put(key, new Parking<>(pictureWidth, pictureHeight));
                        continue;
                    }
                    if (line.equals("")) {
                        continue;
                    }
                    if (line.split(separator)[0].equals("Car")) {
                        car = new Car(line.split(separator)[1]);
                    } else if (line.split(separator)[0].equals("CrawlerCar")) {
                        car = new CrawlerCar(line.split(separator)[1]);
                    }
                    boolean result = parkingStages.get(key).add(car);
                    if (!result) {
                        return false;
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            return false;
        }
        return true;
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