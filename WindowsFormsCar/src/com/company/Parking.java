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
    private int _maxCount;

    public Parking(int pictureWidth, int pictureHeight) {
        int columns = pictureWidth / _placeSizeWidth;
        rows = pictureHeight / _placeSizeHeight;
        places = new ArrayList<>();
        _maxCount = columns * rows;
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
    }

    public boolean add(T car) {
        if (places.size() >= _maxCount) {
            return false;
        }
        places.add(car);
        return true;
    }

    public T remove(int index) {
        if (index < -1 || index > places.size() - 1) {
            return null;
        }
        T car = places.get(index);
        places.remove(index);
        return car;
    }


    public void Draw(Graphics g) {
        DrawMarking(g);
        for (int i = 0; i < places.size(); ++i) {
            places.get(i).setPosition(5 + i / 5 * _placeSizeWidth + 5, i % 5 * _placeSizeHeight + 15, pictureWidth, pictureHeight);
            places.get(i).drawTransport(g);
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

    public T get(int index) {
        if (index < 0 || index > places.size() - 1) {
            return null;
        }
        return places.get(index);
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

}
