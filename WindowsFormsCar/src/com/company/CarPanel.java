package com.company;

import javax.swing.*;
import java.awt.*;

public class CarPanel extends JPanel {
    private ITransport iTransport;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (iTransport != null) {
            iTransport.drawTransport(g);
        }
    }

    public void setCar(ITransport iTransport) {
        this.iTransport = iTransport;
    }
}
