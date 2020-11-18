package com.company;

import java.awt.*;
import java.util.ArrayList;

public class Parking<T extends ITransport, E extends IColoring> {
    private ArrayList<T> places;
    private int pictureWidth;
    private int pictureHeight;
    private int rows;
    private int _placeSizeWidth = 320;
    private int _placeSizeHeight = 80;

    public Parking(int pictureWidth, int pictureHeight) {
        int columns = pictureWidth / _placeSizeWidth;
        rows = pictureHeight / _placeSizeHeight;
        places = new ArrayList<>();
        for (int i = 0; i < columns * rows; i++) {
            places.add(null);
        }
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
    }

    public boolean add(T car) {
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i) == null) {
                car.setPosition(i / rows * _placeSizeWidth + 10, (i - i / rows * rows) * _placeSizeHeight + 10, pictureWidth, pictureHeight);
                places.set(i, car);
                return true;
            }
        }
        return false;
    }

    public T remove(int index) {
        if (index < 0 || index > places.size() - 1) {
            return null;
        }
        T car = places.get(index);
        places.remove(index);
        return car;
    }


    public void Draw(Graphics g) {
        DrawMarking(g);
        for (int i = 0; i < places.size(); i++) {
            T car = places.get(i);
            if (car != null) {
                car.drawTransport(g);
            }
        }
    }

    private void DrawMarking(Graphics g) {
        g.setColor(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2.0f));
        for (int i = 0; i < pictureWidth / _placeSizeWidth; i++) {
            for (int j = 0; j < pictureHeight / _placeSizeHeight + 1; ++j) {
                g2.drawLine(i * _placeSizeWidth, j * _placeSizeHeight, i * _placeSizeWidth + _placeSizeWidth / 2, j * _placeSizeHeight);
            }
            g2.drawLine(i * _placeSizeWidth, 0, i * _placeSizeWidth, (pictureHeight / _placeSizeHeight) * _placeSizeHeight);
        }
    }


    public boolean equal(ITransport car) { // ==
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i).equals(car)) {
                return true;
            }
        }
            return false;
    }

    public boolean unequal(ITransport car) { // !=
    return !equal(car);
    }

    public int getPlaceWeight() {
        return _placeSizeWidth;
    }
    public int getPlaceHeight() {
        return _placeSizeHeight;
    }
    public int getRows() {
        return rows;
    }


}
