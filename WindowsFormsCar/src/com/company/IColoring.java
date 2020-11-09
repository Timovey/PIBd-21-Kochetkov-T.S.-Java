package com.company;

import java.awt.*;

public interface IColoring {
    void setConfig(int configuration);
    void draw(Graphics g, int _startPosX, int _startPosY);
}
