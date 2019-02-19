package frc.robot.components;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

public class SensorMonitor {
  private static final long TASK_PERIOD_MS = 5; // 200hz -> 5ms period

  private final Timer timer = new Timer("SensorMonitor Timer task", true);
  private final TimerTask task;
  private boolean monitoring = false;

  private final Set<SensorMonitor.SensorPos> linesDetected = Collections.synchronizedSet(new HashSet<>());
  private final Map<SensorMonitor.SensorPos, ColorSensor> sensors;

  public enum SensorPos {
    MIDDLE_LEFT(RobotMap.COLOR_SENSOR_MIDDLE_LEFT_ADDRESS), REAR_LEFT(RobotMap.COLOR_SENSOR_REAR_LEFT_ADDRESS),
    FRONT_LEFT(RobotMap.COLOR_SENSOR_FRONT_LEFT_ADDRESS), MIDDLE_RIGHT(RobotMap.COLOR_SENSOR_MIDDLE_RIGHT_ADDRESS),
    REAR_RIGHT(RobotMap.COLOR_SENSOR_REAR_RIGHT_ADDRESS), FRONT_RIGHT(RobotMap.COLOR_SENSOR_FRONT_RIGHT_ADDRESS);

    private final int address;

    private SensorPos(int address) {
      this.address = address;
    }
  }

  public SensorMonitor() {
    sensors = new HashMap<>();
    for (SensorMonitor.SensorPos s : SensorPos.values()) {
      if (s.address != 0) {
        sensors.put(s, new ColorSensor(s.address));
      }
    }

    task = new TimerTask() {
      @Override
      public void run() {
        // System.out.println("In timer task run; " + Instant.now());
        for (Entry<SensorMonitor.SensorPos, ColorSensor> entry : sensors.entrySet()) {
          synchronized (SensorMonitor.this) {
            if (!linesDetected.contains(entry.getKey())) {
              int whiteValue = entry.getValue().readWhite();
              SmartDashboard.putNumber("ColorSensor_" + entry.getKey(), (double) whiteValue);
              if (ColorSensor.isOnReflectiveLine(whiteValue)) {
                // System.out.println("Line detected for sensor " + entry.getKey());
                SmartDashboard.putBoolean("ColorSensor_" + entry.getKey() + "_lineDetected", true);
                linesDetected.add(entry.getKey());
              }
            }
          }
        }
      }
    };
  }

  public void startMonitoring() {
    timer.scheduleAtFixedRate(task, TASK_PERIOD_MS, TASK_PERIOD_MS);
    monitoring = true;
  }

  public void stopMonitoring() {
    if (monitoring) {
      try {
        timer.cancel();
      } catch (IllegalStateException e) {
        System.out.println("SensorMonitor.stopMonitoring(): timer already cancelled");
      }
    }
    monitoring = false;
  }

  public boolean isLineDetected(SensorMonitor.SensorPos sensor) {
    return linesDetected.contains(sensor);
  }

  public void clearLineDetected(SensorMonitor.SensorPos sensor) {
    linesDetected.remove(sensor);
    SmartDashboard.putBoolean("ColorSensor_" + sensor + "_lineDetected", false);
  }

  public synchronized boolean isLineDetectedAndClear(SensorMonitor.SensorPos sensor) {
    boolean lineDetected = isLineDetected(sensor);
    if (lineDetected) {
      clearLineDetected(sensor);
    }
    return lineDetected;
  }

  public ColorSensor getColorSensor(SensorPos sensor) {
    return sensors.get(sensor);
  }
}
