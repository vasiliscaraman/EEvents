package eevents;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.text.*;

@SuppressWarnings("serial")
public class LoginScreen extends JFrame {

    private JTextField username = new JTextField(10), parola = new JPasswordField(10);

    public LoginScreen() {
        super("Login");
        JPanel nord = new JPanel();
        nord.add(new JLabel("Nu ai cont? Apasa aici"));
        JButton register = new JButton("Register");
        register.addActionListener(e -> {
            this.dispose();
            @SuppressWarnings("unused")
            RegisterScreen r = new RegisterScreen();
        });
        nord.add(register);
        this.add(nord, BorderLayout.NORTH);
        JPanel centru = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        for (int i = 0; i < 2; i++) {
            gbc.gridx = 0;
            if (i == 0) {
                centru.add(new JLabel("Username"), gbc);
                gbc.gridx++;
                centru.add(username, gbc);
            } else {
                centru.add(new JLabel("Parola"), gbc);
                gbc.gridx++;
                centru.add(parola, gbc);
            }
            gbc.gridy++;
        }
        this.add(centru);
        JPanel sud = new JPanel();
        JButton b2 = new JButton("Login");
        b2.addActionListener(e -> {
            Date curenta = new Date(System.currentTimeMillis());
            SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
            Date dataEvent;
            try {
                String line;
                BufferedReader stergVechi = new BufferedReader(new FileReader("Evenimente.txt"));
                ArrayList<String> lista = new ArrayList<>();
                while ((line = stergVechi.readLine()) != null) {
                    String[] cutie = line.split(":");
                    dataEvent = form.parse(cutie[3]);
                    if (dataEvent.getTime() - curenta.getTime() >= 0) {
                        lista.add(line);
                    }
                }
                stergVechi.close();

                stergVechi = new BufferedReader(new FileReader("EvenimenteUseri.txt"));
                ArrayList<String> lista2 = new ArrayList<>();
                while ((line = stergVechi.readLine()) != null) {
                    String[] cutie = line.split(":");
                    dataEvent = form.parse(cutie[2]);
                    if (dataEvent.getTime() - curenta.getTime() >= 0) {
                        lista2.add(line);
                    }
                }

                Writer file = new FileWriter(new File("Evenimente.txt"));
                for (int i = 0; i < lista.size(); i++) {
                    if (i != lista.size() - 1) {
                        file.write(lista.get(i) + "\n");
                    } else {
                        file.write(lista.get(i));
                    }
                }
                file.close();

                file = new FileWriter(new File("EvenimenteUseri.txt"));
                for (int i = 0; i < lista2.size(); i++) {
                    if (i != lista2.size() - 1) {
                        file.write(lista2.get(i) + "\n");
                    } else {
                        file.write(lista2.get(i));
                    }
                }
                file.close();

            } catch (Exception e1) {
            }

            try {
                String currentLine;
                BufferedReader file = new BufferedReader(new FileReader(new File("Data.txt")));
                boolean found = false;
                while ((currentLine = file.readLine()) != null) {
                    String[] data = currentLine.split(":");
                    if (data[0].equals(username.getText()) && data[1].equals(parola.getText())) {
                        @SuppressWarnings("unused")
                        Dashboard d = new Dashboard(username.getText());
                        BufferedReader evenimenteUseri = new BufferedReader(new FileReader(new File("EvenimenteUseri.txt")));
                        String linie;
                        while ((linie = evenimenteUseri.readLine()) != null) {
                            String[] boxes = linie.split(":");
                            if (boxes[0].equals(username.getText())) {
                                Date viitor;
                                try {
                                    viitor = form.parse(boxes[2]);
                                    System.out.println(viitor.getTime()+" ~ "+curenta.getTime());
                                    long diff = viitor.getTime() - curenta.getTime();
                                    long zile = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                                    if (zile <= 30 && zile >= 0) {
                                        if (zile == 0) {
                                            JOptionPane.showMessageDialog(null, "Evenimentul " + boxes[1] + " va avea loc astazi!");
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Evenimentul " + boxes[1] + " va avea loc in " + zile + " zile!");
                                        }
                                    }
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }

                            }
                        }
                        evenimenteUseri.close();
                        this.dispose();
                        found = true;
                        break;
                    }
                }
                file.close();
                if (found == false) {
                    JOptionPane.showMessageDialog(this, "Acest cont nu exista!");
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        });
        sud.add(b2);
        this.add(sud, BorderLayout.SOUTH);
        this.setBounds(600, 350, 300, 175);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        LoginScreen l = new LoginScreen();
    }
}
