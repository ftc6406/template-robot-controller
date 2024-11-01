package org.firstinspires.ftc.teamcode.hardwareSystems;

import java.util.HashSet;

import com.qualcomm.robotcore.hardware.*;

public class ExtendableArm extends Arm {
    /**
     * Passed into the {@code ExtendableArm} constructor.
     * Contains the motors and motor types.
     */
    public static class MotorSet {
        private final HashSet<DcMotor> MOTORS;

        // The motor that rotates the arm up and down.
        private final DcMotor ROTATION_MOTOR;
        // The motor that extends and retracts the arm.
        private final DcMotor EXTENSION_MOTOR;

        public MotorSet(DcMotor rotationMotor, DcMotor extensionMotor) {
            this.ROTATION_MOTOR = rotationMotor;
            this.EXTENSION_MOTOR = extensionMotor;

            MOTORS = new HashSet<>();
            MOTORS.add(rotationMotor);
            MOTORS.add(extensionMotor);
        }
    }

    /**
     * Passed into the {@code ExtendableArm} constructor.
     * Contains the min rotation, max rotation, and ticks per degree.
     */
    public static class RotationRange {
        // The minimum rotation of the arm in ticks.
        private final int MIN_ROTATION;
        // The maximum rotation of the arm in ticks.
        private final int MAX_ROTATION;

        // How many ticks it takes to rotate the arm by one degree.
        private final double TICKS_PER_DEGREE;

        public RotationRange(int minRotation, int maxRotation, double ticksPerDegree) {
            this.MIN_ROTATION = minRotation;
            this.MAX_ROTATION = maxRotation;
            this.TICKS_PER_DEGREE = ticksPerDegree;
        }
    }

    /**
     * Passed into the {@code ExtendableArm} constructor.
     * Contains the min extension and max extension.
     */
    public static class ExtensionRange {
        // The minimum extension of the arm in ticks.
        private final int MIN_EXTENSION;
        // The maximum extension of the arm in ticks.
        private final int MAX_EXTENSION;

        public ExtensionRange(int minExtension, int maxExtension) {
            this.MIN_EXTENSION = minExtension;
            this.MAX_EXTENSION = maxExtension;
        }
    }

    // The motor that rotates the arm up and down.
    private final DcMotor ROTATION_MOTOR;
    // The motor power that the arm uses when rotating.
    private static final double ROTATION_POWER = 1.0;
    // How many ticks it takes to rotate the arm by one degree.
    private final double TICKS_PER_ROTATION_DEGREE;

    // The minimum rotation of the arm in ticks.
    private final int MIN_ROTATION;
    // The maximum rotation of the arm in ticks.
    private final int MAX_ROTATION;

    // The motor that extends and retracts the arm.
    private final DcMotor EXTENSION_MOTOR;
    // The motor power that the arm uses when rotating.
    private static final double EXTENSION_POWER = 1.0;
    // The minimum extension of the arm in ticks.
    private final int MIN_EXTENSION;
    // The maximum extension of the arm in ticks.
    private final int MAX_EXTENSION;

    /**
     * Instantiates an extendable arm
     * @param motorSet     The motors and motor types.
     * @param rotationRange  The min rotation, max rotation, and ticks per degree.
     * @param extensionRange The min extension and max extension.
     */
    public ExtendableArm(MotorSet motorSet, RotationRange rotationRange, ExtensionRange extensionRange) {
        super(motorSet.MOTORS);

        this.ROTATION_MOTOR = motorSet.ROTATION_MOTOR;
        this.TICKS_PER_ROTATION_DEGREE = rotationRange.TICKS_PER_DEGREE;
        this.MIN_ROTATION = rotationRange.MIN_ROTATION;
        this.MAX_ROTATION = rotationRange.MAX_ROTATION;

        this.EXTENSION_MOTOR = motorSet.EXTENSION_MOTOR;
        this.MIN_EXTENSION = extensionRange.MIN_EXTENSION;
        this.MAX_EXTENSION = extensionRange.MAX_EXTENSION;
    }

    public double getRotationPower() {
        return ROTATION_POWER;
    }

    public double getExtensionPower() {
        return EXTENSION_POWER;
    }

    /**
     * Rotate the arm with a set velocity.
     * Stop the motor if it is out of bounds.
     *
     * @param direction The direction that the arm should rotate in.
     *                  Positive rotates it up, negative rotates it down, zero stops the motor.
     */
    public void rotateArm(double direction) {
        if (ROTATION_MOTOR == null) {
            return;
        }

        if (ROTATION_MOTOR.getCurrentPosition() > MAX_ROTATION || ROTATION_MOTOR.getCurrentPosition() < MIN_ROTATION) {
            ROTATION_MOTOR.setPower(0);
            return;
        }

        ROTATION_MOTOR.setPower(direction * ROTATION_POWER);
    }

    /**
     * Rotates the arm to a position specified in degrees.
     *
     * @param degrees The position the arm moves to.
     *                The arm's starting position is 0 degrees.
     */
    public void rotateArmToPosition(double degrees) {
        if (ROTATION_MOTOR == null) {
            return;
        }

        int targetPosition = (int) Math.round(degrees * TICKS_PER_ROTATION_DEGREE);

        // keep the target position within acceptable bounds
        targetPosition = Math.min(Math.max(targetPosition, MIN_ROTATION), MAX_ROTATION);

        /*
         * Calculate the direction that the arm will have to rotate.
         * Negative is down, positive is up
         */
        int direction = (int) Math.signum(targetPosition - ROTATION_MOTOR.getCurrentPosition());

        ROTATION_MOTOR.setTargetPosition(targetPosition);
        ROTATION_MOTOR.setPower(direction * ROTATION_POWER);
        ROTATION_MOTOR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * @param direction The direction that the extension motor moves.
     *                  Positive values extend the arm, negative values retract it.
     */
    public void extendArm(double direction) {
        if (EXTENSION_MOTOR == null) {
            return;
        }

        if (EXTENSION_MOTOR.getCurrentPosition() > MAX_EXTENSION || EXTENSION_MOTOR.getCurrentPosition() < MIN_EXTENSION) {
            EXTENSION_MOTOR.setPower(0);
            return;
        }

        EXTENSION_MOTOR.setPower(Math.signum(direction) * EXTENSION_POWER);
    }

    public void extendArmToPosition(int targetPosition) {
        if (EXTENSION_MOTOR == null) {
            return;
        }

        EXTENSION_MOTOR.setTargetPosition(targetPosition);
        int direction = (int) Math.signum(targetPosition - EXTENSION_MOTOR.getCurrentPosition());
        EXTENSION_MOTOR.setPower(direction * EXTENSION_POWER);
        EXTENSION_MOTOR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}