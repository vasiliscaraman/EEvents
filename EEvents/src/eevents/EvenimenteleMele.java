package eevents;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

@SuppressWarnings("serial")
public class EvenimenteleMele extends JFrame {

    public EvenimenteleMele(String email) {
        super("Evenimentele mele");
        try {
            JPanel centru = new JPanel(new GridBagLayout());
            BufferedReader buf = new BufferedReader(new FileReader("EvenimenteUseri.txt"));
            String currentLine;
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            while ((currentLine = buf.readLine()) != null) {
                String[] date = currentLine.split(":");
                if (date[0].equals(email)) {
                    BufferedReader buf2 = new BufferedReader(new FileReader("Evenimente.txt"));
                    String currentLine2;
                    while ((currentLine2 = buf2.readLine()) != null) {
                        String[] date2 = currentLine2.split(":");
                        if (date2[0].equals(date[1])) {
                            JPanel j = new JPanel();
                            j.add(new JLabel("Nume: " + date2[0] + " | "));
                            j.add(new JLabel("Tip eveniment: " + date2[1] + " | "));
                            j.add(new JLabel("Pret bilet: " + date2[2] + " | "));
                            j.add(new JLabel("In data de: " + date2[3]));
                            JButton cumpara = new JButton("Cumparati bilet!");
                            cumpara.addActionListener(e -> {
                                int v = JOptionPane.showConfirmDialog(this, "Sunteti sigur ca doriti bilet?");
                                if (v == 0) {
                                    @SuppressWarnings("unused")
                                    Bilet bilet = new Bilet(email, date2[0]);
                                }
                            });
                            JButton sterge = new JButton("Sterge");
                            sterge.addActionListener(e -> {
                                ArrayList<String> lista = new ArrayList<>();
                                try {
                                    Writer file;
                                    BufferedReader buf3 = new BufferedReader(new FileReader("EvenimenteUseri.txt"));
                                    String currentLine3;
                                    while ((currentLine3 = buf3.readLine()) != null) {
                                        String[] date3 = currentLine3.split(":");
                                        if (!(date3[0].equals(date[0]) && date3[1].equals(date[1]))) {
                                            lista.add(currentLine3);
                                        }
                                    }
                                    buf3.close();
                                    file = new FileWriter(new File("EvenimenteUseri.txt"));
                                    for (int i = 0; i < lista.size(); i++) {
                                        if (i != lista.size() - 1) {
                                            file.write(lista.get(i) + "\n");
                                        } else {
                                            file.write(lista.get(i));
                                        }
                                    }
                                    file.close();
                                    this.dispose();
                                    @SuppressWarnings("unused")
                                    EvenimenteleMele em = new EvenimenteleMele(email);
                                } catch (IOException e1) {
                                }
                            });
                            j.add(sterge);
                            j.add(cumpara);
                            centru.add(j, gbc);
                            gbc.gridy++;
                        }
                    }
                    buf2.close();
                }
            }
            buf.close();
            JScrollPane scroll = new JScrollPane(centru);
            this.add(scroll);
            JButton inapoi = new JButton("Inapoi");
            inapoi.addActionListener(e -> {
                this.dispose();
                @SuppressWarnings("unused")
                Dashboard d = new Dashboard(email);
            });
            JPanel sud = new JPanel();
            sud.add(inapoi);
            this.add(sud, BorderLayout.SOUTH);
            this.setVisible(true);
            this.setBounds(500, 300, 800, 500);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
