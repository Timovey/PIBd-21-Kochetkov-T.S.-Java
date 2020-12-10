package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import javax.swing.border.BevelBorder;

public class FormParking {
    public JFrame frame;
    private ParkingCollection parkingCollection;
    private final JButton parkingCarButton = new JButton("Припарковать гусенечный автомобиль");
    private final JButton parkingCrawlerCarButton = new JButton("Припарковать экскаватор");
    private final int numType = 0;

    private JList<String> listOfParking;
    private final DefaultListModel<String> listParkingModel = new DefaultListModel<>();
    private LinkedList<Vehicle> dynamicParking = new LinkedList<>();

    /**
     * Launch the application.
     */
    FormParking() {
        initialize();
    }

    private void initialize() {
        int width = 700;
        int height = 400;
        parkingCollection = new ParkingCollection(width, height);
        frame = new JFrame();
        frame.setBounds(100, 100, 1100, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("Стоянка");
        ParkingPanel panel = new ParkingPanel();
        panel.setBounds(10, 11, width, height);
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        frame.getContentPane().add(panel);

        JLabel parkingLabel = new JLabel("Стоянки:");
        parkingLabel.setBounds(width + 20, 20, 100, 20);
        frame.add(parkingLabel);

        JTextField addParkingTextField = new JTextField(2);
        addParkingTextField.setFont(addParkingTextField.getFont().deriveFont(20f));
        addParkingTextField.setBounds(width + 20, 40, 200, 20);
        frame.add(addParkingTextField);

        JButton addParkingButton = new JButton("Добавить стоянку");
        addParkingButton.addActionListener(e -> {
            if (addParkingTextField.getText().equals("")) {
                JOptionPane.showMessageDialog(frame, "Введите название стоянки", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            parkingCollection.addParking(addParkingTextField.getText());
            ReloadLevels();
        });
        addParkingButton.setBounds(width + 20, 60, 200, 20);
        frame.add(addParkingButton);

        for (String key : parkingCollection.keys()) {
            listParkingModel.addElement(key);
        }
        listOfParking = new JList<>(listParkingModel);
        listOfParking.setLayoutOrientation(JList.VERTICAL);
        listOfParking.setBounds(width + 20, 80, 200, 80);
        listOfParking.addListSelectionListener(e -> {
            if (listOfParking.getSelectedIndex() > -1) {
                panel.setParking(parkingCollection.get(listParkingModel.get(listOfParking.getSelectedIndex())));
                panel.repaint();
            }
        });
        frame.add(listOfParking);

        JButton deleteParkingButton = new JButton("Удалить стоянку");
        deleteParkingButton.addActionListener(e -> {
            if (listOfParking.getSelectedIndex() > -1) {
                if (JOptionPane.showConfirmDialog(frame, "Удалить стоянку "
                        + listParkingModel.get(listOfParking.getSelectedIndex()) + "?", "Удаление", JOptionPane.OK_CANCEL_OPTION)
                        == JOptionPane.OK_OPTION) {
                    parkingCollection.delParking(listParkingModel.get(listOfParking.getSelectedIndex()));
                    ReloadLevels();
                }
            }
        });
        deleteParkingButton .setBounds(width + 20, 160, 200, 20);
        frame.add(deleteParkingButton );

        JButton showLastDynamicCarButton = new JButton("Забранный автомобиль");
        showLastDynamicCarButton.addActionListener(e -> {
            if (dynamicParking.size() == 0) {
                JOptionPane.showMessageDialog(frame, "Вы не забрали автомобиль");
                return;
            }
            EventQueue.invokeLater(() -> {
                Form window;
                try {
                    window = new Form(frame);
                    window.frame.setVisible(true);
                    frame.setVisible(false);
                    window.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                } catch (Exception exp) {
                    exp.printStackTrace();
                    return;
                }
                window.setCar(dynamicParking.getLast(), 10,  10, parkingCollection.getPictureWidth(), parkingCollection.getPictureHeight());
                dynamicParking.removeLast();
            });
        });
        showLastDynamicCarButton.setBounds(width + 20, 190, 200, 20);
        frame.add(showLastDynamicCarButton);

        parkingCarButton.addActionListener(e -> {
            if (listOfParking.getSelectedIndex() > -1) {
                Color mainColor = JColorChooser.showDialog(frame, "Выберите цвет автомобиля", Color.RED);
                if (mainColor != null) {
                    Vehicle car = new Car(100, 1000, mainColor);
                    if (parkingCollection.get(listParkingModel.get(listOfParking.getSelectedIndex())).add(car)) {
                        panel.repaint();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Стоянка переполнена", "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                panel.repaint();
            }
        });
        parkingCarButton.setBounds(width + 20, height  - 110, 200, 50);
        frame.getContentPane().add(parkingCarButton);
        parkingCrawlerCarButton.addActionListener(e -> {
            if (listOfParking.getSelectedIndex() > -1) {
                Color mainColor = JColorChooser.showDialog(frame, "Выберите основной цвет автомобиля", Color.BLUE);
                if (mainColor != null) {
                    Color dopColor = JColorChooser.showDialog(frame, "Выберите доп. цвет автомобиля", Color.BLUE);
                    if (dopColor != null) {
                        CrawlerCar car = new CrawlerCar(100, 1000, mainColor, dopColor, true, true, true, 3, numType);
                        if (parkingCollection.get(listParkingModel.get(listOfParking.getSelectedIndex())).add(car)) {
                            panel.repaint();
                        } else {
                            JOptionPane.showMessageDialog(frame, "Парковка переполнена", "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
                panel.repaint();
            }
        });
        parkingCrawlerCarButton.setBounds(width + 20, height  - 180, 200, 50);
        frame.getContentPane().add(parkingCrawlerCarButton);
        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        groupPanel.setBorder(BorderFactory.createTitledBorder("Забрать автомобиль"));
        groupPanel.setBounds(width + 20, height - 60, 200, 100);
        frame.getContentPane().add(groupPanel);

        JLabel placeLabel = new JLabel("Место:");
        groupPanel.add(placeLabel);

        JTextField placeTextField = new JTextField(2);
        placeTextField.setFont(placeTextField.getFont().deriveFont(20f));
        placeTextField.setBounds(20, 10, 100, 20);
        groupPanel.add(placeTextField);
        JButton takeCar = new JButton("Забрать");
        takeCar.addActionListener(e -> {
            if (listOfParking.getSelectedIndex() > -1) {
                if (!placeTextField.getText().equals("")) {
                    int numPlace;
                    try {
                        numPlace = Integer.parseInt(placeTextField.getText());
                    } catch (Exception ex) {
                        return;
                    }

                    Vehicle car = parkingCollection.get(listParkingModel.get(listOfParking.getSelectedIndex())).remove(numPlace);
                    if (car != null) {
                        dynamicParking.add(car);
                    }
                    panel.repaint();
                }
            }
        });
        takeCar.setBounds(20, 30, 100, 50);
        groupPanel.add(takeCar);
    }

    private void ReloadLevels() {
        int index = listOfParking.getSelectedIndex();

        listOfParking.setSelectedIndex(-1);
        listParkingModel.clear();
        for (int i = 0; i < parkingCollection.keys().length; i++) {
            listParkingModel.addElement(parkingCollection.keys()[i]);
        }

        if (listParkingModel.size() > 0 && index > -1 && index < listParkingModel.size()) {
            listOfParking.setSelectedIndex(index);
        }
        else  if (listParkingModel.size() > 0 && (index == -1 || index >= listParkingModel.size())) {
            listOfParking.setSelectedIndex(0);
        }
    }
}
