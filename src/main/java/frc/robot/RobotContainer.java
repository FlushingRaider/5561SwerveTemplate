package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import java.io.File;


import swervelib.SwerveInputStream;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.miscConstants;
import frc.robot.subsystems.Swerve.SwerveSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{

  final         CommandXboxController DriveController = new CommandXboxController(0);
  final         CommandXboxController OPController = new CommandXboxController(1);
  // The robot's subsystems and commands are defined here...
  private final SwerveSubsystem       drivebase  = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
                                                                                "swerve"));

  //======================Auton_Stuff=========================


  private final Command Red_Right_Start; 
  private final Command Red_Middle_Start; 
  private final Command Red_Left_Start; 

  private final Command Blue_Left_Start; 
  private final Command Blue_Right_Start; 
  private final Command Blue_Middle_Start; 

  private final Command Blue_Right_Coral;
  private final Command Blue_Middle_Coral;
  private final Command Blue_Left_Coral;

  private final Command Red_Right_Coral;
  private final Command Red_Middle_Coral;
  private final Command Red_Left_Coral;

  SendableChooser<Command> m_chooser;

  //=======================================================

  // Dashboard inputs
  // private final LoggedDashboardChooser<Command> autoChooser;

  SwerveInputStream driveAngularVelocity = SwerveInputStream.of(drivebase.getSwerveDrive(),
                                                             () -> DriveController.getLeftY() * -1,
                                                             () -> DriveController.getLeftX() * -1)
                                                            .withControllerRotationAxis(DriveController::getRightX)
                                                            .deadband(miscConstants.DEADBAND)
                                                            .scaleTranslation(0.25)
                                                            .scaleRotation(0.15)
                                                            .allianceRelativeControl(true);
            
 

  /**
   * Clone's the angular velocity input stream and converts it to a fieldRelative input stream.
   */
  SwerveInputStream driveDirectAngle = driveAngularVelocity.copy().withControllerHeadingAxis(DriveController::getLeftX,
                                                                                             DriveController::getLeftY)
                                                           .headingWhile(true);
                                                           

  /**
   * Clone's the angular velocity input stream and converts it to a robotRelative input stream.
   */
  SwerveInputStream driveRobotOriented = driveAngularVelocity.copy().robotRelative(true)
                                                             .allianceRelativeControl(false);

  SwerveInputStream driveAngularVelocityKeyboard = SwerveInputStream.of(drivebase.getSwerveDrive(),
                                                                      () -> -DriveController.getLeftY(),
                                                                      () -> -DriveController.getLeftX())
                                                                    .withControllerRotationAxis(() -> DriveController.getRawAxis(
                                                                        // () -> -driverJoystick.getRawAxis(1),
                                                                        // () -> -driverJoystick.getRawAxis(0))
                                                                    // .withControllerRotationAxis(() -> driverJoystick.getRawAxis(
                                                                        2))
                                                                    .deadband(miscConstants.DEADBAND)
                                                                    .scaleTranslation(0.8)
                                                                    .allianceRelativeControl(true);
  // Derive the heading axis with math!
  SwerveInputStream driveDirectAngleKeyboard     = driveAngularVelocityKeyboard.copy()
                                                                               .withControllerHeadingAxis(() ->
                                                                                                              Math.sin(
                                                                                                                DriveController.getRawAxis(
                                                                                                                  // driverJoystick.getRawAxis(
                                                                                                                      2) *
                                                                                                                  Math.PI) *
                                                                                                              (Math.PI *
                                                                                                               2),
                                                                                                          () ->
                                                                                                              Math.cos(
                                                                                                                DriveController.getRawAxis(
                                                                                                                  // driverJoystick.getRawAxis(
                                                                                                                      2) *
                                                                                                                  Math.PI) *
                                                                                                              (Math.PI *
                                                                                                               2))
                                                                               .headingWhile(true);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer()
  {

    // Set up auto routines
    //autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());
    // autoChooser.addOption("Blue Middle Start", getAutonomousCommand());

    configureBindings();
    DriverStation.silenceJoystickConnectionWarning(false);
    
    //========================Auton_Stuff===================================================

    Red_Right_Start = drivebase.getAutonomousCommand("Red Right Start");
    Red_Middle_Start = drivebase.getAutonomousCommand("Red Middle Start");
    Red_Left_Start = drivebase.getAutonomousCommand("Red Left Start");

    Blue_Right_Start = drivebase.getAutonomousCommand("Blue Right Start");
    Blue_Middle_Start = drivebase.getAutonomousCommand("Blue Middle Start");
    Blue_Left_Start = drivebase.getAutonomousCommand("Blue Left Start");

    Red_Right_Coral = drivebase.getAutonomousCommand("Red Right Coral");
    Red_Middle_Coral = drivebase.getAutonomousCommand("Red Middle Coral");
    Red_Left_Coral = drivebase.getAutonomousCommand("Red Left Coral");

    Blue_Right_Coral = drivebase.getAutonomousCommand("Blue Right Coral");
    Blue_Middle_Coral = drivebase.getAutonomousCommand("Blue Middle Coral");
    Blue_Left_Coral = drivebase.getAutonomousCommand("Blue Left Coral");

    m_chooser = new SendableChooser<Command>();

    m_chooser.addOption("Red Middle Start", Red_Middle_Start);
    m_chooser.addOption("Red Left Start", Red_Left_Start);
    m_chooser.addOption("Red Right Start", Red_Right_Start);

    m_chooser.addOption("Blue Middle Start", Blue_Middle_Start);
    m_chooser.addOption("Blue Left Start", Blue_Left_Start);
    m_chooser.addOption("Blue Right Start", Blue_Right_Start);

    m_chooser.addOption("Blue Middle Start", Blue_Middle_Coral);
    m_chooser.addOption("Blue Left Start", Blue_Left_Coral);
    m_chooser.addOption("Blue Right Start", Blue_Right_Coral);

    m_chooser.addOption("Red Middle Start", Red_Middle_Coral);
    m_chooser.addOption("Red Left Start", Red_Left_Coral);
    m_chooser.addOption("Red Right Start", Red_Right_Coral);

    SmartDashboard.putData(m_chooser);

    //======================================================================================

    // SmartDashboard.putData(autoChooser);

  }

  private void configureBindings() {
    Command driveFieldOrientedAnglularVelocity = drivebase.driveFieldOriented(driveAngularVelocity);
    Command c_driveRobotOriented = drivebase.drive(driveRobotOriented);

    Command driveFieldOrientedDirectAngleKeyboard  = drivebase.driveFieldOriented(driveDirectAngleKeyboard);



    if (RobotBase.isSimulation()) {
      drivebase.setDefaultCommand(driveFieldOrientedDirectAngleKeyboard);
    } else {
      drivebase.setDefaultCommand(driveFieldOrientedAnglularVelocity);
    }

    if (Robot.isSimulation()) {
      DriveController.start().onTrue(Commands.runOnce(() -> drivebase.resetOdometry(new Pose2d(3, 3, new Rotation2d()))));
      DriveController.button(1).whileTrue(drivebase.sysIdDriveMotorCommand());
    }

    //~~~~~~~~~~~~~~~~~~OPControler~~~~~~~~~~~~~~~~~~~~~~~~


    //~~~~~~~~~~~~~~~~~~Drive Control~~~~~~~~~~~~~~~~~~~~~~
    DriveController.a().onTrue((Commands.runOnce(drivebase::zeroGyro)));

    //This is our boost control Right Trigger
    DriveController.axisGreaterThan(3, 0.01).onChange(Commands.runOnce(() -> {
      driveAngularVelocity.scaleTranslation(DriveController.getRightTriggerAxis() + 0.25);
      driveAngularVelocity.scaleRotation((DriveController.getRightTriggerAxis() * miscConstants.RotationSpeedScale) + 0.25);
    }).repeatedly()).whileFalse(Commands.runOnce(() -> { 
      driveAngularVelocity.scaleTranslation(0.25);
      driveAngularVelocity.scaleRotation(0.15);
    }).repeatedly());

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return m_chooser.getSelected();
  }

  public void setMotorBrake(boolean brake) {
    drivebase.setMotorBrake(brake);
  }
}
