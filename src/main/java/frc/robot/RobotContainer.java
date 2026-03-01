// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.commands.vision.AimAtTagCommand;
import frc.robot.constants.ControllerConstants;
import frc.robot.constants.DrivetrainConstants;
import frc.robot.constants.ShooterConstants;
import frc.robot.constants.RobotConstants;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.subsystems.feeder.FeederIOReal;
import frc.robot.subsystems.feeder.FeederSubsystem;
import frc.robot.subsystems.intake.IntakeIOReal;
import frc.robot.subsystems.intake.IntakeIOSim;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.shooter.ShooterIOReal;
import frc.robot.subsystems.shooter.ShooterIOSim;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.vision.VisionIOSim;
import frc.robot.subsystems.vision.VisionSubsystem;
import frc.robot.utils.Logger;
import frc.robot.utils.RobotMode;

import java.io.File;
import org.ironmaple.simulation.SimulatedArena;
import org.photonvision.simulation.VisionSystemSim;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import swervelib.SwerveInputStream;

public class RobotContainer {
    private final CommandPS5Controller driverController = new CommandPS5Controller(ControllerConstants.CONTROLLER_PORT);

    private final Trigger x = driverController.square();
    private final Trigger a = driverController.cross();
    private final Trigger b = driverController.circle();
    private final Trigger y = driverController.triangle();
    private final Trigger rb = driverController.R1();
    //private final Trigger rt = driverController.R2();
    private final Trigger lb = driverController.L1();
    //private final Trigger lt = driverController.L2();
    private final Trigger options = driverController.options();

    private DrivetrainSubsystem drivetrain;
    private IntakeSubsystem intake;
    private ShooterSubsystem shooter;
    private FeederSubsystem feeder;
    private VisionSubsystem visionSubsystem;
    private VisionSystemSim visionSim;

    private SwerveInputStream driveAngularVelocity;
    private double sonAtisZamani = 0;

    public RobotContainer() {
        if(Robot.isReal()){
            realSetup();
        }
        else{
            simSetup();
        }

        setupDriveStreams();
        configureNamedCommands();

        if(RobotConstants.ROBOT_MODE == RobotMode.Test){
            this.testSetup();
        }

        configureBindings();
    }

    private void realSetup() {
        this.drivetrain = new DrivetrainSubsystem(new File(Filesystem.getDeployDirectory(), "swerve/neo"));
        this.intake = new IntakeSubsystem(new IntakeIOReal());
        this.shooter = new ShooterSubsystem(new ShooterIOReal());
        this.feeder = new FeederSubsystem(new FeederIOReal());
    }

    private void simSetup() {
        this.drivetrain = new DrivetrainSubsystem(new File(Filesystem.getDeployDirectory(), "swerve/neo"));
        this.intake = new IntakeSubsystem(new IntakeIOSim(drivetrain.getSwerveDriveSim()));
        this.shooter = new ShooterSubsystem(new ShooterIOSim(drivetrain.getSwerveDriveSim()));
        this.feeder = new FeederSubsystem(new FeederIOReal());
        this.visionSim = new VisionSystemSim("GlobalVisionSim");
        VisionIOSim cameraSim = new VisionIOSim(visionSim, "MainCamera");
        this.visionSubsystem = new VisionSubsystem(cameraSim);

        SimulatedArena.getInstance().resetFieldForAuto();
    
    }

    private void setupDriveStreams() {
        this.driveAngularVelocity = SwerveInputStream.of(drivetrain.getSwerveDrive(),
                () -> driverController.getLeftY() * -1,
                () -> driverController.getLeftX() * -1)
                .withControllerRotationAxis(() -> driverController.getRightX() * -1)
                .deadband(ControllerConstants.DEADBAND)
                .scaleTranslation(0.8)
                .allianceRelativeControl(false);
    }
    // Teleop Komutların Tanımlandığı Yer
    private void configureBindings() {
        // Varsayılan Sürüş Komutu
        drivetrain.setDefaultCommand(drivetrain.driveFieldOriented(driveAngularVelocity));

        // Gyro Sıfırlama (options)
        options.onTrue(Commands.runOnce(drivetrain::zeroGyro));

        // Aim At Tag (L1)
        lb.toggleOnTrue(new AimAtTagCommand(
                drivetrain,
                visionSubsystem,
                () -> -driverController.getRightX() * RobotConstants.MAX_SPEED,
                () -> -driverController.getLeftY() * RobotConstants.MAX_SPEED,
                () -> -driverController.getLeftX() * RobotConstants.MAX_SPEED
        ));

        // Intake Fuel (Cross)
        a.whileTrue(new RunCommand(() -> intake.setRunning(true), intake))
         .onFalse(new RunCommand(() -> intake.setRunning(false), intake));

        // Open Intake (Triangle)
        y.whileTrue(new RunCommand(() -> intake.OpenIntake(true), intake))
         .onFalse(new RunCommand(() -> intake.OpenIntake(false), intake));


        // Close Intake (circle)
        b.whileTrue(new RunCommand(() -> intake.CloseIntake(true), intake))
         .onFalse(new RunCommand(() -> intake.CloseIntake(false), intake));

        // Shooter (Square)
        x.whileTrue(new RunCommand(() -> {
            double suan = Timer.getFPGATimestamp();
            if (suan - sonAtisZamani >= ShooterConstants.FUEL_SHOOTER_TIME) {
                if (intake.useFuel()) {
                    shooter.launchFuel(ShooterConstants.FUEL_SHOOTER_SPEED, ShooterConstants.FUEL_SHOOTER_ANGLE);
                    sonAtisZamani = suan;
                }
            }
        }, intake, shooter));

        // Feeder (r1)
        rb.whileTrue(new RunCommand(() -> feeder.setRunning(true), intake))
         .onFalse(new RunCommand(() -> feeder.setRunning(false), intake));
        
    }

    public void periodic() {
        // Simülasyon Güncellemeleri
        if (Robot.isSimulation() && visionSim != null && drivetrain.getSwerveDriveSim() != null) {
            Pose2d realPhysicsPose = drivetrain.getSwerveDriveSim().getSimulatedDriveTrainPose();
            visionSim.update(realPhysicsPose);
            SimulatedArena.getInstance().simulationPeriodic();
            
            Pose3d[] fuelPoses = SimulatedArena.getInstance().getGamePiecesArrayByType("Fuel");
            Logger.log("FieldSimulation/Fuels", fuelPoses);
        }
        if(RobotConstants.ROBOT_MODE == RobotMode.Test){
            drivetrain.setPIDIZ(
                SmartDashboard.getNumber("DriveP", 0), 
                SmartDashboard.getNumber("DriveI", 0),
                SmartDashboard.getNumber("DriveD", 0), 
                SmartDashboard.getNumber("DriveIZ", 0)
            );
            drivetrain.setSGVA(
                SmartDashboard.getNumber("DriveS", 0), 
                SmartDashboard.getNumber("DriveV", 0), 
                SmartDashboard.getNumber("DriveA", 0)
            );
        }
    }

    // Pathplanner Komut tanımlama
    public void configureNamedCommands() {
        NamedCommands.registerCommand("Intake", Commands.startEnd(
            () -> intake.setRunning(true), () -> intake.setRunning(false), intake));

        NamedCommands.registerCommand("Shoot", Commands.run(() -> {
            double suan = Timer.getFPGATimestamp();
            if (suan - sonAtisZamani >= ShooterConstants.FUEL_SHOOTER_TIME) {
                shooter.launchFuel(ShooterConstants.FUEL_SHOOTER_SPEED, ShooterConstants.FUEL_SHOOTER_ANGLE);
                sonAtisZamani = suan;
            }
        }, intake, shooter));
    }
    
    // Pathplanner Otonom Görev Tanımlama
    public Command getAutonomousCommand() {
        return AutoBuilder.buildAuto("auto");
    }

    public void testSetup(){
        SmartDashboard.putNumber("ArmP", DrivetrainConstants.P);
        SmartDashboard.putNumber("ArmI", DrivetrainConstants.I);
        SmartDashboard.putNumber("ArmD", DrivetrainConstants.D);
        SmartDashboard.putNumber("ArmIZ", DrivetrainConstants.IZ);

        SmartDashboard.putNumber("ArmS", DrivetrainConstants.S);
        SmartDashboard.putNumber("ArmG", DrivetrainConstants.G);
        SmartDashboard.putNumber("ArmV", DrivetrainConstants.V);
        SmartDashboard.putNumber("ArmA", DrivetrainConstants.A);
    
    }

    public void setMotorBrake(boolean brake) {
        drivetrain.setMotorBrake(brake);
    }
}