package fr.udem.turtle;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class BasicTurtle implements Turtle {
    /**
     * Length of a step
     */
    private double step;

    /**
     * Rotation angle
     */
    private double delta;

    /**
     * State memory
     */
    private Stack<State> states;

    /**
     * Current position of the turtle
     */
    private double x, y;

    /**
     * Current angle to move
     */
    private double currAngle;

    private File file;

    private StringBuffer sb;

    /**
     * Constructor of {@link BasicTurtle}
     */
    public BasicTurtle(String postScriptFileName) {
        this.states = new Stack<>();
        this.sb = new StringBuffer();

        try {
            file = new File(postScriptFileName);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred when creating the file.");
            e.printStackTrace();
        }
    }

    public void writeFile() {
        this.sb.append("stroke\n"
                     + "%%Trailer\n"
                     + "%%EOF");
        try {
            FileWriter myWriter = new FileWriter(file.getName());
            myWriter.write(this.sb.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Draws a line of {@link BasicTurtle#step} length
     */
    @Override
    public void draw() {
        x += step * Math.cos(Math.toRadians(currAngle));
        y += step * Math.sin(Math.toRadians(currAngle));
        this.sb.append(x + " " + y + " lineto\n");
    }

    /**
     * Moves by {@link BasicTurtle#step} unit length, no drawing.
     */
    @Override
    public void move() {
        x += step * Math.cos(Math.toRadians(currAngle));
        y += step * Math.sin(Math.toRadians(currAngle));
        this.sb.append(x + " " + y + "newpath moveto\n");
    }

    /**
     * Turn right (clockwise) by unit angle.
     */
    @Override
    public void turnR() {
        currAngle += this.delta;
    }

    /**
     * Turn left (counter-clockwise) by unit angle.
     */
    @Override
    public void turnL() {
        currAngle -= this.delta;
    }

    /**
     * Saves turtle state
     */
    @Override
    public void push() {
        State st = new State(getPos(), currAngle);
        states.push(st);
        this.sb.append("currentpoint stroke newpath moveto\n");
        System.out.println("Push in the stack !");
    }

    /**
     * Recovers turtle state
     */
    @Override
    public void pop() {
        this.sb.append(x + " " + y + " moveto\n" + "stroke\n");

        State currSt = states.peek();
        x = currSt.getPos().getX();
        y = currSt.getPos().getY();
        currAngle = currSt.getAngle();
        states.pop();
        this.sb.append("stroke " + x + " " + y + " newpath " + "moveto\n");
        System.out.println("Pop in the stack !");
    }

    @Override
    public void stay() {
        // do nothing
    }

    @Override
    public Point2D getPos() {
        return new Point2D.Double(this.x, this.y);
    }

    /**
     * Turtle current x position
     * @return location of the turtle on x axe
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * Turtle current y position
     * @return location of the turtle on y axe
     */
    @Override
    public double getY() {
        return y;
    }

    /**
     * Initializes the turtle state (and clears the state stack)
     * @param pos turtle position
     * @param angle_deg angle in degrees (90=up, 0=right)
     */
    @Override
    public void init(Point2D pos, double angle_deg) {
        states.clear();
        this.x = pos.getX();
        this.y = pos.getY();
        this.currAngle = angle_deg;

        // Postscript part
        this.sb.append("%!PS-Adobe-3.0 EPSF-3.0\n"
                    + "%%BoundingBox (attend)\n"
                    + "%%EndComments\n"
                    + "0.5 setlinewidth\n"
                    + "newpath " + x + " " + y + " moveto\n");
    }

    /**
     * Angle of the turtle's nose
     * @return angle in degrees
     */
    @Override
    public double getAngle() {
        return currAngle;
    }

    /**
     * Sets the unit step and unit angle
     *
     * @param step length of an advance (move or draw)
     * @param delta unit angle change in degrees (for turnR and turnL)
     */
    @Override
    public void setUnits(double step, double delta) {
        this.step = step;
        this.delta = delta;
    }

    public double getStep() {
        return step;
    }
}