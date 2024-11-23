package org.firstinspires.ftc.teamcode.hardwareSystems;

import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashSet;

public abstract class Claw {
    /**
     * How much to gradually move the servo.
     */
    private final static double SERVO_INCREMENT = 0.1;
    private final HashSet<Servo> SERVOS;
    /**
     * The servo that rotates the claw about the X-axis
     */
    private final Servo X_AXIS_SERVO;
    /**
     * The servo that rotates the claw about the Y-axis
     */
    private final Servo Y_AXIS_SERVO;
    /**
     * The servo that rotates the claw about the Z-axis
     */
    private final Servo Z_AXIS_SERVO;

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
     * @throws NullPointerException If {@code X_AXIS_SERVO} is null;
     */
    public void rotateXAxisServo(double direction) throws NullPointerException {
        if (X_AXIS_SERVO == null) {
            throw new NullPointerException("WARNING: CLAW X-AXIS SERVO IS NULL!");
        }

        double targetPosition = X_AXIS_SERVO.getPosition() + Math.signum(direction) * SERVO_INCREMENT;

        X_AXIS_SERVO.setPosition(targetPosition);
    }

    public void rotateXAxisServoToPosition(double position) {
        X_AXIS_SERVO.setPosition(position);
    }

    /**
     * Rotate the Y rotation servo.
     *
     * @param direction The direction to rotate the servo in.
     *                  Positive values rotate it clockwise, negative values rotate it counterclockwise.
     *                  2
     */
    public void rotateYAxisServo(double direction) throws NullPointerException {
        if (Y_AXIS_SERVO == null) {
            throw new NullPointerException("WARNING: CLAW Y-AXIS SERVO IS NULL!");
        }

        double targetPosition = Y_AXIS_SERVO.getPosition() + Math.signum(direction) * SERVO_INCREMENT;
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

        double targetPosition = Z_AXIS_SERVO.getPosition() + Math.signum(direction) * SERVO_INCREMENT;
        Z_AXIS_SERVO.setPosition(targetPosition);
    }
}