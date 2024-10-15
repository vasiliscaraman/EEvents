package eevents;

import java.awt.*;
import javax.swing.*;
import java.io.*;

@SuppressWarnings("serial")
public class RegisterScreen extends JFrame {

    private JTextField username = new JTextField(10), parola = new JPasswordField(10);

    public RegisterScreen() {
        super("Register");
        JPanel nord = new JPanel();
        nord.add(new JLabel("Ai deja cont? Apasa aici"));
        JButton login = new JButton("Login");
        login.addActionListener(e -> {
            this.dispose();
            @SuppressWarnings("unused")
            LoginScreen login1 = new LoginScreen();
        });
        nord.add(login);
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
        JButton b2 = new JButton("Register");
        b2.addActionListener(e -> {
            try {
                if (username.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$") && !username.getText().equals(null)) {
                    if (!parola.equals(null) && parola.getText().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,16}$")) {
                        Writer file = new FileWriter(new File("Data.txt"), true);
                        StringBuffer sb = new StringBuffer();
                        sb.append("\n");
                        sb.append(username.getText());
                        sb.append(":");
                        sb.append(parola.getText());
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
                        file.write(sb.toString());
                        file.close();
                        JOptionPane.showMessageDialog(this, "Inregistrare completa! Va vom redirectiona catre Dashboard");
                        this.dispose();
                        @SuppressWarnings("unused")
                        Dashboard logined = new Dashboard(username.getText());
                    } else {
                        JOptionPane.showMessageDialog(this, "Nu ati introdus o parola corecta!\nParola trebuie sa contina macar 1 caracter mic, 1 caracter mare, 1 cifra si sa aiba lungimea de minim 8!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Nu ati introdus o adresa de email valabila");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        sud.add(b2);
        this.add(sud, BorderLayout.SOUTH);
        this.setBounds(600, 350, 300, 220);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
