package com.company;

import javax.swing.*;
import java.awt.*;

public class FormParking {
    public JFrame frame;
    private Parking<ITransport, IColoring> parking;
    private final JButton parkingCarButton = new JButton("Припарковать гусенечный автомобиль");
    private final JButton parkingCrawlerCarButton = new JButton("Припарковать экскаватор");
    private final int numType = 0;

    /**
     * Launch the application.
     */
    FormParking() {
        initialize();
    }

    private void initialize() {
        int width = 700;
        int height = 400;
        parking = new Parking<>(width, height);
        frame = new JFrame();
        frame.setBounds(100, 100, 1100, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("Парковка");
        ParkingPanel panel = new ParkingPanel(parking);
        panel.setBounds(10, 11, width, height);
        frame.getContentPane().add(panel);
        parkingCarButton.addActionListener(e -> {
            Color mainColor = JColorChooser.showDialog(frame, "Выберите цвет автомобиля", Color.RED);
            if (mainColor != null) {
                Car car = new Car(100, 1100, mainColor);
                if (parking.add(car)) {
                    panel.repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "Парковка переполнена", "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            panel.repaint();
        });
        parkingCarButton.setBounds(width + 20, 20, 200, 50);
        frame.getContentPane().add(parkingCarButton);
        parkingCrawlerCarButton.addActionListener(e -> {
            Color mainColor = JColorChooser.showDialog(frame, "Выберите основной цвет автомобиля", Color.BLUE);
            if (mainColor != null) {
                Color dopColor = JColorChooser.showDialog(frame, "Выберите доп. цвет автомобиля", Color.BLUE);
                if (dopColor != null) {
                    CrawlerCar car = new CrawlerCar(100, 1000, mainColor, dopColor, true, true, true, 3, numType);
                    if (parking.add(car)) {
                        panel.repaint();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Парковка переполнена", "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
            panel.repaint();
        });
        parkingCrawlerCarButton.setBounds(width + 20, 80, 200, 50);
        frame.getContentPane().add(parkingCrawlerCarButton);
        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        groupPanel.setBorder(BorderFactory.createTitledBorder("Забрать автомобиль"));
        groupPanel.setBounds(width + 20, 200, 200, 100);
        frame.getContentPane().add(groupPanel);

        JLabel placeLabel = new JLabel("Место:");
        groupPanel.add(placeLabel);

        JTextField placeTextField = new JTextField(2);
        placeTextField.setFont(placeTextField.getFont().deriveFont(20f));
        placeTextField.setBounds(20, 10, 100, 20);
        groupPanel.add(placeTextField);
        JButton takeCar = new JButton("Забрать");
        takeCar.addActionListener(e -> {
            if (!placeTextField.getText().equals("")) {
                int numPlace;
                try {
                    numPlace = Integer.parseInt(placeTextField.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Вы не ввели число", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                ITransport car = parking.remove(numPlace);
                if (car != null) {
                    EventQueue.invokeLater(() -> {
                        Form window;
                        try {
                            window = new Form(frame);
                            window.frame.setVisible(true);
                            frame.setVisible(false);
                        } catch (Exception exp) {
                            exp.printStackTrace();
                            return;
                        }
                        int placeSizeWidth = parking.getPlaceWeight();
                        int placeSizeHeight = parking.getPlaceHeight();
                        int rows = parking.getRows();
                        window.setCar(car, numPlace / rows * placeSizeWidth + 10, (numPlace - numPlace / rows * rows) * placeSizeHeight + 10, placeSizeWidth, placeSizeHeight);
                    });
                }
                panel.repaint();
            }
        });
        takeCar.setBounds(20, 30, 100, 50);
        groupPanel.add(takeCar);

    }
}
