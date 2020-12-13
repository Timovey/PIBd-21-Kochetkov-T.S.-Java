package com.company;

import java.awt.*;

public class CrawlerCar extends Car {

    private Color dopColor;
    private boolean frontLadle;
    private boolean backAntenna;
    private boolean stand;
    private IColoring drawingRollers;

    CrawlerCar(int maxSpeed, float weight, Color mainColor, Color dopColor, boolean frontLadle, boolean backAntenna, boolean stand, int numRollers, int numType) {
        super(maxSpeed, weight, mainColor, 320, 60);
        this.maxSpeed = maxSpeed;
        this.weight = weight;
        this.mainColor = mainColor;
        this.dopColor = dopColor;
        this.frontLadle = frontLadle;
        this.backAntenna = backAntenna;
        this.stand = stand;
        if (numType == 1) {
            drawingRollers = new DrawingCircleRollers();
        } else if (numType == 2) {
            drawingRollers = new DrawingSquareRollers();
        } else {
            drawingRollers = new DrawingRollers();
        }
        drawingRollers.setConfig(numRollers);
    }

    public void setNumRollers(int numRollers) {
        drawingRollers.setConfig(numRollers);
    }

    public void setDopColor(Color dopColor) {
        this.dopColor = dopColor;
    }

    public void setDrawingRollers(IColoring iColoring) {
        this.drawingRollers = iColoring;
    }

    @Override
    public void drawTransport(Graphics g) {

        super.drawTransport(g);
        if (frontLadle) { // передний ковш

            g.setColor(dopColor);
            g.fillRect(_startPosX + 10, _startPosY, 30, 10);
            g.fillRect(_startPosX + 40, _startPosY, 10, 25);

            g.setColor(Color.BLACK);
            g.drawRect(_startPosX + 10, _startPosY, 30, 10);
            g.drawRect(_startPosX + 40, _startPosY, 10, 25);

            g.fillRect(_startPosX, _startPosY, 20, 20);
        }

        if (backAntenna) {
            g.setColor(Color.BLACK);
            g.fillRect(_startPosX + 145, _startPosY + 40, 5, 20);
        }

        if (stand) {
            g.setColor(dopColor);
            g.fillOval(_startPosX + 60, _startPosY, 15, 10);

            g.setColor(Color.BLACK);
            g.drawOval(_startPosX + 60, _startPosY, 15, 10);

        }
        drawingRollers.draw(g, _startPosX, _startPosY);
    }
}
