package frc.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    public class ColorSensor {
        public final static int FIRMWARE_REV_REGISTER = 0x00;
        public final static int MANUFACTURER_REGISTER = 0x01;
        public final static int SENSOR_ID_REGISTER    = 0x02;
        public final static int COMMAND_REGISTER      = 0x03;
        public final static int COLOR_NUMBER_REGISTER = 0x04;
        public final static int RED_VALUE_REGISTER    = 0x05;
        public final static int GREEN_VALUE_REGISTER  = 0x06;
        public final static int BLUE_VALUE_REGISTER   = 0x07;
        public final static int WHITE_VALUE_REGISTER  = 0x08;
        public final static int COLOR_INDEX_REGISTER  = 0x09;
        public final static int RED_INDEX_REGISTER    = 0x0A;
        public final static int GREEN_INDEX_REGISTER  = 0x0B;
        public final static int BLUE_INDEX_REGISTER   = 0x0C;
        public final static int UNDEFINED_REGISTER    = 0x0D;
        
        public final static int RED_READING_REGISTER     = 0x0E;
        public final static int GREEN_READING_REGISTER   = 0x10;
        public final static int BLUE_READING_REGISTER    = 0x12;
        public final static int WHITE_READING_REGISTER_LSB   = 0x14;
        public final static int WHITE_READING_REGISTER_MSB   = 0x15;
        public final static int NORM_RED_READ_REGISTER   = 0x16;
        public final static int NORM_GREEN_READ_REGISTER = 0x18;
        public final static int NORM_BLUE_READ_REGISTER  = 0x1A;
        public final static int NORM_WHITE_READ_REGISTER = 0x1C;
        
        public final static int ACTIVE_MODE_COMMAND  = 0x00;
        public final static int PASSIVE_MODE_COMMAND = 0x01;
        public final static int OP_FREQ_50HZ_COMMAND = 0x35;
        public final static int OP_FREQ_60HZ_COMMAND = 0x36;
        public final static int BLACK_CAL_COMMAND    = 0x42;
        public final static int WHITE_BAL_COMMAND    = 0x43;    
    }    

    private I2C i2c1;
    private I2C i2c2;
    private int count;

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        this.i2c1 = new I2C(I2C.Port.kOnboard, 0x12 >> 1);
        this.i2c2 = new I2C(I2C.Port.kOnboard, 0x10 >> 1);
        this.count = 0;
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for
     * items like diagnostics that you want ran during disabled, autonomous,
     * teleoperated and test.
     *
     * This runs after the mode specific periodic functions, but before LiveWindow
     * and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        if ((++count % 25) != 0) {
            return;
        }

        byte[] buffer1 = new byte[1];
        boolean success1 = !i2c1.read(ColorSensor.COLOR_NUMBER_REGISTER, buffer1.length, buffer1);
        byte colorNumber1 = buffer1[0];

        byte[] buffer2 = new byte[1];
        boolean success2 = !i2c2.read(ColorSensor.COLOR_NUMBER_REGISTER, buffer2.length, buffer2);
        byte colorNumber2 = buffer2[0];

        System.out.println("success1=" + success1 + ", colorNumber1=" + colorNumber1 + ", success2=" + success2 + ", colorNumber2=" + colorNumber2);
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        Joystick joystick = new Joystick(0);

        JoystickButton buttonA = new JoystickButton(joystick, 1);
        JoystickButton buttonB = new JoystickButton(joystick, 2);
        JoystickButton buttonX = new JoystickButton(joystick, 3);
        JoystickButton buttonY = new JoystickButton(joystick, 4);

        buttonA.whenPressed(new InstantCommand(() -> {
            i2c1.write(ColorSensor.COMMAND_REGISTER, ColorSensor.PASSIVE_MODE_COMMAND);
        }));

        buttonB.whenPressed(new InstantCommand(() -> {
            i2c1.write(ColorSensor.COMMAND_REGISTER, ColorSensor.ACTIVE_MODE_COMMAND);
        }));

        buttonX.whenPressed(new InstantCommand(() -> {
            i2c2.write(ColorSensor.COMMAND_REGISTER, ColorSensor.PASSIVE_MODE_COMMAND);
        }));

        buttonY.whenPressed(new InstantCommand(() -> {
            i2c2.write(ColorSensor.COMMAND_REGISTER, ColorSensor.ACTIVE_MODE_COMMAND);
        }));
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }
}
