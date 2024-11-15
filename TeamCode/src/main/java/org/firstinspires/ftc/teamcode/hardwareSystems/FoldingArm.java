package org.firstinspires.ftc.teamcode.hardwareSystems;

import java.util.HashSet;

import com.qualcomm.robotcore.hardware.*;

public class FoldingArm extends Arm {
    /**
     * Passed into the {@code ExtendableArm} constructor.
     * Contains the motors and motor types.
     */
    public static class MotorSet {
        private final HashSet<DcMotor> MOTORS;

        // The motor that rotates the arm up and down.
        private final DcMotor ROTATION_MOTOR;
        // The motor that folds the arm.
        private final DcMotor FOLDING_MOTOR;

        public MotorSet(DcMotor rotationMotor, DcMotor foldingMotor) {
            MOTORS = new HashSet<>();
            MOTORS.add(rotationMotor);
            MOTORS.add(foldingMotor);

            ROTATION_MOTOR = rotationMotor;
            FOLDING_MOTOR = foldingMotor;
        }

        public MotorSet() {
            MOTORS = new HashSet<>();

            ROTATION_MOTOR = null;
            FOLDING_MOTOR = null;
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
     * Passed into the {@code FoldingArm} constructor.
     * Contains the min extension and max extension.
     */
    public static class FoldingRange {
        // The minimum extension of the arm in ticks.
        private final int MIN_FOLDING;
        // The maximum extension of the arm in ticks.
        private final int MAX_FOLDING;

        // How many ticks are in a degree.
        private final double TICKS_PER_DEGREE;

        public FoldingRange(int minExtension, int maxExtension, double ticksPerDegree) {
            this.MIN_FOLDING = minExtension;
            this.MAX_FOLDING = maxExtension;

            this.TICKS_PER_DEGREE = ticksPerDegree;
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

    // The motor that folds and retracts the arm.
    private final DcMotor FOLDING_MOTOR;
    // The motor power that the arm uses when rotating.
    private static final double FOLDING_POWER = 1.0;
    // How many ticks it takes to rotate the arm by one degree.
    private final double TICKS_PER_FOLDING_DEGREE;
    // The minimum extension of the arm in ticks.
    private final int MIN_FOLDING;
    // The maximum extension of the arm in ticks.
    private final int MAX_FOLDING;

    /**
     * Instantiates an foldable arm
     * @param motorSet     The motors and motor types.
     * @param rotationRange  The min rotation, max rotation, and ticks per degree.
     * @param foldingRange The min extension and max extension.
     */
    public FoldingArm(MotorSet motorSet, RotationRange rotationRange, FoldingRange foldingRange) {
        super(motorSet.MOTORS);

        this.ROTATION_MOTOR = motorSet.ROTATION_MOTOR;
        this.TICKS_PER_ROTATION_DEGREE = rotationRange.TICKS_PER_DEGREE;
        this.MIN_ROTATION = rotationRange.MIN_ROTATION;
        this.MAX_ROTATION = rotationRange.MAX_ROTATION;

        this.FOLDING_MOTOR = motorSet.FOLDING_MOTOR;
        this.TICKS_PER_FOLDING_DEGREE = foldingRange.TICKS_PER_DEGREE;
        this.MIN_FOLDING = foldingRange.MIN_FOLDING;
        this.MAX_FOLDING = foldingRange.MAX_FOLDING;
    }

    public double getRotationPower() {
        return ROTATION_POWER;
    }

    public double getFoldingPower() {
        return FOLDING_POWER;
    }

    // Return the rotation of the arm in degrees.
    public double getRotationDegree() {
        return ROTATION_MOTOR.getCurrentPosition() / TICKS_PER_ROTATION_DEGREE ;
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
        // Keep the target position within acceptable bounds
        targetPosition = Math.min(Math.max(targetPosition, MIN_ROTATION), MAX_ROTATION);
        ROTATION_MOTOR.setTargetPosition(targetPosition);

        /*
         * Calculate the direction that the arm will have to rotate.
         * Negative is down, positive is up
         */
        int direction = (int) Math.signum(targetPosition - ROTATION_MOTOR.getCurrentPosition());
        ROTATION_MOTOR.setPower(direction * ROTATION_POWER);

        ROTATION_MOTOR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public int getFoldingTicks() {
        return FOLDING_MOTOR.getCurrentPosition();
    }

    /**
     * @param direction The direction that the extension motor moves.
     *                  Positive values fold the arm, negative values retract it.
     */
    public void foldArm(double direction) throws NullPointerException, IllegalStateException {
        if (FOLDING_MOTOR == null) {
            throw new NullPointerException("The folding motor is null.");
        }

        if (FOLDING_MOTOR.getCurrentPosition() > MAX_FOLDING || FOLDING_MOTOR.getCurrentPosition() < MIN_FOLDING) {
            FOLDING_MOTOR.setPower(0);
            throw new IllegalStateException("Reached limits.");
        }

        FOLDING_MOTOR.setPower(direction * FOLDING_POWER);
    }

    public void foldArmToPosition(double degrees) {
        if (FOLDING_MOTOR == null) {
            return;
        }

        int targetPosition = (int) Math.round(degrees * TICKS_PER_FOLDING_DEGREE);
        // Keep the target position within acceptable bounds
        targetPosition = Math.min(Math.max(targetPosition, MIN_FOLDING), MAX_FOLDING);
        FOLDING_MOTOR.setTargetPosition(targetPosition);

        int direction = (int) Math.signum(targetPosition - FOLDING_MOTOR.getCurrentPosition());
        FOLDING_MOTOR.setPower(direction * FOLDING_POWER);

        FOLDING_MOTOR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void foldArmToPosition(int targetPosition) {
        if (FOLDING_MOTOR == null) {
            return;
        }

        // Keep the target position within acceptable bounds
        targetPosition = Math.min(Math.max(targetPosition, MIN_FOLDING), MAX_FOLDING);
        FOLDING_MOTOR.setTargetPosition(targetPosition);

        // Get the direction of turning.
        int direction = (int) Math.signum(targetPosition - FOLDING_MOTOR.getCurrentPosition());
        FOLDING_MOTOR.setPower(direction * FOLDING_POWER);

        FOLDING_MOTOR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}