package com.company;

import java.awt.*;

public class CrawlerCar extends Car {

    private Color dopColor;
    private boolean frontLadle;
    private boolean backAntenna;
    private boolean stand;
    private int numRollers;
    private int numType;
    private IColoring drawingRollers;
    static private String separator = ";";

    CrawlerCar(int maxSpeed, float weight, Color mainColor, Color dopColor, boolean frontLadle, boolean backAntenna, boolean stand, int numRollers, int numType) {
        super(maxSpeed, weight, mainColor, 320, 60);
        this.dopColor = dopColor;
        this.frontLadle = frontLadle;
        this.backAntenna = backAntenna;
        this.stand = stand;
        this.numRollers = numRollers;
        this.numType = numType;
        drawingRollers = setIColoring(numType);
        drawingRollers.setConfig(numRollers);
    }

    public CrawlerCar(String info) {
        super(Integer.parseInt(info.split(separator)[0]), Float.parseFloat(info.split(separator)[1]), Color.decode(info.split(separator)[2]));

        String[] strings = info.split(separator);
        if (strings.length == 9) {
            dopColor = Color.decode(strings[3]);
            frontLadle = Boolean.parseBoolean(strings[4]);
            backAntenna = Boolean.parseBoolean(strings[5]);
            stand = Boolean.parseBoolean(strings[6]);
            this.numRollers = Integer.parseInt(strings[7]);
            this.numType = Integer.parseInt(strings[8]);
            drawingRollers = setIColoring(numType);
            drawingRollers.setConfig(numRollers);
        }
    }

    public IColoring setIColoring(int numType) {
        if (numType == 1) {
            return new DrawingCircleRollers();
        } else if (numType == 2) {
            return new DrawingSquareRollers();
        } else {
            return new DrawingRollers();
        }
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
    public String toString() {
        return super.toString() + separator
                + dopColor.getRGB() + separator
                + frontLadle + separator
                + backAntenna + separator
                + stand + separator
                + numRollers + separator
                + numType;
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

        if (backAntenna) { // задняя антенна
            g.setColor(Color.BLACK);
            g.fillRect(_startPosX + 145, _startPosY + 40, 5, 20);
        }

        if (stand) {// мигалка
            g.setColor(dopColor);
            g.fillOval(_startPosX + 60, _startPosY, 15, 10);

            g.setColor(Color.BLACK);
            g.drawOval(_startPosX + 60, _startPosY, 15, 10);

        }
        drawingRollers.draw(g, _startPosX, _startPosY);
    }
}
