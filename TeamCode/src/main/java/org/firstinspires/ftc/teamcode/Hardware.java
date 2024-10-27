package org.firstinspires.ftc.teamcode;

import java.util.HashMap;
import java.util.HashSet;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.hardwareSystems.*;

public class Hardware {
    // The opMode to access the hardware map from.
    private final OpMode OP_MODE;

    // Whether the robot will automatically sleep after each command.
    // Only applicable in LinearOpMode.
    private boolean autoSleepEnabled;

    /* Robot systems */
    private final MecanumWheels WHEELS;
    private final ExtendableArm ARM;
    private final IntakeClaw CLAW;
    private final Webcam WEBCAM;

    private final DigitalChannel COLOR_SWITCH;
    private final DigitalChannel SIDE_SWITCH;

    private final CRServo INTAKE_SERVO;

    // Detects whether there is a sample in the claw.
    private final DigitalChannel INTAKE_SENSOR;

    public Hardware(OpMode opMode) {
        this.OP_MODE = opMode;
        autoSleepEnabled = true;

        WHEELS = initWheels();
        ARM = null; // initArm();
        CLAW = initClaw();
        WEBCAM = null; // new Webcam(OP_MODE.hardwareMap.get(WebcamName.class, "webcam"));

        COLOR_SWITCH = null; //OP_MODE.hardwareMap.get(DigitalChannel.class, "color_switch");
        SIDE_SWITCH = null; //OP_MODE.hardwareMap.get(DigitalChannel.class, "side_switch");

        INTAKE_SENSOR = null;
        INTAKE_SERVO = OP_MODE.hardwareMap.get(CRServo.class, "intakeServo");
    }

    /**
     * Initiates all hardware needed for the Wheels.
     */
    private MecanumWheels initWheels() {
        /*
         * Define wheels system hardware here.
         * e.g. exampleMotor = OP_MODE.hardwareMap.get(DcMotor.class, "example_motor");
         */
        DcMotor frontLeftMotor = OP_MODE.hardwareMap.get(DcMotor.class, "frontLeftWheel");
        DcMotor frontRightMotor = OP_MODE.hardwareMap.get(DcMotor.class, "frontRightWheel");
        DcMotor backLeftMotor = OP_MODE.hardwareMap.get(DcMotor.class, "backLeftWheel");
        DcMotor backRightMotor = OP_MODE.hardwareMap.get(DcMotor.class, "backRightWheel");

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
        /*
         * Define arm hardware here.
         * e.g. exampleMotor = OP_MODE.hardwareMap.get(DcMotor.class, "example_motor");
         */
        ExtendableArm.MotorParams motorParams = new ExtendableArm.MotorParams(
                OP_MODE.hardwareMap.get(DcMotor.class, "rotationMotor"),
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
        return new IntakeClaw(
                OP_MODE.hardwareMap.get(Servo.class, "clawXServo"),
                null,
                OP_MODE.hardwareMap.get(Servo.class, "clawZServo"),
                OP_MODE.hardwareMap.get(CRServo.class, "intakeServo")
        );
    }

    public MecanumWheels getWheels() {
        return WHEELS;
    }

    public ExtendableArm getArm() {
        return ARM;
    }

    public IntakeClaw getClaw() {
        return CLAW;
    }

    public Webcam getWebCam() {
        return WEBCAM;
    }

    public DigitalChannel getColorSwitch() {
        return COLOR_SWITCH;
    }

    public DigitalChannel getSideSwitch() {
        return SIDE_SWITCH;
    }

    /**
     * Attempts to cast the OP_MODE to a LinearOpMode.
     * Returns null if it fails.
     *
     * @return a linearOpMode representation of OP_MODE if possible
     * Else returns null
     */
    public LinearOpMode getLinearOpMode() {
        try {
            return (LinearOpMode) OP_MODE;

        } catch (ClassCastException e) {
            return null;
        }
    }

    public boolean getAutoSleepEnabled() {
        return autoSleepEnabled;
    }

    public void setAutoSleepEnabled(boolean autoSleepEnabled) {
        this.autoSleepEnabled = autoSleepEnabled;
    }

    /**
     * Sleeps the robot while any motors are running.
     */
    public void autoSleep() {
        HashSet<DcMotor> allMotors = new HashSet<>(ARM.getMotors());
        allMotors.addAll(WHEELS.getMotors());

        autoSleep(allMotors);
    }

    /**
     * Sleeps the robot while the given motors are running.
     *
     * @param motors The motors that are running.
     */
    public void autoSleep(HashSet<DcMotor> motors) {
        LinearOpMode linearOp = getLinearOpMode();

        // Does nothing if it isn't a LinearOpMode.
        if (linearOp == null) {
            return;
        }

        // Sleep while any of the motors are still running.
        while (motors.stream().anyMatch(DcMotor::isBusy)) {
            linearOp.idle();
        }
    }
}