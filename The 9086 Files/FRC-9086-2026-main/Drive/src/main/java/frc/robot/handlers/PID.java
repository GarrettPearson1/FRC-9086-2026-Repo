package frc.robot.handlers;

public class PID {
    private double max;
    private double min;

    private double K_p;
    private double K_i;
    private double K_d;

    private double _pre_error = 0;
    private double _integral = 0;

    private double lt;

    public PID(double max, double min, double K_p, double K_i, double K_d) {
        this.max = max;
        this.min = min;
        this.K_p = K_p;
        this.K_i = K_i;
        this.K_d = K_d;

        lt = System.currentTimeMillis();
    }

    public void resetLt() {
        lt = System.currentTimeMillis();
    }

    public double calculate(double setpoint, double pv) {
        double _dt = System.currentTimeMillis() - lt;

        // Calculate error
        double error = setpoint - pv;

        // Proportional term
        double Pout = K_p * error;

        // Integral term
        double pint = _integral;
        _integral += error * _dt;
        double Iout = K_i * _integral;

        // Derivative term
        double derivative = (error - _pre_error) / _dt;
        double Dout = K_d * derivative;

        // Calculate total output
        double output = Pout + Iout + Dout;

        // Restrict to max/min
        if( output > max ) {
            _integral = pint; // prevent integral wind
            output = max;
        }
        else if( output < min ) {
            _integral = pint;
            output = min;
        }

        // Save error to previous error
        _pre_error = error;

        lt = System.currentTimeMillis();
        if (Double.isNaN(output))
            return 0;
        return output;
    }
}