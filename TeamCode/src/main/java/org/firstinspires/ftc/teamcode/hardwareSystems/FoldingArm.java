package org.firstinspires.ftc.teamcode.hardwareSystems;

import androidx.annotation.NonNull;

import java.util.HashSet;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class FoldingArm extends Arm {
    /**
     * Passed into the {@code FoldingArm} constructor.
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
     * Passed into the {@code FoldingArm} constructor.
     * Contains the min rotation, max rotation, and ticks per degree.
     */
    public static class RotationRange {
        /**
         * The minimum rotation of the arm in ticks.
         */
        private final int MIN_ROTATION;
        /**
         * The maximum rotation of the arm in ticks.
         */
        private final int MAX_ROTATION;

        /**
         * The angle that the arm rotation starts from.
         * 0.0 ticks will be considered equal to `INITIAL_ANGLE`.
         */
        private final double INITIAL_ANGLE;

        /**
         * How many ticks it takes to rotate the arm by one degree.
         */
        private final double TICKS_PER_DEGREE;

        public RotationRange(int minRotation, int maxRotation, double ticksPerDegree) {
            this(minRotation, maxRotation, 0, ticksPerDegree);
        }

        public RotationRange(int minRotation, int maxRotation, double initialAngle, double ticksPerDegree) {
            this.MIN_ROTATION = minRotation;
            this.MAX_ROTATION = maxRotation;
            this.INITIAL_ANGLE = initialAngle;
            this.TICKS_PER_DEGREE = ticksPerDegree;
        }
    }

    /**
     * Passed into the {@code FoldingArm} constructor.
     * Contains the min folding, max folding.
     */
    public static class FoldingRange {
        // The minimum folding of the arm in ticks.
        private final int MIN_FOLDING;
        // The maximum extension of the arm in ticks.
        private final int MAX_FOLDING;

        /**
         * The angle that the arm rotation starts from.
         * 0 ticks will be considered equal to `INITIAL_ANGLE`.
         */
        private double INITIAL_ANGLE;

        // How many ticks are in a degree.
        private final double TICKS_PER_DEGREE;

        public FoldingRange(int minFolding, int maxFolding, double ticksPerDegree) {
            this.MIN_FOLDING = minFolding;
            this.MAX_FOLDING = maxFolding;

            this.TICKS_PER_DEGREE = ticksPerDegree;
        }

        public FoldingRange(int minFolding, int maxFolding, double initialAngle, double ticksPerDegree) {
            this.MIN_FOLDING = minFolding;
            this.MAX_FOLDING = maxFolding;

            this.INITIAL_ANGLE = initialAngle;

            this.TICKS_PER_DEGREE = ticksPerDegree;
        }
    }

    /**
     * The motor that rotates the arm up and down.
     */
    private final DcMotor ROTATION_MOTOR;
    /**
     * The motor power that the arm uses when rotating.
     */
    private static final double ROTATION_POWER = 0.67;
    /**
     * The minimum rotation of the arm in ticks.
     */
    private final int MIN_ROTATION;
    /**
     * The maximum rotation of the arm in ticks.
     */
    private final int MAX_ROTATION;
    /**
     * The angle that the arm rotation starts from.
     * 0 ticks will be considered equal to `INITIAL_ANGLE`.
     */
    private final double INITIAL_ROTATION_ANGLE;
    /**
     * How many ticks it takes to rotate the arm by one degree.
     */
    private final double TICKS_PER_ROTATION_DEGREE;

    /**
     * The motor that folds and retracts the arm.
     */
    private final DcMotor FOLDING_MOTOR;
    /**
     * The motor power that the arm uses when rotating.
     */
    private static final double FOLDING_POWER = 0.75;
    /**
     * The minimum extension of the arm in ticks.
     */
    private final int MIN_FOLDING;
    /**
     * The maximum extension of the arm in ticks.
     */
    private final int MAX_FOLDING;
    /**
     * The angle that the arm folding starts from.
     * 0 ticks will be considered equal to `INITIAL_ANGLE`.
     */
    private final double INITIAL_FOLDING_ANGLE;
    /**
     * How many ticks it takes to rotate the arm by one degree.
     */
    private final double TICKS_PER_FOLDING_DEGREE;

    /**
     * Instantiates an foldable arm
     *
     * @param motorSet      The motors and motor types.
     * @param rotationRange The min rotation, max rotation, and ticks per degree.
     * @param foldingRange  The min extension and max extension.
     */
    public FoldingArm(MotorSet motorSet, RotationRange rotationRange, FoldingRange foldingRange) {
        super(motorSet.MOTORS);

        this.ROTATION_MOTOR = motorSet.ROTATION_MOTOR;
        this.ROTATION_MOTOR.setDirection(DcMotorSimple.Direction.REVERSE);

        this.MIN_ROTATION = rotationRange.MIN_ROTATION;
        this.MAX_ROTATION = rotationRange.MAX_ROTATION;
        this.INITIAL_ROTATION_ANGLE = rotationRange.INITIAL_ANGLE;
        this.TICKS_PER_ROTATION_DEGREE = rotationRange.TICKS_PER_DEGREE;

        this.FOLDING_MOTOR = motorSet.FOLDING_MOTOR;
        this.MIN_FOLDING = foldingRange.MIN_FOLDING;
        this.MAX_FOLDING = foldingRange.MAX_FOLDING;
        this.INITIAL_FOLDING_ANGLE = foldingRange.INITIAL_ANGLE;
        this.TICKS_PER_FOLDING_DEGREE = foldingRange.TICKS_PER_DEGREE;
    }

    private void checkNullRotationMotor() throws NullPointerException {
        if (ROTATION_MOTOR == null) {
            throw new NullPointerException("WARNING: ARM ROTATION MOTOR IS NULL!");
        }
    }

    private void checkNullFoldingMotor() throws NullPointerException {
        if (FOLDING_MOTOR == null) {
            throw new NullPointerException("WARNING: ARM FOLDING MOTOR IS NULL!");
        }
    }

    public double getRotationPower() {
        return ROTATION_POWER;
    }

    public double getFoldingPower() {
        return FOLDING_POWER;
    }

    public DcMotor getRotationMotor() throws NullPointerException {
        checkNullRotationMotor();

        return ROTATION_MOTOR;
    }

    public DcMotor getFoldingMotor() throws NullPointerException {
        checkNullFoldingMotor();

        return FOLDING_MOTOR;
    }
    
    public int getRotationTicks() throws NullPointerException {
        checkNullRotationMotor();
        return ROTATION_MOTOR.getCurrentPosition();
    }

    /**
     * Return the rotation of the arm in degrees.
     *
     * @return A double representing the rotation angle of the arm in degrees.
     * @throws NullPointerException If {@code ROTATION_MOTOR} is null.
     */
    public double getRotationDegrees() throws NullPointerException {
        checkNullRotationMotor();

        return ROTATION_MOTOR.getCurrentPosition() / TICKS_PER_ROTATION_DEGREE + INITIAL_ROTATION_ANGLE;
    }

    /**
     * Rotate the arm with a set velocity.
     * Stop the motor if it is out of bounds.
     *
     * @param direction The direction that the arm should rotate in.
     *                  Positive rotates it up, negative rotates it down, zero stops the motor.
     */
    public void rotateArm(double direction) throws NullPointerException, IllegalStateException{
        checkNullRotationMotor();

        if (ROTATION_MOTOR.getCurrentPosition() > MAX_ROTATION || ROTATION_MOTOR.getCurrentPosition() < MIN_ROTATION) {
            ROTATION_MOTOR.setPower(0);
            throw new IllegalStateException("Arm rotation reached limits");
        }

        ROTATION_MOTOR.setPower(direction * ROTATION_POWER);
    }

    /**
     * Rotates the arm to a position specified in degrees.
     *
     * @param degrees The position the arm moves to.
     *                The arm's starting position is 0 degrees.
     */
    public void rotateArmToAngle(double degrees) throws NullPointerException {
        checkNullRotationMotor();
        
        double targetDegrees = degrees - INITIAL_ROTATION_ANGLE;
        int targetPosition = (int) Math.round(targetDegrees * TICKS_PER_ROTATION_DEGREE);
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

    public int getFoldingTicks() throws NullPointerException {
        if (FOLDING_MOTOR == null) {
            throw new NullPointerException("WARNING: ARM FOLDING MOTOR IS NULL");
        }
        
        return FOLDING_MOTOR.getCurrentPosition();
    }

    /**
     * Return the folding of the arm in degrees.
     * 
     * @return A double representing the folding angle of the arm in degrees.
     * @throws NullPointerException If {@code FOLDING_MOTOR} is null.
     */
    public double getFoldingDegrees() throws NullPointerException {
        checkNullFoldingMotor();
        
        return FOLDING_MOTOR.getCurrentPosition() / TICKS_PER_FOLDING_DEGREE + INITIAL_FOLDING_ANGLE;
    }

    /**
     * Fold the arm with a given power.
     *
     * @param direction The direction that the extension motor moves.
     *                  Positive values fold the arm, negative values retract it.
     */
    public void foldArm(double direction) throws NullPointerException, IllegalStateException {
        checkNullFoldingMotor();

        if (FOLDING_MOTOR.getCurrentPosition() > MAX_FOLDING || FOLDING_MOTOR.getCurrentPosition() < MIN_FOLDING) {
            FOLDING_MOTOR.setPower(0);
            throw new IllegalStateException("Arm folding reached limits.");
        }

        FOLDING_MOTOR.setPower(direction * FOLDING_POWER);
    }

    /**
     * Fold the arm to a certain number of degrees.
     *
     * @param degrees The position to move the join of the arm in degrees.
     */
    public void foldArmToAngle(double degrees) throws NullPointerException {
        checkNullFoldingMotor();
        
        double targetDegrees = degrees - INITIAL_FOLDING_ANGLE;
        int targetPosition = (int) Math.round(targetDegrees * TICKS_PER_FOLDING_DEGREE);
        // Keep the target position within acceptable bounds
        targetPosition = Math.min(Math.max(targetPosition, MIN_FOLDING), MAX_FOLDING);
        FOLDING_MOTOR.setTargetPosition(targetPosition);

        int direction = (int) Math.signum(targetPosition - FOLDING_MOTOR.getCurrentPosition());
        FOLDING_MOTOR.setPower(direction * FOLDING_POWER);

        FOLDING_MOTOR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Fold the arm to a certain number of ticks.
     *
     * @param targetPosition The position to move the join of the arm in ticks.
     */
    public void foldArmToPosition(int targetPosition) throws NullPointerException {
        checkNullFoldingMotor();

        // Keep the target position within acceptable bounds
        targetPosition = Math.min(Math.max(targetPosition, MIN_FOLDING), MAX_FOLDING);
        FOLDING_MOTOR.setTargetPosition(targetPosition);

        // Get the direction of turning.
        int direction = (int) Math.signum(targetPosition - FOLDING_MOTOR.getCurrentPosition());
        FOLDING_MOTOR.setPower(direction * FOLDING_POWER);

        FOLDING_MOTOR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}