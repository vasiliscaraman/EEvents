package eevents;

import java.util.*;
import java.awt.*;
import java.awt.print.*;
import javax.swing.*;
import java.io.*;

@SuppressWarnings("serial")
public class Bilet extends JFrame implements Printable {

    public Bilet(String email, String denumireEveniment) {
        this.setTitle("Bilet pentru evenimentul " + denumireEveniment + "!");
        BufferedReader buf;
        try {
            buf = new BufferedReader(new FileReader("Evenimente.txt"));
            while (true) {
                String aux = buf.readLine();
                if (aux == null) {
                    break;
                }
                String elemente[] = aux.split(":");
                if (elemente[0].equals(denumireEveniment)) {
                    Random rand = new Random();
                    int nrRandom = rand.nextInt(1000000);
                    JLabel text = new JLabel(
                            "Iti multumim ca ai achizitionat biletul cu codul " + nrRandom + "," + " (pretul " + elemente[2]
                            + " lei) pentru evenimentul " + denumireEveniment + "!");
                    JPanel centru = new JPanel();
                    centru.add(text);
                    centru.add(new JLabel("Va asteptam cu drag!"));
                    JButton print = new JButton("Printeaza");
                    print.addActionListener(e -> {
                        PrinterJob imprimanta = PrinterJob.getPrinterJob();
                        imprimanta.setPrintable(this);
                        boolean b = imprimanta.printDialog();
                        if (b) {
                            try {
                                imprimanta.print();
                                this.dispose();
                            } catch (PrinterException e2) {
                            }
                        }
                    });
                    JButton ok = new JButton("Ok");
                    ok.addActionListener(e -> {
                        this.dispose();
                    });
                    JPanel sud = new JPanel();
                    sud.add(print);
                    sud.add(ok);
                    this.add(centru, BorderLayout.CENTER);
                    this.add(sud, BorderLayout.SOUTH);
                    break;
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(600, 500, 580, 120);
        this.setLocationRelativeTo(null);
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(pf.getImageableX(), pf.getImageableY());
        this.printAll(g);
        return PAGE_EXISTS;
    }
}
