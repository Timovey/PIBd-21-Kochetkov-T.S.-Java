package com.company;

import java.awt.*;

public class DrawingCircleRollers implements IColoring {
    private Rollers numRollers = Rollers.Four;
    @Override
    public void setConfig(int configuration) {
        switch (configuration) {
            case 5:
                numRollers = Rollers.Five;
                break;
            case 6:
                numRollers = Rollers.Six;
                break;
            default:
                numRollers = Rollers.Four;
        }
    }

    @Override
    public void draw(Graphics g, int _startPosX, int _startPosY) {
        g.setColor(Color.BLACK);
        switch (numRollers) {
            case Six:
                g.fillOval(_startPosX + 80, _startPosY + 40, 20, 20);
                g.setColor(Color.YELLOW);
                g.drawOval(_startPosX + 80, _startPosY + 40, 20, 20);
                g.fillOval(_startPosX + 85, _startPosY + 45, 10, 10);
                g.setColor(Color.BLACK);
            case Five:
                g.fillOval(_startPosX + 60, _startPosY + 40, 20, 20);
                g.setColor(Color.YELLOW);
                g.drawOval(_startPosX + 60, _startPosY + 40, 20, 20);
                g.fillOval(_startPosX + 65, _startPosY + 45, 10, 10);
                g.setColor(Color.BLACK);
            case Four:
                g.fillOval(_startPosX + 40, _startPosY + 40, 20, 20);
                g.setColor(Color.YELLOW);
                g.drawOval(_startPosX + 40, _startPosY + 40, 20, 20);
                g.fillOval(_startPosX + 45, _startPosY + 45, 10, 10);
                g.setColor(Color.BLACK);
                break;
        }
    }

}
