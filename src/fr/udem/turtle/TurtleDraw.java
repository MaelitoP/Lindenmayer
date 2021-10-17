package fr.udem.turtle;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Stack;

/**
 * Class to draw with the turtle
 *
 * @author Mael LE PETIT
 */
public class TurtleDraw implements Turtle {
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

    /**
     * Graphics2D variable
     */
    private Graphics2D g2d;

    /**
     * Constructor of {@link TurtleDraw}
     */
    public TurtleDraw(Graphics g2d) {
        this.states = new Stack<>();
        this.g2d = (Graphics2D) g2d;
    }

    /**
     * Draws a line of {@link TurtleDraw#step} length
     */
    @Override
    public void draw() {
        double oldX = this.x;
        double oldY = this.y;
        move();
        g2d.drawLine((int) this.x,(int) this.y, (int) oldX, (int) oldY);
    }

    /*@Override
    public void draw() {
        Point2D pos = getPos();
        int x2 = (int) (pos.getX()
                + getStep() * Math.cos(Math.toRadians(getAngle())));
        int y2 = (int) (pos.getY()
                + getStep() * Math.sin(Math.toRadians(getAngle())));
        g2d.drawLine((int) pos.getX(), (int) pos.getY(), x2, y2);
        super.move();
    }*/

    /**
     * Moves by {@link TurtleDraw#step} unit length, no drawing.
     */
    @Override
    public void move() {
        this.x += step * Math.cos(Math.toRadians(currAngle));
        this.y += step * Math.sin(Math.toRadians(currAngle));
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
    }

    /**
     * Recovers turtle state
     */
    @Override
    public void pop() {
        states.pop();

        State currSt = states.peek();
        x = currSt.getPos().getX();
        y = currSt.getPos().getY();
        currAngle = currSt.getAngle();
    }

    @Override
    public void stay() {
        // do nothing
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
}