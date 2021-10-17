package fr.udem.gui;

import fr.udem.lsystem.LSystem;
import fr.udem.turtle.Turtle;
import fr.udem.turtle.TurtleDraw;
import fr.udem.utils.JSONFile;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Screen extends JFrame {
    private int n;
    private String fileName;

    private LSystem lsystem;
    private Turtle turtle;

    public Screen(String fileName, int n) throws IOException {
        this.fileName = fileName;
        this.n = n;

        this.lsystem = new LSystem();

        init();
    }

    private void init() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Lindenmayer System");
        this.setResizable(false);
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        this.turtle = new TurtleDraw(g);

        try {
            JSONFile.readJSONFile(fileName, this.lsystem, this.turtle);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Rectangle2D rec = lsystem.tell(this.turtle, this.lsystem.getAxiom(), this.n);
            //setSize((int)rec.getWidth() + 10, (int)rec.getHeight() + 10);
            setSize(800, 800);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        setLocationRelativeTo(null);
    }
}
