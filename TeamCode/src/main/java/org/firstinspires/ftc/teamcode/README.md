# Table of Contents

- [teamcode/](#teamcode)
    - [`CustomLinearOp`](#CustomLinearOp)
    - [`Auto`](#Auto)
    - [`DriverMode`](#DriverMode)
    - [`TeamColor`](#TeamColor)
    - [`TeamSide`](#Teamside)
- [hardwareSystems/](#hardwareSystems)
    - [`Wheels`](#Wheels)
    - [`MecanumWheels`](#MecanumWheels)
    - [`Arm`](#Arm)
    - [`ExtendableArm`](#Extendablearm)

# [teamcode/](./)

## [`CustomLinearOp`](./CustomLinearOp.java)

A custom LinearOpMode that is the parent class of [`Auto`](#Auto) and [`DriverMode`](#DriverMode).
Its `runOpMode()` initializes all the robot's hardware and contains methods for auto-sleep,
sleeping while motors and continuous servos are runnin.

## [`Auto`](./Auto.java)

The Autonomous class, which runs without driver input.
Child class of [`CustomLinearOp`](#CustomLinearOp).
The annotation `@Autonomous(name = "Auto")` means that the class will be considered
an Autonomous program with the name of "Auto."
The `runOpMode()` method runs automatically without the need to do anything.
The first line of `runOpMode()` should be `super.runOpMode()` to run the parent class's hardware
initialization.

## [`DriverMode`](./DriverMode.java)

The TeleOp class which runs using driver input.
Child class of [`CustomLinearOp`](#CustomLinearOp).
The annotation `@TeleOp(name = "DriverMode")` means that the class will be considered
a TeleOp program with the name of "DriverMode."
The `runOpMode()` method runs automatically without the need to do anything.
The first line of `runOpMode()` should be `super.runOpMode()` to run the parent class's hardware
initialization.

## [`TeamColor`](./TeamColor.java)

An enum that states whether the robot is on red or blue side.

## [`TeamSide`](./TeamSide.java)

An enum that states whether the robot is on far or near side.

# [hardwareSystems/](./hardwareSystems/)

This subdirectory contains helper classes.
The classes are meant to separate and organize the various systems of the robot(e.g. arms, wheels,
etc.).
Contain methods for basic tasks such as driving and lifting the arm.
Some of the classes(e.g. [`Arm`](#Arm) and [`Wheels`](#Wheels)) are abstract and are meant to be
used as superclasses.
Being abstract classes rather than interfaces prevents multiple implementing.

## [`MotorType`](./hardwareSystems/MotorType.java)

An enum that stores the type of motor(e.g. Tetrix Torquenado) and its number of ticks per
revolution.

## [`Wheels`](./hardwareSystems/Wheels.java)

A abstract class for the robot's wheels.
Contains a HashSet of all motors.
Sets each motor to float when zero power is applied.

## [`MecanumWheels`](./hardwareSystems/MecanumWheels.java)

A subclass of the [`Wheels`](#Wheels) class for controlling the driving of a four-mecanum wheel
system.
Contains an inner class(`MotorParams`) to pass in the motors and motor types to the `MecanumWheels`
constructor.

## [`Arm`](./hardwareSystems/Arm.java)

An abstract class to control the robot's arm system.
Contains a HashSet of all motors.
Sets each motor to brake when zero power is applied.

> [!Note]
> `Arm` does not contain the servos for controlling the claw.
> For that, look at [`Claw`](#Claw)

## [`ExtendableArm`](./hardwareSystems/ExtendableArm.java)

A subclass of [`Arm`](#Arm) that controls a rotating, extendable arm.
Contains four inner classes(i.e. `MotorSet`, `RotationRange`,
and `ExtensionRange`) that group together parameters for the constructor.
More specific details can be found in [`Arm`](#Arm).
The current system is admittedly clunky.
If it becomes cumbersome, please do change it.

# [`Claw`](./hardwareSystems/Claw.java)

An abstract class to control the robot's claw.
Has properties for servos to rotate in the X, Y, and Z-axes.
If any of the servos are not needed, set them to null.
The class methods check for null servo values.

# [`IntakeClaw`](./hardwareSystems/IntakeClaw.java)

A subclass of [`Claw`](#Claw) that controls a claw with a intake servo.
Each method checks for a null intake servo.

## [`Webcam`](./hardwareSystems/Webcam.java)

A class for vision and color detection.
Currently still in progress. 