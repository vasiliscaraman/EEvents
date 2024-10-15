package eevents;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.text.*;

@SuppressWarnings("serial")
public class CreareEveniment extends JFrame {

    CreareEveniment(String user) {
        super("Creare eveniment");
        JPanel nord = new JPanel();
        nord.add(new JLabel("Creati evenimentul dorit:"));
        this.add(nord, BorderLayout.NORTH);
        JPanel centru = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        centru.add(new JLabel("Nume Eveniment: "), gbc);
        gbc.gridx++;
        JTextField nume = new JTextField(10);
        centru.add(nume, gbc);
        gbc.gridx--;
        gbc.gridy++;
        centru.add(new JLabel("Categorie: "), gbc);
        gbc.gridx++;
        @SuppressWarnings({"rawtypes", "unchecked"})
        JComboBox categorie = new JComboBox(new String[]{"muzica", "pictura", "sport", "teatru"});
        centru.add(categorie, gbc);
        gbc.gridx--;
        gbc.gridy++;
        centru.add(new JLabel("Pret"));
        gbc.gridx++;
        JTextField pret = new JTextField(10);
        gbc.gridx--;
        gbc.gridy++;
        centru.add(pret);
        centru.add(new JLabel("Data: "), gbc);
        gbc.gridx++;
        JTextField data = new JTextField(10);
        centru.add(data, gbc);
        gbc.gridx--;
        gbc.gridy++;
        this.add(centru);
        JPanel sud = new JPanel();
        JButton add = new JButton("Adauga");
        add.addActionListener(e -> {
            try {
                Writer file = new FileWriter(new File("Evenimente.txt"), true);
                StringBuffer sb = new StringBuffer();
                sb.append("\n");
                sb.append(nume.getText());
                sb.append(":");
                sb.append(categorie.getSelectedItem());
                sb.append(":");
                sb.append(pret.getText());
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date date = sdf.parse(data.getText());
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                sb.append(":");
                sb.append(data.getText());
                file.write(sb.toString());
                file.close();
                JOptionPane.showMessageDialog(this, "Evenimentul a fost adaugat cu succes!");
            } catch (ParseException | IOException e1) {
                JOptionPane.showMessageDialog(this, "Data este incorect adaugata");
            }
        });
        sud.add(add);
        JButton back = new JButton("Inapoi la Dashboard");
        back.addActionListener(e -> {
            this.dispose();
            @SuppressWarnings("unused")
            Dashboard d = new Dashboard(user);
        });
        sud.add(back);
        this.add(sud, BorderLayout.SOUTH);
        this.setBounds(500, 300, 500, 240);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
