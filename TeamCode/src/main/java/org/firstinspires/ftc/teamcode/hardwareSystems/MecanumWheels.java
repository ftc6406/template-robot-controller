package org.firstinspires.ftc.teamcode.hardwareSystems;

import com.qualcomm.robotcore.hardware.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class MecanumWheels extends Wheels {
    /* The DcMotors powering the wheels */
    private final DcMotor FRONT_LEFT_MOTOR;
    private final DcMotor FRONT_RIGHT_MOTOR;
    private final DcMotor BACK_LEFT_MOTOR;
    private final DcMotor BACK_RIGHT_MOTOR;

    public MecanumWheels(DcMotor frontLeftMotor, DcMotor frontRightMotor, DcMotor backLeftMotor, DcMotor backRightMotor, double ticksPerInch) {
        super(
                new HashSet<>(
                        Arrays.asList(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor)
                ),
                ticksPerInch
        );

        this.FRONT_LEFT_MOTOR = frontLeftMotor;
        this.FRONT_RIGHT_MOTOR = frontRightMotor;
        this.BACK_LEFT_MOTOR = backLeftMotor;
        this.BACK_RIGHT_MOTOR = backRightMotor;

        /*
         * Set the directions of the motors.
         * The right and left motors run in opposite directions of each other.
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
    public void drive(double y, double x, double turn) {
        for (DcMotor motor : MOTORS) {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        double frontLeftPower = y - x - turn;
        double frontRightPower = y + x + turn;
        double backLeftPower = y + x - turn;
        double backRightPower = y - x + turn;

        // Scale the motor powers to be within +/- 1.0
        List<Double> powers = Arrays.asList(
                frontLeftPower,
                frontRightPower,
                backLeftPower,
                backRightPower
        );
        double max = Collections.max(powers);
        if (max > 1.0) {
            frontLeftPower /= max;
            frontRightPower /= max;
            backLeftPower /= max;
            backRightPower /= max;
        }

        FRONT_LEFT_MOTOR.setPower(frontLeftPower);
        FRONT_RIGHT_MOTOR.setPower(frontRightPower);
        BACK_LEFT_MOTOR.setPower(backLeftPower);
        BACK_RIGHT_MOTOR.setPower(backRightPower);
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
    public void driveDistance(double sidewaysDistance, double forwardDistance) {
        // Apply Pythagorean's Theorem to find the Euclidean distance
        double totalDistance = Math.sqrt(Math.pow(forwardDistance, 2) + Math.pow(sidewaysDistance, 2));

        // Scale the motor motor power based on trigonometry
        double xPower = MOTOR_POWER * (sidewaysDistance / totalDistance);
        double yPower = MOTOR_POWER * (forwardDistance / totalDistance);

        drive(xPower, yPower);

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