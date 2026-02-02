package org.firstinspires.ftc.teamcode.hardwaresystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Set;

/**
 * An abstract class to defined the methods that robot arms are capable of.
 */
public abstract class Arm {
    protected final Set<DcMotor> MOTORS;

    /**
     * Instantiate a new {@link Arm} with a {@link Set} of {@link DcMotor}s.
     *
     * @param motors The {@link DcMotor}s contained within this {@link Arm}.
     */
    public Arm(Set<DcMotor> motors) {
        MOTORS = motors;
        // The arm motors will attempt to resist external forces　(e.g.,
        // gravity).
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