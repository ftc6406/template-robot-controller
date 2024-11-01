package org.firstinspires.ftc.teamcode.hardwareSystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashSet;

public class IntakeClaw extends Claw{
    // The servo that spins the intake.
    private final Servo INTAKE_SERVO;

    private static final double INTAKE_POWER = 0.5;
    private static final double EJECT_POWER = -1.0;

    public IntakeClaw(Servo xAxisServo, Servo yAxisServo, Servo zAxisServo, Servo intakeServo) {
        super(xAxisServo, yAxisServo, zAxisServo);

        INTAKE_SERVO = intakeServo;
    }

    public double getIntakePower() {
        return INTAKE_POWER;
    }

    public HashSet<CRServo> getCrServos() {
        return null;
        // return new HashSet<>(Collections.singletonList(INTAKE_SERVO));
    }

    public double getEjectPower() {
        return EJECT_POWER;
    }

    /*
    public boolean isIntakeActive() {
        return INTAKE_SERVO.getPower() != 0;
    }
     */

    public void startIntake() {
        if (INTAKE_SERVO == null) {
            return;
        }

        INTAKE_SERVO.setPosition(1.0 / 8.0);
        // INTAKE_SERVO.setPower(INTAKE_POWER);
    }

    /*
    public void stopIntake() {
        if (INTAKE_SERVO == null) {
            return;
        }

        INTAKE_SERVO.setPower(0);
    }
     */

    /**
     * Make the intake spin in reverse and eject the object.
     */
    public void ejectIntake() {
        if (INTAKE_SERVO == null) {
            return;
        }

        // INTAKE_SERVO.setPower(EJECT_POWER);
        INTAKE_SERVO.setPosition(0);
    }
}