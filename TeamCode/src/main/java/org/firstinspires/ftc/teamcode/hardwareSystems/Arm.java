package org.firstinspires.ftc.teamcode.hardwareSystems;

import java.util.HashSet;

import com.qualcomm.robotcore.hardware.*;

public abstract class Arm {
    protected final HashSet<DcMotor> MOTORS;
    protected final MotorType MOTOR_TYPE;

    public Arm(HashSet<DcMotor> motors) {
        this(motors, MotorType.TETRIX_TORQUENADO);
    }

    public Arm(HashSet<DcMotor> motors, MotorType motorType) {
        this.MOTORS = motors;

        this.MOTOR_TYPE = motorType;
    }

    /**
     * Get all the {@code DcMotor}s that are included in this arm system.
     *
     * @return A {@code HashSet} that contains every DcMotor included in this arm system.
     */
    public HashSet<DcMotor> getMotors() {
        return MOTORS;
    }

    public MotorType getMotorType() {
        return MOTOR_TYPE;
    }
}