package org.firstinspires.ftc.teamcode.hardwareSystems;

import com.qualcomm.robotcore.hardware.Servo;

public abstract class Claw {
    // The servo that rotates the claw about the X-axis
    private final Servo X_AXIS_SERVO;
    // The servo that rotates the claw about the Y-axis
    private final Servo Y_AXIS_SERVO;
    // The servo that rotates the claw about the Z-axis
    private final Servo Z_AXIS_SERVO;
    // How much to gradually move the servo.
    private double servoIncrement;
    
    public Claw(Servo xAxisServo, Servo yAxisServo, Servo zAxisServo, double servoIncrement) {
        X_AXIS_SERVO = xAxisServo;
        Y_AXIS_SERVO = yAxisServo;
        Z_AXIS_SERVO = zAxisServo;

        this.servoIncrement = servoIncrement;
    }

    public double getServoIncrement() {
        return servoIncrement;
    }

    public void setServoIncrement(double servoIncrement) {
        this.servoIncrement = servoIncrement;
    }
    
    /**
     * Rotate the X rotation servo.
     *
     * @param direction The direction to rotate the servo in.
     *                  Positive values rotate it clockwise, negative values rotate it counterclockwise.
     */
    public void rotateXAxisServo(double direction) {
        double targetPosition = X_AXIS_SERVO.getPosition()
                + Math.signum(direction) * servoIncrement;
        X_AXIS_SERVO.setPosition(targetPosition);
    }

    /**
     * Rotate the Y rotation servo.
     *
     * @param direction The direction to rotate the servo in.
     *                  Positive values rotate it clockwise, negative values rotate it counterclockwise.
     */
    public void rotateYAxisServo(double direction) {
        double targetPosition = Z_AXIS_SERVO.getPosition()
                + Math.signum(direction) * servoIncrement;
        Y_AXIS_SERVO.setPosition(targetPosition);
    }

    /**
     * Rotate the Z rotation servo.
     *
     * @param direction The direction to rotate the servo in.
     *                  Positive values rotate it clockwise, negative values rotate it counterclockwise.
     */
    public void rotateZAxisServo(double direction) {
        double targetPosition = Z_AXIS_SERVO.getPosition()
                + Math.signum(direction) * servoIncrement;
        Z_AXIS_SERVO.setPosition(targetPosition);
    }
}