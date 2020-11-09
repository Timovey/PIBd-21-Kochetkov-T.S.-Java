package com.company;

import java.awt.*;

public class Car extends Vehicle {

    protected int carWidth = 320;
    protected int carHeight = 60;

    public Car(int maxSpeed, float weight, Color mainColor) {
        this.maxSpeed = maxSpeed;
        this.weight = weight;
        this.mainColor = mainColor;
    }

    protected Car(int maxSpeed, float weight, Color mainColor, int carWidth, int carHeight) {
        this.maxSpeed = maxSpeed;
        this.weight = weight;
        this.mainColor = mainColor;
        this.carWidth = carWidth;
        this.carHeight = carHeight;
    }

    @Override
    public void moveTransport(Direction direction) {
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

    @Override
    public void drawTransport(Graphics g) {
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

    }

}
