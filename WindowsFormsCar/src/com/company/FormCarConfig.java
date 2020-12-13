package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class FormCarConfig {
    public JDialog frame;
    public Vehicle car = null;
    private IDelegate eventAddCar;
    JButton addCarButton = new JButton("Добавить");
    JButton cancelButton = new JButton("Отменить");

    FormCarConfig(JFrame parent) {
        initialize(parent);
    }

    private void initialize(JFrame parent) {
        int width = 300;
        int height = 200;
        CarPanel carPanel = new CarPanel();
        carPanel.setBounds(220, 10, width, height);
        carPanel.setBorder(BorderFactory.createBevelBorder(1));
        carPanel.setTransferHandler(new TransferHandler("text"));

        frame = new JDialog(parent);
        frame.setBounds(100, 100, 900, 450);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("Добавление автомобиля");

        JPanel groupPanelBody = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        groupPanelBody.setBorder(BorderFactory.createTitledBorder("Тип кузова"));
        groupPanelBody.setBounds(10, 10, 200, 100);
        frame.getContentPane().add(groupPanelBody);



        JLabel CrawlerCarLabel = new JLabel("Гусенечный автомобиль");
        CrawlerCarLabel.setBorder(BorderFactory.createBevelBorder(0));
        CrawlerCarLabel.setBounds(10, 70, 280, 50);
        CrawlerCarLabel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            public boolean canImport(TransferSupport support)
            {
                return false;
            }

            protected Transferable createTransferable(JComponent c) {
                return new StringSelection(((JLabel) c).getText());
            }
        });
        CrawlerCarLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseAction(e);
            }
        });
        groupPanelBody.add(CrawlerCarLabel);

        JLabel CarLabel = new JLabel("Экскаватор");
        CarLabel.setBorder(BorderFactory.createBevelBorder(0));
        CarLabel.setBounds(10, 70, 280, 50);
        CarLabel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            public boolean canImport(TransferSupport support) {
                return false;
            }

            protected Transferable createTransferable(JComponent c) {
                return new StringSelection(((JLabel) c).getText());
            }
        });
        CarLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseAction(e);
            }
        });
        groupPanelBody.add(CarLabel);



        JPanel groupPanelOptions = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        groupPanelOptions.setBorder(BorderFactory.createTitledBorder("Параметры"));
        groupPanelOptions.setBounds(10, 120, 200, 240);
        frame.getContentPane().add(groupPanelOptions);

        JLabel maxSpeedLabel = new JLabel("Макс. скорость");
        maxSpeedLabel.setBounds(10, 10, 280, 50);
        groupPanelOptions.add(maxSpeedLabel);

        JSpinner maxSpeedSpinner = new JSpinner(new SpinnerNumberModel(100, 100, 1000, 1));
        maxSpeedSpinner.setBounds(150, 70, 50, 50);
        groupPanelOptions.add(maxSpeedSpinner);

        JLabel weightLabel = new JLabel("Вес автомобиля");
        weightLabel.setBounds(10, 130, 280, 50);
        groupPanelOptions.add(weightLabel);

        JSpinner weightSpinner = new JSpinner(new SpinnerNumberModel(100, 100, 1000, 1));
        weightSpinner.setBounds(150, 190, 50, 50);
        groupPanelOptions.add(weightSpinner);


        JCheckBox checkBoxFrontLadle = new JCheckBox("Передний ковш",true);
        groupPanelOptions.add(checkBoxFrontLadle );

        JCheckBox checkBoxBackAntenna = new JCheckBox("Задняя антенна",true);
        groupPanelOptions.add(checkBoxBackAntenna);

        JCheckBox checkBoxStand = new JCheckBox("Мигалка",true);
        groupPanelOptions.add(checkBoxStand );



        carPanel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            public boolean importData(TransferSupport info) {
                String viewCar;
                Transferable t = info.getTransferable();

                try {
                    if (t.getTransferData(DataFlavor.stringFlavor) instanceof IColoring) {
                        if (car != null && car.getClass() == CrawlerCar.class) {
                            ((CrawlerCar) car).setDrawingRollers((IColoring) t.getTransferData(DataFlavor.stringFlavor));
                            carPanel.repaint();
                        }
                        return true;
                    } else {
                        viewCar = (String) t.getTransferData(DataFlavor.stringFlavor);
                    }
                } catch (Exception e) {
                    return false;
                }
                switch (viewCar) {
                    case "Гусенечный автомобиль":
                        car = new Car((int) maxSpeedSpinner.getValue(), (int) weightSpinner.getValue(), Color.WHITE);
                        car.setPosition(30, 30, 150, 60);
                        break;
                    case "Экскаватор":
                        car = new CrawlerCar((int) maxSpeedSpinner.getValue(), (int) weightSpinner.getValue(), Color.WHITE, Color.BLACK,
                                checkBoxFrontLadle.isSelected(), checkBoxBackAntenna.isSelected(), checkBoxStand.isSelected(),3, 0);
                        car.setPosition(30, 30, 320, 60);
                        break;
                    default:
                        return false;
                }
                carPanel.setCar(car);
                carPanel.repaint();
                return true;
            }

            public boolean canImport(TransferHandler.TransferSupport info) {
                try {
                    return info.isDataFlavorSupported(DataFlavor.stringFlavor) || info.getTransferable().getTransferData(DataFlavor.stringFlavor) instanceof IColoring;
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        frame.getContentPane().add(carPanel);

        JPanel groupPanelRollers = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        groupPanelRollers.setBorder(BorderFactory.createTitledBorder("Тип катков"));
        groupPanelRollers.setBounds(540 , 230, 170, 150);
        frame.getContentPane().add(groupPanelRollers);


        JLabel SimpleRollersLabel = new JLabel("Обычные катки");
        SimpleRollersLabel.setBorder(BorderFactory.createBevelBorder(0));
        SimpleRollersLabel.setBounds(10, 70, 280, 50);
        SimpleRollersLabel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            public boolean canImport(TransferSupport support) {
                return false;
            }

            protected Transferable createTransferable(JComponent c) {
                return new Transferable() {

                    public DataFlavor[] getTransferDataFlavors() {
                        return new DataFlavor[0];
                    }

                    public boolean isDataFlavorSupported(DataFlavor flavor) {
                        return flavor == DataFlavor.stringFlavor;
                    }

                    public Object getTransferData(DataFlavor flavor) {
                        return new DrawingRollers();
                    }
                };
            }
        });
        SimpleRollersLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseAction(e);
            }
        });
        groupPanelRollers.add(SimpleRollersLabel);




        JLabel CircleRollersLabel = new JLabel("Овальные катки");
        CircleRollersLabel.setBorder(BorderFactory.createBevelBorder(0));
        CircleRollersLabel.setBounds(10, 70, 280, 50);
        CircleRollersLabel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            public boolean canImport(TransferSupport support) {
                return false;
            }

            protected Transferable createTransferable(JComponent c) {
                return new Transferable() {

                    public DataFlavor[] getTransferDataFlavors() {
                        return new DataFlavor[0];
                    }


                    public boolean isDataFlavorSupported(DataFlavor flavor) {
                        return flavor == DataFlavor.stringFlavor;
                    }


                    public Object getTransferData(DataFlavor flavor) {
                        return new DrawingCircleRollers();
                    }
                };
            }
        });
        CircleRollersLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseAction(e);
            }
        });
        groupPanelRollers.add(CircleRollersLabel);


        JLabel SquareRollersLabel = new JLabel("Квадратные катки");
        SquareRollersLabel.setBorder(BorderFactory.createBevelBorder(0));
        SquareRollersLabel.setBounds(10, 70, 280, 50);
        SquareRollersLabel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            public boolean canImport(TransferSupport support) {
                return false;
            }

            protected Transferable createTransferable(JComponent c) {
                return new Transferable() {

                    public DataFlavor[] getTransferDataFlavors() {
                        return new DataFlavor[0];
                    }


                    public boolean isDataFlavorSupported(DataFlavor flavor) {
                        return flavor == DataFlavor.stringFlavor;
                    }


                    public Object getTransferData(DataFlavor flavor) {
                        return new DrawingSquareRollers();
                    }
                };
            }
        });
        SquareRollersLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseAction(e);
            }
        });
        groupPanelRollers.add(SquareRollersLabel);


        JPanel groupPanelColors = new JPanel(new GridLayout(5, 2, 5, 5));
        groupPanelColors.setBorder(BorderFactory.createTitledBorder("Выбор цвета"));
        groupPanelColors.setBounds(540, 10, 300, 200);
        frame.getContentPane().add(groupPanelColors);

        JLabel mainColorLabel = new JLabel("Основной цвет");
        mainColorLabel.setBorder(BorderFactory.createBevelBorder(0));
        mainColorLabel.setBounds(10, 10, 280, 50);
        mainColorLabel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            public boolean importData(TransferSupport info) {
                Transferable t = info.getTransferable();
                Color color;
                try {
                    color = (Color) t.getTransferData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    return false;
                }
                if (car != null) {
                    car.mainColor = color;
                    carPanel.repaint();
                }
                return true;
            }

            public boolean canImport(TransferHandler.TransferSupport info) {
                try {
                    return info.getTransferable().getTransferData(DataFlavor.stringFlavor).getClass() == Color.class;
                } catch (UnsupportedFlavorException | IOException e) {
                    return false;
                }
            }
        });
        groupPanelColors.add(mainColorLabel);

        JLabel dopColorLabel = new JLabel("Дополнительный цвет");
        dopColorLabel.setBorder(BorderFactory.createBevelBorder(0));
        dopColorLabel.setBounds(10, 70, 280, 50);
        dopColorLabel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }


            public boolean importData(TransferSupport info) {
                Transferable t = info.getTransferable();
                Color color;
                try {
                    color = (Color) t.getTransferData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    return false;
                }
                if (car != null) {
                    if (car.getClass() == CrawlerCar.class) {
                        ((CrawlerCar) car).setDopColor(color);
                        carPanel.repaint();
                    }
                }
                return true;
            }

            public boolean canImport(TransferHandler.TransferSupport info) {
                try {
                    return info.getTransferable().getTransferData(DataFlavor.stringFlavor).getClass() == Color.class;
                } catch (UnsupportedFlavorException | IOException e) {
                    return false;
                }
            }
        });
        groupPanelColors.add(dopColorLabel);

        setColors(Color.RED, groupPanelColors);
        setColors(Color.ORANGE, groupPanelColors);
        setColors(Color.BLUE, groupPanelColors);
        setColors(Color.YELLOW, groupPanelColors);
        setColors(Color.CYAN, groupPanelColors);
        setColors(Color.GREEN, groupPanelColors);
        setColors(Color.BLACK, groupPanelColors);
        setColors(Color.WHITE, groupPanelColors);



        addCarButton.addActionListener(e -> {
            if (car == null) {
                JOptionPane.showMessageDialog(frame, "Сначала создайте автомобиль!", "Добавление автомобиля", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (eventAddCar != null) {
                eventAddCar.CarDelegate(car);
            }
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        addCarButton.setBounds(220, 230, 150, 50);
        frame.add(addCarButton);


        cancelButton.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
        cancelButton.setBounds(370, 230, 150, 50);
        frame.add(cancelButton);
    }


    private void setColors(Color color, JPanel groupPanelColors) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setTransferHandler(new TransferHandler() {
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            public boolean canImport(TransferSupport support) {
                return false;
            }

            protected Transferable createTransferable(JComponent c) {
                return new Transferable() {
                    @Override
                    public DataFlavor[] getTransferDataFlavors() {
                        return new DataFlavor[0];
                    }

                    @Override
                    public boolean isDataFlavorSupported(DataFlavor flavor) {
                        return flavor == DataFlavor.stringFlavor;
                    }

                    @Override
                    public Object getTransferData(DataFlavor flavor) {
                        return panel.getBackground();
                    }
                };
            }
        });
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseAction(e);
            }
        });
        panel.setBounds(10, 10, 100, 100);
        groupPanelColors.add(panel);
    }


    public void addEvent(IDelegate event) {
        eventAddCar = event;
    }

    public void mouseAction(MouseEvent e){
        if (SwingUtilities.isLeftMouseButton(e)) {
            JComponent component = (JComponent) e.getSource();
            TransferHandler handler = component.getTransferHandler();
            handler.exportAsDrag(component, e, TransferHandler.COPY);
        }
    }
}

