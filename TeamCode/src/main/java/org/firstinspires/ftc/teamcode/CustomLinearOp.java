package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.hardwareSystems.*;

import java.util.HashSet;

public class CustomLinearOp extends LinearOpMode {
    // Whether the robot will automatically sleep after each command.
    protected boolean autoSleepEnabled;

    /* Robot systems */
    protected MecanumWheels WHEELS;
    protected ExtendableArm ARM;
    protected IntakeClaw CLAW;
    protected Webcam WEBCAM;

    protected DigitalChannel COLOR_SWITCH;
    protected DigitalChannel SIDE_SWITCH;

    // Detects whether there is a sample in the claw.
    protected DigitalChannel INTAKE_SENSOR;

    // Red or blue team
    protected TeamColor teamColor;
    // Far or near
    protected TeamSide teamSide;

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
    }

    public void initialize() {
        autoSleepEnabled = true;

        WHEELS = initWheels();
        ARM = null; // initArm();
        CLAW = initClaw();
        WEBCAM = new Webcam(hardwareMap.get(WebcamName.class, "webcam"));

        COLOR_SWITCH = null; //OP_MODE.hardwareMap.get(DigitalChannel.class, "color_switch");
        SIDE_SWITCH = null; //OP_MODE.hardwareMap.get(DigitalChannel.class, "side_switch");

        INTAKE_SENSOR = null;}

    /**
     * Initiates all hardware needed for the Wheels.
     */
    private MecanumWheels initWheels() {
        // Prevent multiple instantiation.
        if (WHEELS != null) {
            return WHEELS;
        }

        /*
         * Define wheels system hardware here.
         * e.g. exampleMotor = OP_MODE.hardwareMap.get(DcMotor.class, "example_motor");
         */
        DcMotor frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftWheel");
        DcMotor frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightWheel");
        DcMotor backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftWheel");
        DcMotor backRightMotor = hardwareMap.get(DcMotor.class, "backRightWheel");

        MecanumWheels.MotorParams motorParams = new MecanumWheels.MotorParams(
                frontLeftMotor,
                frontRightMotor,
                backLeftMotor,
                backRightMotor
        );

        // Approximately measured from the CAD model in inches
        double wheelCircumference = 4.0 * Math.PI;
        double gearRatio = 30.0 / 30.0;
        double ticksPerInch = MotorType.TETRIX_TORQUENADO.getTicksPerRotation() * gearRatio / wheelCircumference;

        return new MecanumWheels(motorParams, ticksPerInch);
    }

    /**
     * Initiate all hardware needed for the Arm.
     */
    private ExtendableArm initArm() {
        // Prevent multiple instantiation.
        if (ARM != null) {
            return ARM;
        }

        /*
         * Define arm hardware here.
         * e.g. exampleMotor = OP_MODE.hardwareMap.get(DcMotor.class, "example_motor");
         */
        ExtendableArm.MotorParams motorParams = new ExtendableArm.MotorParams(
                null, // OP_MODE.hardwareMap.get(DcMotor.class, "rotationMotor"),
                null
        );

        double gearRatio = 120.0 / 40.0;
        ExtendableArm.RotationParams rotationParams = new ExtendableArm.RotationParams(
                0,
                1080,
                MotorType.TETRIX_TORQUENADO.getTicksPerRotation()
                        / 360.0
                        * gearRatio
        );

        ExtendableArm.ExtensionParams extensionParams = new ExtendableArm.ExtensionParams(
                0,
                1000
        );

        return new ExtendableArm(motorParams, rotationParams, extensionParams);
    }

    public IntakeClaw initClaw() {
        // Prevent multiple instantiation.
        if (CLAW != null) {
            return null;
        }

        return new IntakeClaw(
                hardwareMap.get(Servo.class, "clawXServo"),
                null,
                hardwareMap.get(Servo.class, "clawZServo"),
                hardwareMap.get(Servo.class, "intakeServo")
        );
    }

    public HashSet<DcMotor> getAllMotors() {
        HashSet<DcMotor> allMotors = new HashSet<>(ARM.getMotors());
        allMotors.addAll(WHEELS.getMotors());

        return allMotors;
    }

    /**
     * Gets all CR servos if they are present.
     * @return A HashSet containing all the CR servos used by this robot.
     */
    public HashSet<CRServo> getAllCrServos() {
        // If the claw is an IntakeClaw
        if (CLAW instanceof IntakeClaw) {
            return ((IntakeClaw) CLAW).getCrServos();
        }

        return new HashSet<>();
    }

    /**
     * Sleeps the robot while any motors or CR servos are running.
     */
    public void autoSleep() {
        telemetry.addLine("No param autoSleep()");
        autoSleep(getAllMotors(), getAllCrServos());
    }

    /**
     * Sleeps the robot while the given motors and CRServos are running.
     *
     * @param motors   The motors that are running.
     * @param crServos The CR servos that are running.
     */
    public void autoSleep(HashSet<DcMotor> motors, HashSet<CRServo> crServos) {
        // Sleep while any of the motors are still running.
        while (motors.stream().anyMatch(DcMotor::isBusy)) {
            sleep(1);
        }

        // Sleep while any of the CR servos are still running.
        while (crServos.stream().anyMatch(crServo -> crServo.getPower() != 0)) {
            sleep(1);
        }
    }
}