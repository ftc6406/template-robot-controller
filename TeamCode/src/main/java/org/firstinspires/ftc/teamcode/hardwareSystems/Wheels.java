package org.firstinspires.ftc.teamcode.hardwareSystems;

import java.util.HashSet;

import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class Wheels {
    protected final HashSet<DcMotor> MOTORS;

    // A modifier for much power the wheels run with (0.0 - 1.0)
    protected final static double MOTOR_POWER = 1.0;
    protected final double TICKS_PER_INCH;

    public Wheels(HashSet<DcMotor> motors) {
        this(motors, 100);
    }

    /**
     * Instantiate a {@code Wheels} object.
     *
     * @param motors       All the motors used by the robot.
     * @param ticksPerInch The number of ticks needed to move the robot by one inch.
     */
    public Wheels(HashSet<DcMotor> motors, double ticksPerInch) {
        MOTORS = motors;
        // Allow wheels to roll freely.
        for (DcMotor motor : MOTORS) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }

        TICKS_PER_INCH = ticksPerInch;
    }

    public double getMotorPower() {
        return MOTOR_POWER;
    }

    /**
     * Get all the {@code DcMotor}s that are used by this wheels system.
     *
     * @return A {@code HashSet} that contains every {@code DcMotor} included by this wheels system.
     */
    public HashSet<DcMotor> getMotors() {
        return MOTORS;
    }

    /**
     * Drive forwards and backwards.
     *
     * @param drivePower What power to set the motors to.
     */
    public void drive(double drivePower) {
        drive(drivePower, 0, 0);
    }

    /**
     * Drive the wheels.
     *
     * @param drivePower Forward power.
     *                   Positive is forward, negative is backward.
     * @param turn       Rotation power.
     *                   Positive is clockwise, negative is counterclockwise.
     */
    public void drive(double drivePower, double turn) {
        drive(drivePower, 0, turn);
    }

    /**
     * Drive the wheels.
     *
     * @param y    Forward power.
     *             Positive is forward, negative is backward.
     * @param x    Sideways power.
     *             Positive is rightward, negative is leftward.
     * @param turn Rotation power.
     *             Positive is clockwise, negative is counterclockwise.
     */
    public abstract void drive(double y, double x, double turn);

    /**
     * Drive the robot a certain distance forward.
     *
     * @param distance The distance that the robot travels in inches.
     *                 Positive is forward, negative is backward.
     */
    public abstract void driveDistance(double distance);

    /**
     * Drive the robot a certain distance in two dimensions.
     *
     * @param forwardDistance  The distance that the robot travels forward in
     *                         inches.
     *                         Positive is forward, negative is backward.
     * @param sidewaysDistance The distance that the robot travels sideways in inches.
     *                         Positive is rightward, negative is leftward.
     */
    public abstract void driveDistance(double forwardDistance, double sidewaysDistance);

    /**
     * Rotate the robot a certain number of degrees.
     *
     * @param degrees How many degrees the robot turns.
     *                Positive is clockwise, negative is counterclockwise.
     */
    public abstract void turn(double degrees);
}
