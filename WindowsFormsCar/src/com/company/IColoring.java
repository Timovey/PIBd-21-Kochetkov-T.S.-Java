package com.company;

import java.awt.*;
import java.io.Serializable;

public interface IColoring extends Serializable {
    void setConfig(int configuration);
    void draw(Graphics g, int _startPosX, int _startPosY);
}
