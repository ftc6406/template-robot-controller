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
     * Instantiate the a wheels object.
     *
     * @param motors       All the motors used by the robot.
     * @param ticksPerInch The number of ticks needed to move the robot by one inh.
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
     * Get all the DcMotors that are used by this wheels system.
     *
     * @return A set that contains every DcMotor included by this wheels system.
     */
    public HashSet<DcMotor> getMotors() {
        return MOTORS;
    }

    /**
     * Drive forwards and backwards.
     * @param drivePower
     */
    public void drive(double drivePower) {
        drive(0, drivePower, 0);
    }

    /**
     * Drive the wheels.
     *
     * @param drivePower Forward power.
     *                   Positive is forward, negative is backward.
     * @param turn       Rotation power.
     *                   Positive is clockwise, negative is counterclockwise.
     */
    public abstract void drive(double drivePower, double turn);

    /**
     * Drive the wheels.
     *
     * @param x    Sideways power.
     *             Positive is rightward, negative is leftward.
     * @param y    Forward power.
     *             Positive is forward, negative is backward.
     * @param turn Rotation power.
     *             Positive is clockwise, negative is counterclockwise.
     */
    public abstract void drive(double x, double y, double turn);

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
     * @param sidewaysDistance The distance that the robot travels sideways in inches.
     *                         Positive is rightward, negative is leftward.
     * @param forwardDistance  The distance that the robot travels forward in
     *                         inches.
     *                         Positive is forward, negative is backward.
     */
    public abstract void driveDistance(double sidewaysDistance, double forwardDistance);

    /**
     * Rotate the robot a certain number of degrees.
     *
     * @param degrees How many degrees the robot turns.
     *                Positive is clockwise, negative is counterclockwise.
     */
    public abstract void turn(double degrees);
}
