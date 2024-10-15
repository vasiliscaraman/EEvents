package eevents;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class SchimbaParolaSiPreferinte extends JFrame {

    JTextField parola = new JPasswordField(10);

    SchimbaParolaSiPreferinte(String email) {
        super("Schimbare Parola");
        JPanel nord = new JPanel();
        nord.add(new JLabel("Bun venit " + email + "."));
        this.add(nord, BorderLayout.NORTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        JPanel centru = new JPanel(new GridBagLayout());
        centru.add(new JLabel("Parola Noua:"), gbc);
        gbc.gridx++;
        centru.add(parola, gbc);
        this.add(centru);

        JPanel est = new JPanel(new GridLayout(5, 1, 10, 10));
        est.add(new JLabel("Interesele mele:"));
        JCheckBox muzica = new JCheckBox("muzica");
        JCheckBox teatru = new JCheckBox("teatru");
        JCheckBox pictura = new JCheckBox("pictura");
        JCheckBox sport = new JCheckBox("sport");
        est.add(muzica);
        est.add(pictura);
        est.add(sport);
        est.add(teatru);
        this.add(est, BorderLayout.EAST);

        JPanel sud = new JPanel();
        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Inapoi");
        ok.addActionListener(e -> {
            if (parola.getText().equals("") || parola.getText().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,16}$")) {
                Writer file;
                BufferedReader reader;
                ArrayList<String> date = new ArrayList<>();
                boolean firstTime = false;
                try {
                    reader = new BufferedReader(new FileReader("Data.txt"));
                    String linieCurenta, parolaVeche = "";
                    int index = 0;
                    while ((linieCurenta = reader.readLine()) != null) {
                        date.add(linieCurenta);
                        String[] token = linieCurenta.split(":");
                        if (token[0].equals(email) && firstTime == false) {
                            parolaVeche = token[1];
                            firstTime = true;
                        }
                        if (firstTime == false) {
                            index++;
                        }
                    }

                    file = new FileWriter(new File("Data.txt"));

                    StringBuffer sb = new StringBuffer();
                    sb.append(email);
                    sb.append(":");
                    if (parola.getText().equals("")) {
                        sb.append(parolaVeche);
                    } else {
                        sb.append(parola.getText());
                    }
                    sb.append(":");
                    if (muzica.isSelected()) {
                        sb.append("muzica");
                    }
                    sb.append(":");
                    if (pictura.isSelected()) {
                        sb.append("pictura");
                    }
                    sb.append(":");
                    if (sport.isSelected()) {
                        sb.append("sport");
                    }
                    sb.append(":");
                    if (teatru.isSelected()) {
                        sb.append("teatru");
                    } else {
                        sb.append(" ");
                    }
                    date.set(index, sb.toString());
                    for (int i = 0; i < date.size(); i++) {
                        if (i == date.size() - 1) {
                            file.write(date.get(i));
                        } else {
                            file.write(date.get(i) + "\n");
                        }
                    }
                    file.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                JOptionPane.showMessageDialog(this, "Preferinte schimbate cu succes! Va vom redirectiona catre Dashboard");
                @SuppressWarnings("unused")
                Dashboard d = new Dashboard(email);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Nu ati introdus o parola corecta!\nParola trebuie"
                        + " sa contina macar 1 caracter mic, 1 caracter mare, 1 cifra si sa aiba lungimea de minim 8!");
            }
        });

        cancel.addActionListener(e -> {
            this.dispose();
            @SuppressWarnings("unused")
            Dashboard d = new Dashboard(email);
        });

        sud.add(ok);
        sud.add(cancel);
        this.add(sud, BorderLayout.SOUTH);
        this.setBounds(600, 350, 300, 225);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
