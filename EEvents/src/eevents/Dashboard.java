package eevents;

import java.awt.*;
import javax.swing.*;
import java.io.*;

@SuppressWarnings("serial")
public class Dashboard extends JFrame {

    public Dashboard(String email) {
        super("Dashboard");
        JPanel nord = new JPanel();
        nord.add(new JLabel("Bun venit " + email + "."));
        this.add(nord, BorderLayout.NORTH);

        JPanel centru = new JPanel(new GridBagLayout());
        try {
            String currentLine;
            String[] currentUser;
            BufferedReader user = new BufferedReader(new FileReader(new File("Data.txt")));
            while (true) {
                currentUser = user.readLine().split(":");
                if (currentUser[0].equals(email)) {
                    break;
                }
            }
            user.close();

            BufferedReader file = new BufferedReader(new FileReader(new File("Evenimente.txt")));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            while ((currentLine = file.readLine()) != null) {
                String[] evenimente = currentLine.split(":");

                for (int i = 2; i < 6; i++) {
                    if (evenimente[1].equals(currentUser[i])) {
                        JPanel j = new JPanel();
                        j.add(new JLabel("Nume: " + evenimente[0] + " | "));
                        j.add(new JLabel("Tip eveniment: " + evenimente[1] + " | "));
                        j.add(new JLabel("Pret bilet: " + evenimente[2] + " | "));
                        j.add(new JLabel("In data de: " + evenimente[3]));
                        JButton interesat = new JButton("Sunt interesat!");
                        interesat.addActionListener(e -> {
                            try {
                                Writer scriu = new FileWriter(new File("EvenimenteUseri.txt"), true);

                                StringBuffer sb = new StringBuffer();
                                sb.append("\n");
                                sb.append(email);
                                sb.append(":");
                                sb.append(evenimente[0]);
                                sb.append(":");
                                sb.append(evenimente[3]);
                                scriu.write(sb.toString());
                                scriu.close();
                                JOptionPane.showMessageDialog(this, "V-ati inscris la evenimentul " + evenimente[0] + "!");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        });
                        j.add(interesat);
                        centru.add(j, gbc);
                        gbc.gridy++;
                    }
                }
            }
            file.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        JScrollPane scroll = new JScrollPane(centru);
        this.add(scroll);

        JPanel sud = new JPanel();
        JButton EvenimenteleMele = new JButton("Evenimentele Mele");
        JButton CreazaEveniment = new JButton("Creaza Eveniment");
        JButton SchimbaParolaSiPreferinte = new JButton("Schimba Parola si Preferinte");
        JButton logout = new JButton("Delogare");

        EvenimenteleMele.addActionListener(e -> {
            this.dispose();
            @SuppressWarnings("unused")
            EvenimenteleMele ev = new EvenimenteleMele(email);
        });

        CreazaEveniment.addActionListener(e -> {
            this.dispose();
            @SuppressWarnings("unused")
            CreareEveniment ce = new CreareEveniment(email);
        });

        SchimbaParolaSiPreferinte.addActionListener(e -> {
            this.dispose();
            @SuppressWarnings("unused")
            SchimbaParolaSiPreferinte sp = new SchimbaParolaSiPreferinte(email);
        });

        logout.addActionListener(e -> {
            this.dispose();
            @SuppressWarnings("unused")
            LoginScreen log = new LoginScreen();
        });

        sud.add(EvenimenteleMele);
        sud.add(SchimbaParolaSiPreferinte);
        if (email.equals("admin")) {
            sud.add(CreazaEveniment);
        }
        sud.add(logout);
        this.add(sud, BorderLayout.SOUTH);
        this.setBounds(300, 300, 810, 500);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Dashboard d = new Dashboard("admin");
        d.setSize(680, 500);
        d.setVisible(true);

    }
}
