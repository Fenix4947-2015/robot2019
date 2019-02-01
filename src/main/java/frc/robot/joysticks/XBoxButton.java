package frc.robot.joysticks;

public enum XBoxButton {
    A(1),
    B(2), 
    X(3), 
    Y(4), 
    BUMPER_LEFT(5), 
    BUMPER_RIGHT(6), 
    BACK(7), 
    START(8),
    STICK_LEFT(9), 
    STICK_RIGHT(10);

    private final int value;

    private XBoxButton(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}