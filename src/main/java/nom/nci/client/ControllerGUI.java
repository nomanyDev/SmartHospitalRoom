package nom.nci.client;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ds.service1.SmartTVServiceGrpc;
import ds.service1.TVResponse;
import ds.service1.VolumeRequest;
import ds.service1.ChannelRequest;
import ds.service2.LightingServiceGrpc;
import ds.service2.LightRequest;
import ds.service2.LightResponse;
import ds.service2.LightLevelRequest;
import ds.service3.TemperatureControlServiceGrpc;
import ds.service3.TempRequest;
import ds.service3.TempResponse;
import ds.service4.SmartBedServiceGrpc;
import ds.service4.PositionRequest;
import ds.service4.BedResponse;
import ds.service5.PatientInfoServiceGrpc;
import ds.service5.PatientRequest;
import ds.service5.PatientResponse;

public class ControllerGUI implements ActionListener {

    private JTextField volumeField, reply1;
    private JTextField entry2, reply2;
    private JTextField entry3, reply3;
    private JTextField entry4, reply4;
    private JTextField entry5, reply5;

    private JPanel getSmartTVServiceJPanel() {
        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.X_AXIS);

        JLabel label = new JLabel("Enter Volume (0-100):");
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        volumeField = new JTextField("", 10);
        panel.add(volumeField);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton button = new JButton("Set Volume");
        button.addActionListener(this);
        button.setActionCommand("Set Volume");
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        reply1 = new JTextField("", 10);
        reply1.setEditable(false);
        panel.add(reply1);

        panel.setLayout(boxlayout);

        return panel;
    }

    private JPanel getLightingServiceJPanel() {
        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.X_AXIS);

        JLabel label = new JLabel("Light Control:");
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton entry2 = new JButton("Motion Detection");
        entry2.addActionListener(this);
        entry2.setActionCommand("Detect Motion");
        panel.add(entry2);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton turnOnButton = new JButton("Turn On Light");
        turnOnButton.addActionListener(this);
        turnOnButton.setActionCommand("Turn On Light");
        panel.add(turnOnButton);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton turnOffButton = new JButton("Turn Off Light");
        turnOffButton.addActionListener(this);
        turnOffButton.setActionCommand("Turn Off Light");
        panel.add(turnOffButton);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));



        JLabel levelLabel = new JLabel("Set Light Level (0-100):");
        panel.add(levelLabel);
        JTextField lightLevelField = new JTextField("", 10);
        panel.add(lightLevelField);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton setLevelButton = new JButton("Set Light Level");
        setLevelButton.addActionListener(e -> {
            try {
                int level = Integer.parseInt(lightLevelField.getText());
                if (level < 0 || level > 100) {
                    reply2.setText("Light level must be between 0 and 100.");
                } else {
                    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052).usePlaintext().build();
                    LightingServiceGrpc.LightingServiceBlockingStub blockingStub = LightingServiceGrpc.newBlockingStub(channel);

                    LightLevelRequest request = LightLevelRequest.newBuilder().setLevel(level).build();
                    LightResponse response = blockingStub.setLightLvl(request);
                    reply2.setText("Light level set to " + level);

                    channel.shutdown();
                }
            } catch (NumberFormatException ex) {
                reply2.setText("Invalid light level format.");
            }
        });
        panel.add(setLevelButton);

        reply2 = new JTextField("", 20);
        reply2.setEditable(false);
        panel.add(reply2);

        panel.setLayout(boxlayout);

        return panel;
    }

    private JPanel getTemperatureControlServiceJPanel() {
        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.X_AXIS);

        JLabel label = new JLabel("Enter Temperature (12-35):");
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        entry3 = new JTextField("", 10);
        panel.add(entry3);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton button = new JButton("Set Temperature");
        button.addActionListener(this);
        button.setActionCommand("Set Temperature");
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        reply3 = new JTextField("", 20);
        reply3.setEditable(false);
        panel.add(reply3);

        panel.setLayout(boxlayout);

        return panel;
    }

    private JPanel getSmartBedServiceJPanel() {
        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.X_AXIS);

        JLabel label = new JLabel("Enter Bed Position (0-80):");
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        entry4 = new JTextField("", 10);
        panel.add(entry4);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton button = new JButton("Set Bed Position");
        button.addActionListener(this);
        button.setActionCommand("Set Bed Position");
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton getStatusButton = new JButton("Get Status Info");
        getStatusButton.addActionListener(this);
        getStatusButton.setActionCommand("GetStatusInfo");
        panel.add(getStatusButton);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        reply4 = new JTextField("", 20);
        reply4.setEditable(false);
        panel.add(reply4);

        panel.setLayout(boxlayout);

        return panel;
    }

    private JPanel getPatientInfoServiceJPanel() {
        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.X_AXIS);

        JLabel label = new JLabel("Enter Patient ID for Info Request:");
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        entry5 = new JTextField("", 10);
        panel.add(entry5);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton button = new JButton("Invoke Patient Info Service");
        button.addActionListener(this);
        button.setActionCommand("Invoke Patient Info Service");
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        reply5 = new JTextField("", 20);
        reply5.setEditable(false);
        panel.add(reply5);

        panel.setLayout(boxlayout);

        return panel;
    }

    public static void main(String[] args) {

        ControllerGUI gui = new ControllerGUI();
        gui.build();
    }

    private void build() {
        JFrame frame = new JFrame("Smart Hospital Room");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);

        panel.setBorder(new EmptyBorder(new Insets(50, 100, 50, 100)));

        panel.add(getSmartTVServiceJPanel());
        panel.add(getLightingServiceJPanel());
        panel.add(getTemperatureControlServiceJPanel());
        panel.add(getSmartBedServiceJPanel());
        panel.add(getPatientInfoServiceJPanel());

        frame.setSize(500, 400);  // Adjusted size to fit new layout
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String actionCommand = button.getActionCommand();

        ManagedChannel channel = null;

        try {
            if (actionCommand.equals("Set Volume")) {
                channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();
                SmartTVServiceGrpc.SmartTVServiceBlockingStub blockingStub = SmartTVServiceGrpc.newBlockingStub(channel);

                try {
                    int volume = Integer.parseInt(volumeField.getText());
                    if (volume < 0 || volume > 100) {
                        reply1.setText("Volume must be between 0 and 100.");
                    } else {
                        VolumeRequest request = VolumeRequest.newBuilder().setVolume(volume).build();
                        TVResponse response = blockingStub.setVolume(request);
                        reply1.setText(response.getMessage());
                        System.out.println("service 1  to be invoked ...");
                    }
                } catch (NumberFormatException ex) {
                    reply1.setText("Invalid volume format.");
                }

            } else if (actionCommand.equals("Channel Up")) {
                channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();
                SmartTVServiceGrpc.SmartTVServiceBlockingStub blockingStub = SmartTVServiceGrpc.newBlockingStub(channel);

                ChannelRequest request = ChannelRequest.newBuilder().setChannel(1).build(); // Передаем значение 1 как индикацию переключения
                TVResponse response = blockingStub.chnlUp(request);

                reply2.setText("Channel switched to next.");

            } else if (actionCommand.equals("Channel Down")) {
                channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();
                SmartTVServiceGrpc.SmartTVServiceBlockingStub blockingStub = SmartTVServiceGrpc.newBlockingStub(channel);

                ChannelRequest request = ChannelRequest.newBuilder().setChannel(1).build(); // Передаем значение 1 как индикацию переключения
                TVResponse response = blockingStub.chnlDown(request);

                reply2.setText("Channel switched to previous.");

            }
            else if (actionCommand.equals("Turn On Light")) {
                channel = ManagedChannelBuilder.forAddress("localhost", 50052).usePlaintext().build();
                LightingServiceGrpc.LightingServiceBlockingStub blockingStub = LightingServiceGrpc.newBlockingStub(channel);

                LightRequest request = LightRequest.newBuilder().setStatus("on").build();
                LightResponse response = blockingStub.turnOn(request);

                reply2.setText("Light turned on.");
                System.out.println("service 2  to be invoked ...");

            } else if (actionCommand.equals("Turn Off Light")) {
                channel = ManagedChannelBuilder.forAddress("localhost", 50052).usePlaintext().build();
                LightingServiceGrpc.LightingServiceBlockingStub blockingStub = LightingServiceGrpc.newBlockingStub(channel);

                LightRequest request = LightRequest.newBuilder().setStatus("off").build();
                LightResponse response = blockingStub.turnOff(request);

                reply2.setText("Light turned off.");

            } else if (actionCommand.equals("Set Temperature")) {
                channel = ManagedChannelBuilder.forAddress("localhost", 50053).usePlaintext().build();
                TemperatureControlServiceGrpc.TemperatureControlServiceBlockingStub blockingStub = TemperatureControlServiceGrpc.newBlockingStub(channel);

                try {
                    int temperature = Integer.parseInt(entry3.getText());
                    if (temperature < 12 || temperature > 35) {
                        reply3.setText("Temperature must be between 12 and 35.");
                    } else {
                        TempRequest request = TempRequest.newBuilder().setTemperature(temperature).build();
                        TempResponse response = blockingStub.setTemp(request);
                        reply3.setText("Current temperature set to " + response.getCurrentTemp());
                        System.out.println("service 3  to be invoked ...");
                    }
                } catch (NumberFormatException ex) {
                    reply3.setText("Invalid temperature format.");
                }

            } else if (actionCommand.equals("Set Bed Position")) {
                channel = ManagedChannelBuilder.forAddress("localhost", 50054).usePlaintext().build();
                SmartBedServiceGrpc.SmartBedServiceBlockingStub blockingStub = SmartBedServiceGrpc.newBlockingStub(channel);

                try {
                    int angle = Integer.parseInt(entry4.getText());
                    if (angle < 0 || angle > 80. ) {
                        reply4.setText("Position must be between 0 and 80.");
                    }  else {
                        PositionRequest request = PositionRequest.newBuilder().setAngle(angle).build();
                        BedResponse response = blockingStub.setPosition(request);
                        reply4.setText("Bed position set to " + angle);
                        System.out.println("service 4  to be invoked ...");
                    }
                } catch (NumberFormatException ex) {
                    reply4.setText("Invalid position format.");
                }


            } else if (actionCommand.equals("Invoke Patient Info Service")) {
                channel = ManagedChannelBuilder.forAddress("localhost", 50055).usePlaintext().build();
                PatientInfoServiceGrpc.PatientInfoServiceBlockingStub blockingStub = PatientInfoServiceGrpc.newBlockingStub(channel);

                try {
                    int patientId = Integer.parseInt(entry5.getText());
                    PatientRequest request = PatientRequest.newBuilder().setPatientId(patientId).build();
                    PatientResponse response = blockingStub.getPatientInfo(request);

                    String fullName = response.getFullName();
                    String gp = response.getGp();
                    String treatmentInfo = response.getTreatmentInfo();

                    String result = String.format("Full Name: %s\nGP: %s\nTreatment Info: %s", fullName, gp, treatmentInfo);
                    reply5.setText(result);
                    System.out.println("service 5  to be invoked ...");
                } catch (NumberFormatException ex) {
                    reply5.setText("Invalid patient ID format.");
                }
            }
        } finally {
            if (channel != null) {
                channel.shutdown();
            }
        }
    }
}