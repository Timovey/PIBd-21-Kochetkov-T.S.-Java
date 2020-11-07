package com.company;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class VehicleCar {
    private int _startPosX;
    private int _startPosY;
    private int _pictureWidth;
    private int _pictureHeight;
    private final int carWidth = 320;
    private final int carHeight = 60;

    private int maxSpeed;
    private float weight;
    private Color mainColor;
    private Color dopColor;
    private boolean frontLadle;
    private boolean backAntenna;
    private boolean stand;

    DrawingRollers drawingRollers = new DrawingRollers();

    VehicleCar(int maxSpeed, float weight, Color mainColor, Color dopColor, boolean frontLadle, boolean backAntenna, boolean stand) {
        this.maxSpeed = maxSpeed;
        this.weight = weight;
        this.mainColor = mainColor;
        this.dopColor = dopColor;
        this.frontLadle = frontLadle;
        this.backAntenna = backAntenna;
        this.stand = stand;
    }

    void setPosition(int x, int y, int width, int height) {
        _startPosX = x;
        _startPosY = y;
        _pictureWidth = width;
        _pictureHeight = height;
    }

    void moveTransport(Direction direction) {
        float step = maxSpeed * 100 / weight;
        switch (direction) {
            // вправо
            case Right:
                if (_startPosX + step < _pictureWidth - carWidth) {
                    _startPosX += step;
                }
                break;
            //влево
            case Left:
                if (_startPosX - step > 0) {
                    _startPosX -= step;
                }
                break;
            //вверх
            case Up:
                if (_startPosY - step > 0) {
                    _startPosY -= step;
                }
                break;
            //вниз
            case Down:
                if (_startPosY + step < _pictureHeight - carHeight) {
                    _startPosY += step;
                }
                break;
        }
    }

    void drawTransport(Graphics g) {
        //кузов
        g.setColor(Color.BLACK);
        g.fillOval(_startPosX + 20, _startPosY + 40, 20, 20);

        g.fillOval(_startPosX + 100, _startPosY + 40, 20, 20);
        g.fillOval(_startPosX + 120, _startPosY + 40, 20, 20);

        g.drawOval(_startPosX + 15, _startPosY + 40, 130, 20);


        g.drawRect(_startPosX + 30, _startPosY + 25, 120, 15);
        g.drawRect(_startPosX + 55, _startPosY + 10, 40, 15);

        g.setColor(mainColor);
        g.fillRect(_startPosX + 30, _startPosY + 25, 120, 15);
        g.fillRect(_startPosX + 55, _startPosY + 10, 40, 15);

        if (frontLadle) { // передний ковш

            g.setColor(dopColor);
            g.fillRect(_startPosX + 10, _startPosY, 30, 10);
            g.fillRect(_startPosX + 40, _startPosY, 10, 25);

            g.setColor(Color.BLACK);
            g.drawRect(_startPosX + 10, _startPosY, 30, 10);
            g.drawRect(_startPosX + 40, _startPosY, 10, 25);

            g.fillRect(_startPosX, _startPosY, 20, 20);
        }

        if(backAntenna) {
            g.setColor(Color.BLACK);
            g.fillRect(_startPosX + 145, _startPosY + 40, 5, 20);
        }

        if (stand)
        {
            g.setColor(dopColor);
            g.fillOval(_startPosX + 60, _startPosY, 15, 10);

            g.setColor(Color.BLACK);
            g.drawOval(_startPosX + 60, _startPosY, 15, 10);

        }
        drawingRollers.drawRollers(g,_startPosX,_startPosY);
    }
}
