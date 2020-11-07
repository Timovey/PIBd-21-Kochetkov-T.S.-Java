package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.io.File;

public class Form {
    public JFrame frame;
    private final JButton up = new JButton();
    private final JButton down = new JButton();
    private final JButton left = new JButton();
    private final JButton right = new JButton();
    private final JButton createCarButton = new JButton("Create Car");
    private final JButton createCrawlerCarButton = new JButton("Create Crawler Car");

    private ITransport Car;

    /**
     * Launch the application.
     */
    Form() {
        initialize();
    }

    private void initialize() {
        int width = 950;
        int height = 600;
        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        CarPanel panel = new CarPanel();
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED,
                null, null, null, null));
        panel.setBounds(10, 11, width, height);
        frame.getContentPane().add(panel);
        setupButton(up, "Up", 65, 630);
        setupButton(down, "Down", 65, 690);
        setupButton(left, "Left", 5, 690);
        setupButton(right, "Right", 125, 690);
        createCarButton.addActionListener(e -> {
            Car = new Car(100, 1000, Color.BLUE);
            Car.setPosition(10, 10, width, height);
            panel.setCar(Car);
            panel.repaint();
        });
        createCarButton.setBounds(200, 645, 100, 50);
        frame.getContentPane().add(createCarButton);
        up.addActionListener(e -> {
            if(Car != null){
                Car.moveTransport(Direction.Up);
                panel.repaint();
            }
        });

        createCrawlerCarButton.addActionListener(e -> {
            Car = new CrawlerCar(100, 1000, Color.BLUE, Color.YELLOW, true, true, true);
            Car.setPosition(10, 10, width, height);
            panel.setCar(Car);
            panel.repaint();
        });
        createCrawlerCarButton.setBounds(500, 645, 150, 50);
        frame.getContentPane().add(createCrawlerCarButton);
        up.addActionListener(e -> {
            if(Car != null){
                Car.moveTransport(Direction.Up);
                panel.repaint();
            }
        });


        down.addActionListener(e -> {
            if(Car != null){
                Car.moveTransport(Direction.Down);
                panel.repaint();
            }
        });
        left.addActionListener(e -> {
            if(Car != null){
                Car.moveTransport(Direction.Left);
                panel.repaint();
            }
        });
        right.addActionListener(e -> {
            if(Car != null){
                Car.moveTransport(Direction.Right);
                panel.repaint();
            }
        });

        JButton fourRollers = new JButton("4");
        fourRollers.setBounds(310, 645, 50, 50);
        frame.getContentPane().add(fourRollers);
        fourRollers.addActionListener(e -> {
            Car.drawingRollers.setConfig(4);
            panel.repaint();
        });

        JButton fiveRollers = new JButton("5");
        fiveRollers.setBounds(370, 645, 50, 50);
        frame.getContentPane().add(fiveRollers);
        fiveRollers.addActionListener(e -> {
            Car.drawingRollers.setConfig(5);
            panel.repaint();
        });

        JButton sixRollers = new JButton("6");
        sixRollers.setBounds(430, 645, 50, 50);
        frame.getContentPane().add(sixRollers);
        sixRollers.addActionListener(e -> {
            Car.drawingRollers.setConfig(6);
            panel.repaint();
        });
    }

    void setupButton(JButton button, String name, int x, int y) {
        try {
            Image img = ImageIO.read(new File("src/images/" + name + ".jpg"));
            button.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        button.setBounds(x, y, 50, 50);
        frame.getContentPane().add(button);
    }
}
