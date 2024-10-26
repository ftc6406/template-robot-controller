package org.firstinspires.ftc.teamcode.hardwareSystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeClaw extends Claw{
    // The servo that opens and closes the grip.
    private final CRServo INTAKE_SERVO;

    private double intakePower = 0.5;
    private double ejectPower = -1.0;

    public IntakeClaw(Servo xAxisServo, Servo yAxisServo, Servo zAxisServo, CRServo intakeServo) {
        super(xAxisServo, yAxisServo, zAxisServo, 0.01);

        INTAKE_SERVO = intakeServo;
    }

    public double getIntakePower() {
        return intakePower;
    }

    public void setIntakePower(double intakePower) {
        this.intakePower = intakePower;
    }

    public double getEjectPower() {
        return ejectPower;
    }

    public void setEjectPower(double ejectPower) {
        this.ejectPower = ejectPower;
    }

    public boolean isIntakeActive() {
        return INTAKE_SERVO.getPower() != 0;
    }

    public void startIntake() {
        INTAKE_SERVO.setPower(intakePower);
    }

    public void stopIntake() {
        INTAKE_SERVO.setPower(0);
    }

    /**
     * Make the intake spin in reverse and eject the object.
     */
    public void ejectIntake() {
        INTAKE_SERVO.setPower(ejectPower);
    }
}