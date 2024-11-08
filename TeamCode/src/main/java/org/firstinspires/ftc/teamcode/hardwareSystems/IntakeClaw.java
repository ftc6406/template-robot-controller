package org.firstinspires.ftc.teamcode.hardwareSystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Collections;
import java.util.HashSet;

public class IntakeClaw extends Claw{
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

    public HashSet<CRServo> getCrServos() {
        return new HashSet<>(Collections.singletonList(INTAKE_SERVO));
    }

    public double getEjectPower() {
        return EJECT_POWER;
    }

    public boolean isIntakeActive() {
        return INTAKE_SERVO.getPower() != 0;
    }

    public void startIntake() {
        // INTAKE_SERVO.setPosition(0.125);
        INTAKE_SERVO.setPower(INTAKE_POWER);
    }

    public void stopIntake() {
        INTAKE_SERVO.setPower(0);
    }

    /**
     * Make the intake spin in reverse and eject the object.
     */
    public void ejectIntake() {
        // INTAKE_SERVO.setPosition(0);
        INTAKE_SERVO.setPower(EJECT_POWER);
    }
}