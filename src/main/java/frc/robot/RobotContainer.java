package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import com.pathplanner.lib.auto.CommandUtil;
// import com.pathplanner.lib.commands.;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import java.io.File;

import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

import swervelib.SwerveInputStream;
import swervelib.parser.json.modules.DriveConversionFactorsJson;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.ClimberUpCommand;
import frc.robot.commands.ClimberDownCommand;
import frc.robot.subsystems.Claw.Claw;
import frc.robot.subsystems.Climber.ClimbSubsystem;
import frc.robot.subsystems.Elevator.Elevator;
import frc.robot.subsystems.Elevator.ElevatorFFCommand;
import frc.robot.subsystems.Elevator.ElevatorPIDCommand;
import frc.robot.subsystems.Swerve.SwerveSubsystem;
import frc.robot.util.Constants.ClawConstants;
import frc.robot.util.Constants.ElevatorConstants;
import frc.robot.util.Constants.miscConstants;
import frc.robot.util.Constants.ClawConstants.Wrist.ClawRollerVolt;
import frc.robot.util.Constants.ClawConstants.Wrist.WristPositions;

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
  public final ClimbSubsystem 
  m_climber = new ClimbSubsystem();

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

  private final Claw sub_claw;
  private final Elevator elevator;
  // Dashboard inputs
  // private final LoggedDashboardChooser<Command> autoChooser;

  private boolean RollerGoingIn = false;

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
    sub_claw = new Claw();
    elevator = new Elevator(sub_claw);

    // Set up auto routines
     //autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());
    // autoChooser.addOption("Blue Middle Start", getAutonomousCommand());

    NamedCommands.registerCommand("Elevate Algae #1", Commands.run(() -> {
      elevator.goToSetpoint(10); //CHANGE ME - I AM WRONG
    }));

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
  private void configureBindings()
  {
        

 Command driveFieldOrientedAnglularVelocity = drivebase.driveFieldOriented(driveAngularVelocity);
 Command c_driveRobotOriented = drivebase.drive(driveRobotOriented);

 Command driveFieldOrientedDirectAngleKeyboard  = drivebase.driveFieldOriented(driveDirectAngleKeyboard);



   if (RobotBase.isSimulation())
    {
      drivebase.setDefaultCommand(driveFieldOrientedDirectAngleKeyboard);
    } else
    {
      drivebase.setDefaultCommand(driveFieldOrientedAnglularVelocity);
    }

    if (Robot.isSimulation())
    {
      DriveController.start().onTrue(Commands.runOnce(() -> drivebase.resetOdometry(new Pose2d(3, 3, new Rotation2d()))));
      DriveController.button(1).whileTrue(drivebase.sysIdDriveMotorCommand());

    }


  
      
      //~~~~~~~~~~~~~~~~~~OPControler~~~~~~~~~~~~~~~~~~~~~~~~
      //Elevator Pos Control 
      OPController.start().whileTrue(new ClimberUpCommand(m_climber));
      OPController.back().whileTrue(new ClimberDownCommand(m_climber));

      OPController.axisGreaterThan(2, 0.01).whileTrue(Commands.run(() -> {
        sub_claw.setRollerPower(((OPController.getRawAxis(2) * 0.65) * 12) * -1);
      }));
      OPController.axisGreaterThan(3, 0.01).whileTrue(Commands.run(() -> {
        sub_claw.setRollerPower((OPController.getRawAxis(3) * 0.65) * 12);
      }));

      OPController.axisLessThan(2, 0.01).and(OPController.axisLessThan(2,0.01)).whileTrue(Commands.run(() -> {
       sub_claw.setRollerPower(0);
      }).repeatedly());


 

      OPController.povUp().whileTrue(Commands.run(() -> {
        elevator.goToSetpoint(ElevatorConstants.ElevatorConfigs.Positions.SCORE.getPos());
      }));
      OPController.povRight().whileTrue(Commands.run(() -> {
        elevator.goToSetpoint(ElevatorConstants.ElevatorConfigs.Positions.BARGE.getPos());
      }));
      OPController.povDown().whileTrue(Commands.run(() -> {
        elevator.goToSetpoint(ElevatorConstants.ElevatorConfigs.Positions.L3.getPos());
      }));
      OPController.povLeft().whileTrue(Commands.run(() -> {
        elevator.goToSetpoint(ElevatorConstants.ElevatorConfigs.Positions.L4.getPos());
      }));

      //Wrist Pos Control 
      OPController.y().whileTrue(Commands.run(() -> {
        sub_claw.goToSetpoint(ClawConstants.Wrist.WristPositions.Home.getPos());
        elevator.goToSetpoint(ElevatorConstants.ElevatorConfigs.Positions.INTAKE.getPos());
      }));
      OPController.x().whileTrue(Commands.run(() -> {

        sub_claw.goToSetpoint(ClawConstants.Wrist.WristPositions.L1_L2_Coral.getPos());
        
      }));
      OPController.b().whileTrue(Commands.run(() -> {
        sub_claw.goToSetpoint(ClawConstants.Wrist.WristPositions.Floor.getPos());
        elevator.goToSetpoint(ElevatorConstants.ElevatorConfigs.Positions.FloorIntake.getPos());
      }));
      OPController.a().whileTrue(Commands.run(() -> {
        sub_claw.goToSetpoint(ClawConstants.Wrist.WristPositions.Elevator_Threh.getPos());
      }));

      OPController.leftBumper().whileTrue(Commands.run(() -> {
        sub_claw.goToSetpoint(ClawConstants.Wrist.WristPositions.Algae_Drive.getPos());
      }));

      OPController.rightBumper().whileTrue(Commands.run(() -> {
        sub_claw.setRollerPower(-9);      }));

      //~~~~~~~~~~~~~~~~~~Drive Control~~~~~~~~~~~~~~~~~~~~~~~~
      DriveController.a().onTrue((Commands.runOnce(drivebase::zeroGyro)));
      // DriveController.leftBumper().whileTrue(drivebase.(c_driveRobotOriented))
      // .whileFalse(Commands.runOnce(() -> {
      //   drivebase.setDefaultCommand(driveFieldOrientedAnglularVelocity);
      // }));

      // DriveController.x().onTrue(Commands.runOnce(drivebase::addFakeVisionReading));

      //Go to pos ?
      // DriveController.y().whileTrue(
      // drivebase.driveToPose(new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0))));


      //Lock the drive
      // DriveController.leftBumper().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());


      //This is our boost control Right Trigger
      DriveController.axisGreaterThan(3, 0.01).onChange(Commands.runOnce(() -> {
        driveAngularVelocity.scaleTranslation(DriveController.getRightTriggerAxis() + 0.25);
        driveAngularVelocity.scaleRotation((DriveController.getRightTriggerAxis() * miscConstants.RotationSpeedScale) + 0.25);
      }).repeatedly()).whileFalse(Commands.runOnce(() -> { 
        driveAngularVelocity.scaleTranslation(0.25);
        driveAngularVelocity.scaleRotation(0.15);
      }).repeatedly());


      // DriveController
      // .rightTrigger(miscConstants.DEADBAND)
      // .whileTrue(Commands.runOnce(() -> {driveAngularVelocity.scaleTranslation(SwerveConstants.kMaxSpeedScalar);
      // }))
      // .whileFalse(Commands.runOnce(() -> {driveAngularVelocity.scaleTranslation(SwerveConstants.kUnboostScalar);
      //  }));
       }

    
  

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    // An example command will be run in autonomous
    return m_chooser.getSelected();
  }
  public void setMotorBrake(boolean brake)
  {
    drivebase.setMotorBrake(brake);
  }
}
