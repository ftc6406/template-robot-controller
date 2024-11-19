package org.firstinspires.ftc.teamcode.hardwareSystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;

public class IntakeClaw extends Claw {
    // The servo that spins the intake.
    private final CRServo INTAKE_SERVO;
    // The touch sensor that touches whether there is a piece in the intake.
    private final DigitalChannel INTAKE_SENSOR;

    private static final double INTAKE_POWER = 0.5;
    private static final double EJECT_POWER = -1.0;

    public IntakeClaw(Servo xAxisServo, Servo yAxisServo, Servo zAxisServo, CRServo intakeServo) {
        this(xAxisServo, yAxisServo, zAxisServo, intakeServo, null);
    }

    public IntakeClaw(Servo xAxisServo, Servo yAxisServo, Servo zAxisServo, CRServo intakeServo, DigitalChannel intakeSensor) {
        super(xAxisServo, yAxisServo, zAxisServo);

        INTAKE_SERVO = intakeServo;
        INTAKE_SENSOR = intakeSensor;
    }

    public double getIntakePower() {
        return INTAKE_POWER;
    }

    public double getEjectPower() {
        return EJECT_POWER;
    }

    public HashSet<CRServo> getCrServos() {
        // return new HashSet<>();
        return new HashSet<>(Collections.singletonList(INTAKE_SERVO));
    }

    public CRServo getIntakeServo() {
        return INTAKE_SERVO;
    }

    public void startIntake() throws NullPointerException {
        if (INTAKE_SERVO == null) {
            throw new NullPointerException("WARNING: THE INTAKE SERVO IS NULL!");
        }

        INTAKE_SERVO.setPower(INTAKE_POWER);
    }

    /**
     * Get whether the intake servo is currently running.
     * @return true if the intake servo's power is non-zero,
     *         false otherwise.
     * @throws IllegalStateException
     */
    public boolean isIntakeActive() throws IllegalStateException {
        if (INTAKE_SERVO == null) {
            throw new IllegalStateException("WARNING: THE INTAKE SERVO IS NULL!");
        }

        return INTAKE_SERVO.getPower() != 0;
    }

    public void stopIntake() throws NullPointerException {
        if (INTAKE_SERVO == null) {
            throw new NullPointerException("WARNING: THE INTAKE SERVO IS NULL!");
        }

        INTAKE_SERVO.setPower(0);
    }

    /**
     * Make the intake spin in reverse and eject the object.
     */
    public void ejectIntake() throws NullPointerException {
        if (INTAKE_SERVO == null) {
            throw new NullPointerException("WARNING: THE INTAKE SERVO IS NULL!");
        }

        INTAKE_SERVO.setPower(EJECT_POWER);
    }

    /**
     * Get whether the sensor on the claw is pressed or not.
     * @return true when the sensor is pressed,
     *         false otherwise.
     * @throws NullPointerException When `INTAKE_SERVO` is null.
     */
    public boolean isSensorPressed() throws NullPointerException {
        if (INTAKE_SENSOR == null) {
            throw new NullPointerException("WARNING: THE INTAKE SERVO IS NULL!");
        }

        // `getState()` returns true when the sensor is not pressed.
        return !INTAKE_SENSOR.getState();
    }
}