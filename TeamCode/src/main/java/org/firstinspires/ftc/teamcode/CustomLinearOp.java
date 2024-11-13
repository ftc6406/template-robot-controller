package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.hardwareSystems.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class CustomLinearOp extends LinearOpMode {
    // Whether the robot will automatically sleep after each command.
    protected boolean autoSleepEnabled;

    /* Robot systems */
    protected MecanumWheels WHEELS;
    protected ExtendableArm ARM;
    protected IntakeClaw CLAW;
    protected Webcam WEBCAM;

    // The robot's color and side.
    protected StartPosition startPosition;
    protected DigitalChannel COLOR_SWITCH;
    protected DigitalChannel SIDE_SWITCH;

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
    }

    /**
     * Initialize all hardware devices used by the robot,
     * and print out any devices that are missing.
     */
    public void initialize() {
        autoSleepEnabled = true;

        WHEELS = initWheels();
        ARM = initArm();
        CLAW = initClaw();
        WEBCAM = initWebCam();

        // Try to read the start position
        try {
            startPosition = StartPosition.valueOf(FileManager.readFile("position.txt").trim());

        } catch (IOException e) {
            startPosition = StartPosition.RED_NEAR;
        }

        COLOR_SWITCH = null; //OP_MODE.hardwareMap.get(DigitalChannel.class, "color_switch");
        SIDE_SWITCH = null; //OP_MODE.hardwareMap.get(DigitalChannel.class, "side_switch");

        telemetry.addData("Missing devices", getMissingHardwareDevices());
    }

    /**
     * Initiates all hardware needed for the wheels.
     * <br>
     * <strong>When starting a new season, change the return type from `Wheels` to the desired return type.</strong>
     */
    private MecanumWheels initWheels() {
        // Prevent multiple instantiation.
        if (WHEELS != null) {
            return WHEELS;
        }

        MecanumWheels.MotorSet motorSet;
        // Catch errors that result from hardware not being connected.
        try {
            /*
             * Define wheels system hardware here.
             * e.g. hardwareMap.get(DcMotor.class, "exampleMotor");
             */
            motorSet = new MecanumWheels.MotorSet(
                    hardwareMap.get(DcMotor.class, "frontLeftWheel"),
                    hardwareMap.get(DcMotor.class, "frontRightWheel"),
                    hardwareMap.get(DcMotor.class, "backLeftWheel"),
                    hardwareMap.get(DcMotor.class, "backRightWheel")
            );

        } catch (Exception e) {
            motorSet = new MecanumWheels.MotorSet();
        }

        // Approximately measured from the CAD model in inches
        double wheelCircumference = 4.0 * Math.PI;
        double gearRatio = 1.0;
        double ticksPerInch = MotorType.TETRIX_TORQUENADO.getTicksPerRotation() * gearRatio / wheelCircumference;
        // Approximately measured from CAD
        Wheels.WheelDistances wheelDistances = new Wheels.WheelDistances(
                10.0,
                14.25
        );

        return new MecanumWheels(motorSet, wheelDistances, ticksPerInch);
    }

    /**
     * Initiate all hardware needed for the arm.
     * <strong>When starting a new season, change the return type from `Arm` to the desired return type.</strong>
     */
    private ExtendableArm initArm() {
        // Prevent multiple instantiation.
        if (ARM != null) {
            return ARM;
        }

        ExtendableArm.MotorSet motorSet;
        // Catch errors that result from hardware not being connected.
        try {
            /*
             * Define arm hardware here.
             * e.g. hardwareMap.get(DcMotor.class, "exampleMotor");
             */
            motorSet = new ExtendableArm.MotorSet(
                    hardwareMap.get(DcMotor.class, "rotationMotor"),
                    null
            );

        } catch (Exception e) {
            motorSet = new ExtendableArm.MotorSet();
        }

        double gearRatio = 120.0 / 40.0;
        ExtendableArm.RotationRange rotationRange = new ExtendableArm.RotationRange(
                0,
                1080,
                MotorType.TETRIX_TORQUENADO.getTicksPerRotation()
                        / 360.0
                        * gearRatio
        );

        ExtendableArm.ExtensionRange extensionRange = new ExtendableArm.ExtensionRange(
                0,
                1000
        );

        return new ExtendableArm(motorSet, rotationRange, extensionRange);
    }

    /**
     * Initiate all hardware needed for the claw.
     * <strong>When starting a new season, change the return type from `Claw` to the desired return type.</strong>
     */
    public IntakeClaw initClaw() {
        // Prevent multiple instantiation.
        if (CLAW != null) {
            return null;
        }

        // Catch errors that result from hardware not being connected.
        try {
            /*
             * Define claw hardware here.
             * e.g. hardwareMap.get(Servo.class, "exampleServo");
             */
            return new IntakeClaw(
                    hardwareMap.get(Servo.class, "clawXServo"),
                    null,
                    hardwareMap.get(Servo.class, "clawZServo"),
                    hardwareMap.get(CRServo.class, "intakeServo")
            );

        } catch (Exception e) {
            return new IntakeClaw();
        }
    }

    public Webcam initWebCam() {
        int[] resolution = {640, 480};
        return new Webcam(
                hardwareMap.get(WebcamName.class, "webcam"),
                resolution
        );
    }

    public HashSet<DcMotor> getAllDcMotors() {
        HashSet<DcMotor> motors = new HashSet<>();
        // hardware.dcMotor stores all the DcMotors as name-device pairs.
        for (Map.Entry<String, DcMotor> ele : hardwareMap.dcMotor.entrySet()) {
            motors.add(ele.getValue());
        }

        return motors;
    }

    /**
     * Gets all CR servos if they are present.
     *
     * @return A HashSet containing all the CR servos used by this robot.
     */
    public HashSet<CRServo> getAllCrServos() {
        HashSet<CRServo> crServos = new HashSet<>();
        // hardware.crservo stores all the CRServos as name-device pairs.
        for (Map.Entry<String, CRServo> hardwareDevice : hardwareMap.crservo.entrySet()) {
            crServos.add(hardwareDevice.getValue());
        }

        return crServos;
    }

    /**
     * Get all the names in the `HardwareMap` that that are not connected to a device.
     * <br>
     * <em><strong>THIS METHOD IS YET TO BE TESTED!</strong></em>
     * @return A `HashSet` of all the hardware devices that can not be found.
     */
    public HashSet<String> getMissingHardwareDevices() {
        HashSet<String> missingHardwareDevices = new HashSet<>();

        // Loop through each `DeviceMapping`(e.g. `Servo`s and `DcMotor`s).
        for (HardwareMap.DeviceMapping<? extends HardwareDevice> deviceMapping : hardwareMap.allDeviceMappings) {
            // Check if each device in the mapping is null.
            for (Map.Entry<String, ? extends HardwareDevice> hardwareDevice : deviceMapping.entrySet()) {
                if (hardwareDevice.getValue() == null) {
                    missingHardwareDevices.add(hardwareDevice.getKey());
                }
            }
        }

        return missingHardwareDevices;
    }

    /**
     * Sleeps the robot while any motors or CR servos are running.
     */
    public void autoSleep() {
        autoSleep(getAllDcMotors(), getAllCrServos());
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
            idle();
        }

        // Sleep while any of the CR servos are still running.
        while (crServos.stream().anyMatch(crServo -> crServo.getPower() != 0)) {
            idle();
        }
    }
}