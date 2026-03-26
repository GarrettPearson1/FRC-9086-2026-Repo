package frc.robot.subsystems;

import com.studica.frc.AHRS;

// I'm not sure why 2025 didn't have one already
public class Gyro {
    // Note that the 2025 constructor requires a data rate.
    private final AHRS m_gyro = new AHRS(AHRS.NavXComType.kMXP_SPI, AHRS.NavXUpdateRate.k50Hz);
    
    private double yawOffset;

    public Gyro() {
    }

    
}
