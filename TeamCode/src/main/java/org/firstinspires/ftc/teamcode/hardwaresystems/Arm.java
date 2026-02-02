package org.firstinspires.ftc.teamcode.hardwaresystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.HashSet;

/**
 * An abstract class to defined the methods that robot arms are capable of.
 */
public abstract class Arm {
    protected final HashSet<DcMotor> MOTORS;

    public Arm(HashSet<DcMotor> motors) {
        MOTORS = motors;
        // The arm motors will attempt to resist external forces　(e.g., gravity).
        for (DcMotor motor : MOTORS) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

    /**
     * Get all the {@code DcMotor}s that are included in this arm system.
     *
     * @return A {@code HashSet} that contains every DcMotor included in this
     * arm system.
     */
    public HashSet<DcMotor> getMotors() {
        return MOTORS;
    }
}