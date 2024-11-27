package org.firstinspires.ftc.teamcode.hardwareSystems;

import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashSet;

public abstract class Claw {
    private final HashSet<Servo> SERVOS;
    /**
     * How much to gradually move the servo.
     */
    private double SERVO_INCREMENT;
    /**
     * The servo that rotates the claw about the X-axis(roll).
     */
    private final Servo ROLL_SERVO;
    /**
     * The servo that rotates the claw about the Y-axis(pitch).
     */
    private final Servo PITCH_SERVO;
    /**
     * The servo that rotates the claw about the Z-axis(yaw).
     */
    private final Servo YAW_SERVO;

    public Claw(Servo rollServo, Servo pitchServo, Servo yawServo) {
        this(rollServo, pitchServo, yawServo, 0.1);
    }

    public Claw(Servo rollServo, Servo pitchServo, Servo yawServo, double servoIncrement) {
        SERVOS = new HashSet<>();
        SERVOS.add(rollServo);
        SERVOS.add(pitchServo);
        SERVOS.add(yawServo);

        ROLL_SERVO = rollServo;
        PITCH_SERVO = pitchServo;
        YAW_SERVO = yawServo;

        SERVO_INCREMENT = servoIncrement;
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

    public void setServoIncrement(double servoIncrement) {
        this.SERVO_INCREMENT = servoIncrement;
    }

    /**
     * Rotate the X-axis(roll) servo in a certain direction by `SERVO_INCREMENT`.
     *
     * @param direction The direction to rotate the servo in.
     *                  Positive values rotate it clockwise, negative values rotate it counterclockwise.
     */
    public void rotateRollServo(double direction) {
        double targetPosition = ROLL_SERVO.getPosition() + Math.signum(direction) * SERVO_INCREMENT;
        ROLL_SERVO.setPosition(targetPosition);
    }

    public void rotateRollServoToPosition(double position) {
        ROLL_SERVO.setPosition(position);
    }

    /**
     * Rotate the Y-axis(pitch) servo in a certain direction by `SERVO_INCREMENT`.
     *
     * @param direction The direction to rotate the servo in.
     *                  Positive values rotate it clockwise, negative values rotate it counterclockwise.
     */
    public void rotatePitchAxisServo(double direction) {
        double targetPosition = PITCH_SERVO.getPosition() + Math.signum(direction) * SERVO_INCREMENT;
        PITCH_SERVO.setPosition(targetPosition);
    }

    public void rotatePitchServoToPosition(double position) {
        PITCH_SERVO.setPosition(position);
    }

    /**
     * Rotate the Z-axis(yaw) servo in a certain direction by `SERVO_INCREMENT`.
     *
     * @param direction The direction to rotate the servo in.
     *                  Positive values rotate it clockwise, negative values rotate it counterclockwise.
     */
    public void rotateYawServo(double direction) {
        double targetPosition = YAW_SERVO.getPosition() + Math.signum(direction) * SERVO_INCREMENT;
        YAW_SERVO.setPosition(targetPosition);
    }

    public void rotateYawServoToPosition(double position) {
        YAW_SERVO.setPosition(position);
    }
}