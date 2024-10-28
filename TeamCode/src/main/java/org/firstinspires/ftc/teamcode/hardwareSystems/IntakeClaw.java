package org.firstinspires.ftc.teamcode.hardwareSystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Collections;
import java.util.HashSet;

public class IntakeClaw extends Claw{
    // The servo that opens and closes the grip.
    private final CRServo INTAKE_SERVO;

    private static final double INTAKE_POWER = 0.5;
    private static final double EJECT_POWER = -1.0;

    public IntakeClaw(Servo xAxisServo, Servo yAxisServo, Servo zAxisServo, CRServo intakeServo) {
        super(xAxisServo, yAxisServo, zAxisServo);

        INTAKE_SERVO = intakeServo;
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
        INTAKE_SERVO.setPower(INTAKE_POWER);
    }

    public void stopIntake() {
        INTAKE_SERVO.setPower(0);
    }

    /**
     * Make the intake spin in reverse and eject the object.
     */
    public void ejectIntake() {
        INTAKE_SERVO.setPower(EJECT_POWER);
    }
}