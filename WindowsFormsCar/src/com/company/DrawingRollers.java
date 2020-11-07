package com.company;

import java.awt.*;

public class DrawingRollers {
    private Rollers numRollers = Rollers.Four;

    void setConfig(int configuration) {
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

    void drawRollers(Graphics g, int _startPosX, int _startPosY) {
        g.setColor(Color.BLACK);
        switch (numRollers) {
            case Four:
                g.fillOval(_startPosX + 40, _startPosY + 40, 20, 20);
                break;
            case Five:
                g.fillOval(_startPosX + 40, _startPosY + 40, 20, 20);
                g.fillOval(_startPosX + 60, _startPosY + 40, 20, 20);
                break;
            case Six:
                g.fillOval(_startPosX + 40, _startPosY + 40, 20, 20);
                g.fillOval(_startPosX + 60, _startPosY + 40, 20, 20);
                g.fillOval(_startPosX + 80, _startPosY + 40, 20, 20);
                break;
        }
    }


}
