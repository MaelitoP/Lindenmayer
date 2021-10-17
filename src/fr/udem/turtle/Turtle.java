package fr.udem.turtle;

/*
 * Copyright 2021 Mikl&oacute;s Cs&#369;r&ouml;s.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.awt.geom.Point2D;

/**
 * Turtle graphics interface. The turtle state is defined as its
 * location on the plane and the orientation of its nose.
 * Implementing classes are expected to initialize
 * the turtle with position (0,0) and angle 0 by default.
 * The turtle moves and draws by unit-length steps, and turns left or right by a unit angle
 * (e.g., 30), which are set in {@link #setUnits(double, double) }.
 *
 *
 * @author Mikl&oacute;s Cs&#369;r&ouml;s
 */

public interface Turtle {
    /**
     * Draws a line of unit length
     */
    void draw();

    /**
     * Moves by unit length, no drawing.
     */
    void move();

    /**
     * Turn right (clockwise) by unit angle.
     */
    void turnR();

    /**
     * Turn left (counter-clockwise) by unit angle.
     */
    void turnL();

    /**
     * Saves turtle state
     */
    void push();

    /**
     * Recovers turtle state
     */
    void pop();

    /**
     * Lets the turtle relax.
     */
    void stay();

    /**
     * Initializes the turtle state (and clears the state stack)
     * @param pos turtle position
     * @param angle_deg angle in degrees (90=up, 0=right)
     */
    void init(Point2D pos, double angle_deg);

    Point2D getPos();

    double getX();

    double getY();

    /**
     * Angle of the turtle's nose
     * @return angle in degrees
     */
    double getAngle();

    /**
     * Sets the unit step and unit angle
     *
     * @param step length of an advance (move or draw)
     * @param delta unit angle change in degrees (for turnR and turnL)
     */
    void setUnits(double step, double delta);

    /**
     * State class
     *
     * @author Mael LE PETIT
     */
    class State implements Cloneable{
        private Point2D pos;
        private double angle;

        State(Point2D pos, double angle) {
            this.pos = pos;
            this.angle = angle;
        }

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        void setAngle(double angle) {
            this.angle = angle;
        }

        void setPos(Point2D pos) {
            this.pos = pos;
        }

        void setState(State s) {
            this.pos = s.pos;
            this.angle = s.angle;
        }

        Point2D getPos() {
            return this.pos;
        }

        double getAngle() {
            return this.angle;
        }
    }
}
