/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import edu.wpi.first.wpilibj.I2C;
/**
 * Add your docs here.
 */
public class ColorSensor extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  
protected final static int COMMAND_REGISTER_BIT = 0x80;
protected final static int CMD = 0x80;
protected final static int MULTI_BYTE_BIT = 0x20;

protected final static int ENABLE_REGISTER   = 0x00;
protected final static int ATIME_REGISTER    = 0x01;
protected final static int PPULSE_REGISTER   = 0x0E;

protected final static int ID_REGISTER       = 0x12;
protected final static int CDATA_REGISTER    = 0x14;
protected final static int RDATA_REGISTER    = 0x16;
protected final static int GDATA_REGISTER    = 0x18;
protected final static int BDATA_REGISTER    = 0x1A;
protected final static int PDATA_REGISTER    = 0x1C;

protected final static int PON   = 0b00000001;
protected final static int AEN   = 0b00000010;
protected final static int PEN   = 0b00000100;
protected final static int WEN   = 0b00001000;
protected final static int AIEN  = 0b00010000;
protected final static int PIEN  = 0b00100000;

private final double integrationTime = 10;

private I2C sensor;

private ByteBuffer buffy = ByteBuffer.allocate(8);

public short red = 0, green = 0, blue = 0, prox = 0;

public ColorSensor(I2C.Port port) {
	buffy.order(ByteOrder.LITTLE_ENDIAN);
    sensor = new I2C(port, 0x39); //0x39 is the address of the Vex ColorSensor V2
    
    sensor.write(CMD | 0x00, PON | AEN | PEN);
    
    sensor.write(CMD | 0x01, (int) (256-integrationTime/2.38)); //configures the integration time (time for updating color data)
    sensor.write(CMD | 0x0E, 0b1111);
    
}
protected int readWordRegister(int address) {
  ByteBuffer buf = ByteBuffer.allocate(2);
  sensor.read(COMMAND_REGISTER_BIT | MULTI_BYTE_BIT | address, 2, buf);
  buf.order(ByteOrder.LITTLE_ENDIAN);
  return buf.getShort(0);
}

public int red() {
 return readWordRegister(RDATA_REGISTER);
}

public int green() {
 return readWordRegister(GDATA_REGISTER);
}

public int blue() {
 return readWordRegister(BDATA_REGISTER);
}

public int clear() {
 return readWordRegister(CDATA_REGISTER);
}

public int proximity() {
 return readWordRegister(PDATA_REGISTER);
}


public void read() {
	buffy.clear();
    sensor.read(CMD | MULTI_BYTE_BIT | RDATA_REGISTER, 8, buffy);
    
    red = buffy.getShort(0);
    if(red < 0) { red += 0b10000000000000000; }
    
    green = buffy.getShort(2);
    if(green < 0) { green += 0b10000000000000000; }
    
    blue = buffy.getShort(4); 
    if(blue < 0) { blue += 0b10000000000000000; }
    
    prox = buffy.getShort(6); 
    if(prox < 0) { prox += 0b10000000000000000; }
    
}


public int status() {
	buffy.clear();
	sensor.read(CMD | 0x13, 1, buffy);
	return buffy.get(0);
}


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
