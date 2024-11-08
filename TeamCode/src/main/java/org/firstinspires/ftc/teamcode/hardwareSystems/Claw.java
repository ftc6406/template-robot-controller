package org.firstinspires.ftc.teamcode.hardwareSystems;

import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashSet;

public abstract class Claw {
    private final HashSet<Servo> SERVOS;

    // The servo that rotates the claw about the X-axis
    private final Servo X_AXIS_SERVO;
    // The servo that rotates the claw about the Y-axis
    private final Servo Y_AXIS_SERVO;
    // The servo that rotates the claw about the Z-axis
    private final Servo Z_AXIS_SERVO;
    // How much to gradually move the servo.
    private final static double SERVO_INCREMENT = 0.01;

    public Claw(Servo xAxisServo, Servo yAxisServo, Servo zAxisServo) {
        SERVOS = new HashSet<>();
        SERVOS.add(xAxisServo);
        SERVOS.add(yAxisServo);
        SERVOS.add(zAxisServo);

        X_AXIS_SERVO = xAxisServo;
        Y_AXIS_SERVO = yAxisServo;
        Z_AXIS_SERVO = zAxisServo;
    }

    /**
     * Get all the {@code Servo}s that are included in this arm system.
     *
     * @return A {@code HashSet} that contains every Servo included in this arm system.
     */
    public HashSet<Servo> getServos() {
        return SERVOS;
    }

    public double getServoIncrement() {
        return SERVO_INCREMENT;
    }

    /**
     * Rotate the X rotation servo.
     *
     * @param direction The direction to rotate the servo in.
     *                  Positive values rotate it clockwise, negative values rotate it counterclockwise.
     */
    public void rotateXAxisServo(double direction) {
        if (X_AXIS_SERVO == null) {
            return;
        }

        double targetPosition = X_AXIS_SERVO.getPosition()
                + Math.signum(direction) * SERVO_INCREMENT;
        X_AXIS_SERVO.setPosition(targetPosition);
    }

    /**
     * Rotate the Y rotation servo.
     *
     * @param direction The direction to rotate the servo in.
     *                  Positive values rotate it clockwise, negative values rotate it counterclockwise.
     */
    public void rotateYAxisServo(double direction) {
        if (Y_AXIS_SERVO == null) {
            return;
        }

        double targetPosition = Y_AXIS_SERVO.getPosition()
                + Math.signum(direction) * SERVO_INCREMENT;
        Y_AXIS_SERVO.setPosition(targetPosition);
    }

    /**
     * Rotate the Z rotation servo.
     *
     * @param direction The direction to rotate the servo in.
     *                  Positive values rotate it clockwise, negative values rotate it counterclockwise.
     */
    public void rotateZAxisServo(double direction) {
        if (Z_AXIS_SERVO == null) {
            return;
        }

        double targetPosition = Z_AXIS_SERVO.getPosition()
                + Math.signum(direction) * SERVO_INCREMENT;
        Z_AXIS_SERVO.setPosition(targetPosition);
    }
}