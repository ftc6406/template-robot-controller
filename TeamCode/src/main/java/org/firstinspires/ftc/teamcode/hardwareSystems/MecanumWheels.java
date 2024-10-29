package org.firstinspires.ftc.teamcode.hardwareSystems;

import com.qualcomm.robotcore.hardware.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class MecanumWheels extends Wheels {
    /**
     * Passed into the `MecanumWheels` constructor.
     * Contains all four motors and the motor type.
     */
    public static class MotorParams {
        public final HashSet<DcMotor> MOTORS;

        /* The DcMotors powering the wheels */
        private final DcMotor FRONT_LEFT_MOTOR;
        private final DcMotor FRONT_RIGHT_MOTOR;
        private final DcMotor BACK_LEFT_MOTOR;
        private final DcMotor BACK_RIGHT_MOTOR;

        public MotorParams(DcMotor frontLeftMotor, DcMotor frontRightMotor, DcMotor backLeftMotor, DcMotor backRightMotor) {
            MOTORS = new HashSet<>();
            MOTORS.add(frontLeftMotor);
            MOTORS.add(frontRightMotor);
            MOTORS.add(backLeftMotor);
            MOTORS.add(backRightMotor);

            this.FRONT_LEFT_MOTOR = frontLeftMotor;
            this.FRONT_RIGHT_MOTOR = frontRightMotor;
            this.BACK_LEFT_MOTOR = backLeftMotor;
            this.BACK_RIGHT_MOTOR = backRightMotor;
        }
    }

    /* The DcMotors powering the wheels */
    private final DcMotor FRONT_LEFT_MOTOR;
    private final DcMotor FRONT_RIGHT_MOTOR;
    private final DcMotor BACK_LEFT_MOTOR;
    private final DcMotor BACK_RIGHT_MOTOR;

    public MecanumWheels(MotorParams motorParams, double ticksPerInch) {
        super(motorParams.MOTORS, ticksPerInch);

        this.FRONT_LEFT_MOTOR = motorParams.FRONT_LEFT_MOTOR;
        this.FRONT_RIGHT_MOTOR = motorParams.FRONT_RIGHT_MOTOR;
        this.BACK_LEFT_MOTOR = motorParams.BACK_LEFT_MOTOR;
        this.BACK_RIGHT_MOTOR = motorParams.BACK_RIGHT_MOTOR;

        /*
         * Set the directions of the motors
         * The right and left motors run in opposite directions of each other
         */
        FRONT_LEFT_MOTOR.setDirection(DcMotorSimple.Direction.REVERSE);
        FRONT_RIGHT_MOTOR.setDirection(DcMotorSimple.Direction.FORWARD);
        BACK_LEFT_MOTOR.setDirection(DcMotorSimple.Direction.REVERSE);
        BACK_RIGHT_MOTOR.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drive(double drivePower, double turn) {
        drive(0, drivePower, turn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drive(double x, double y, double turn) {
        for (DcMotor motor: MOTORS) {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        double frontLeftPower = x - y - turn;
        double frontRightPower = x + y + turn;
        double backLeftPower =  x + y - turn;
        double backRightPower = x - y + turn;

        // Scale the motor powers to be within +/- 1.0
        List<Double> powers = Arrays.asList(
                frontLeftPower,
                frontRightPower,
                backLeftPower,
                backRightPower
        );
        double max = Collections.max(powers);
        if (max > 1.0) {
            for (Double power : powers) {
                power /= max;
            }
        }

        FRONT_LEFT_MOTOR.setPower(x - y - turn);
        FRONT_RIGHT_MOTOR.setPower(x + y + turn);
        BACK_LEFT_MOTOR.setPower(x + y - turn);
        BACK_RIGHT_MOTOR.setPower(x - y + turn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void driveDistance(double distance) {
        for (DcMotor motor : MOTORS) {
            motor.setTargetPosition((int) Math.round(distance * TICKS_PER_INCH));
            motor.setPower(MOTOR_POWER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <strong><i>THIS METHOD IS STILL EXPERIMENTAL!</i></strong>
     */
    @Override
    public void driveDistance(double forwardDistance, double sidewaysDistance) {
        // Apply Pythagorean's Theorem to find the Euclidean distance
        double totalDistance = Math.sqrt(Math.pow(forwardDistance, 2) + Math.pow(sidewaysDistance, 2));

        for (DcMotor motor : MOTORS) {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void turn(double degrees) {

    }
}