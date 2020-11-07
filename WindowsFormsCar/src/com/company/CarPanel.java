package com.company;

import javax.swing.*;
import java.awt.*;

public class CarPanel extends JPanel {
    private VehicleCar vehicleCar;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (vehicleCar != null)
            vehicleCar.drawTransport(g);
    }

    public void setVehicleCar(VehicleCar vehicleCar) {
        this.vehicleCar = vehicleCar;
    }
}
