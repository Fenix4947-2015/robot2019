package frc.robot.joysticks;

public enum XBoxButton {
    A(1),
    B(2), 
    X(3), 
    Y(4), 
    LB(5), 
    RB(6), 
    BACK(7), 
    START(8),
    LEFT_STICK(9), 
    RIGHT_STICK(10);

    private int value;

    private XBoxButton(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
