package com.company;

import javax.swing.*;
import java.awt.*;

public class ParkingPanel extends JPanel {
    private Parking<Vehicle, IColoring> parking;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(parking != null) {
            parking.Draw(g);
        }
    }
    public void setParking(Parking<Vehicle, IColoring> parking) {
        this.parking = parking;
    }
}
