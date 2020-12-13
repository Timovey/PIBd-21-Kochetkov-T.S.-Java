package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.LinkedList;
import javax.swing.border.BevelBorder;

public class FormParking {
    public JFrame frame;
    private ParkingCollection parkingCollection;
    private JButton parkingCarButton = new JButton("Припарковать автомобиль");
    private final ParkingPanel panel = new ParkingPanel();


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

        panel.setBounds(10, 11, width, height);
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        frame.getContentPane().add(panel);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu());
        frame.setJMenuBar(menuBar);
        
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
        deleteParkingButton.setBounds(width + 20, 160, 200, 20);
        frame.add(deleteParkingButton);

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
                window.setCar(dynamicParking.getLast(), 10, 10, parkingCollection.getPictureWidth(), parkingCollection.getPictureHeight());
                dynamicParking.removeLast();
            });
        });
        showLastDynamicCarButton.setBounds(width + 20, 190, 200, 20);
        frame.add(showLastDynamicCarButton);

        parkingCarButton.addActionListener(e -> EventQueue.invokeLater(() ->
        {
            if(parkingCollection.keys().length == 0) {
                JOptionPane.showMessageDialog(frame, "Добавьте сначала парковку");
                return;
            }
            try {
                FormCarConfig window = new FormCarConfig(frame);

                window.addEvent((vehicle) ->addCar(vehicle));
                window.frame.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        ));
        parkingCarButton.setBounds(width + 20, height - 110, 200, 50);
        frame.getContentPane().add(parkingCarButton);

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

    private void addCar(Vehicle car) {
        if (car != null && listOfParking.getSelectedIndex() > -1) {
            if (parkingCollection.get(listParkingModel.get(listOfParking.getSelectedIndex())).add(car)) {
                panel.repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "Автомобиль не удалось поставить");
            }
        }
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
        } else if (listParkingModel.size() > 0 && (index == -1 || index >= listParkingModel.size())) {
            listOfParking.setSelectedIndex(0);
        }
    }

    private JMenu fileMenu() {
        JMenu fileMenu = new JMenu("Меню");
        JMenuItem saveParking = new JMenuItem("Сохранить парковку");
        fileMenu.add(saveParking);
        JMenuItem loadParking = new JMenuItem("Загрузить парковку");
        fileMenu.add(loadParking);
        JMenuItem save = new JMenuItem("Сохранить");
        fileMenu.add(save);
        JMenuItem load = new JMenuItem("Загрузить");
        fileMenu.add(load);


        saveParking.addActionListener(e ->
        {
            String name = fileDialogSetup(true);
            if (name == null) {
                return;
            }
            name = checkTXT(name);
            if (listOfParking.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(frame, "Вы не указали парковку", "Сохранение парковки", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if (parkingCollection.SaveData(name , listParkingModel.get(listOfParking.getSelectedIndex()))) {
                JOptionPane.showMessageDialog(frame, "Парковка сохранена", "Сохранение парковки", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Ошибка при сохранении", "Сохранение парковки", JOptionPane.ERROR_MESSAGE);
            }
        });

        loadParking.addActionListener(e ->
        {
            String name = fileDialogSetup(false);
            if (name == null) {
                return;
            }
            if (parkingCollection.LoadData(name)) {
                JOptionPane.showMessageDialog(frame, "Парковка загружена", "Загрузка парковки", JOptionPane.INFORMATION_MESSAGE);
                listParkingModel.clear();
                for (String key : parkingCollection.keys()) {
                    listParkingModel.addElement(key);
                }
                if (listParkingModel.size() > 0) {
                    panel.setParking(parkingCollection.get(listParkingModel.get(0)));
                    panel.repaint();
                }
                panel.repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "Ошибка при загрузке", "Загрузка парковки", JOptionPane.ERROR_MESSAGE);
            }
        });

        save.addActionListener(e ->
        {
            String name = fileDialogSetup(true);
            if (name == null) {
                return;
            }
            name = checkTXT(name);
            if (parkingCollection.SaveData(name )) {
                JOptionPane.showMessageDialog(frame, "Сохранено", "Сохранение", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Ошибка при сохранении", "Сохранение",  JOptionPane.ERROR_MESSAGE);
            }
        });

        load.addActionListener(e ->
        {
            String name = fileDialogSetup(false);
            if (name == null) {
                return;
            }
            if (parkingCollection.LoadAData(name,true)) {
                JOptionPane.showMessageDialog(frame, "Загружено", "Загрузка", JOptionPane.INFORMATION_MESSAGE);
                ReloadLevels();
                if (listParkingModel.size() > 0) {
                    panel.setParking(parkingCollection.get(listParkingModel.get(0)));
                    panel.repaint();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Ошибка при загрузке", "Загрузка", JOptionPane.ERROR_MESSAGE);
            }
        });

        return fileMenu;
    }

    private String checkTXT(String name) {
        char [] nameChars = name.toCharArray();
        StringBuffer bf = new StringBuffer();
        for(int i = nameChars.length - 1; i >= 0 && i >= nameChars.length - 4 ; i--) {
            bf.append(nameChars[i]);
        }
        if(bf.toString().equals("txt.")) {
            return name;
        }
        else {
            return name + ".txt";
        }
    }

    private String fileDialogSetup(boolean save) {
        JFileChooser fileChooser = new JFileChooser();
        int fileChooseValue;
        if(save) {
            fileChooseValue = fileChooser.showSaveDialog(frame);
        }
        else {
            fileChooseValue = fileChooser.showOpenDialog(frame);
        }

        if (JFileChooser.APPROVE_OPTION == fileChooseValue) {
            File file = fileChooser.getSelectedFile();
            return file.getAbsolutePath();
        }
        return null;
    }
}
