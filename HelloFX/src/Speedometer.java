import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Speedometer extends Application {
    private Speed speed;
    private Needle needle;
    private Line needleLine;
    private Timeline decreaseTimeline;

    private static final double CENTER_X = 200; // Fixed center X position for needle rotation
    private static final double CENTER_Y = 240; // Fixed center Y position for needle rotation
    private static final double NEEDLE_LENGTH = 150; // Length of the needle

    public Speedometer() {
        this.speed = new Speed(0);
        this.needle = new Needle();
    }

    public void updateSpeed(double newSpeed) {
        speed.setSpeed(newSpeed);
        needle.updateAngle(speed);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();

        // Load and display the background image (speedometer.png)
        ImageView speedometerImage = new ImageView(new Image("/images/speedometer.png"));
        speedometerImage.setFitWidth(400);  // Adjust the size to match your scene
        speedometerImage.setFitHeight(200);
        speedometerImage.setX(0);
        speedometerImage.setY(50);
        pane.getChildren().add(speedometerImage);

        // Create the needle (line) with fixed start position
        needleLine = new Line(CENTER_X, CENTER_Y, CENTER_X, CENTER_Y - NEEDLE_LENGTH); // Initial position
        needleLine.setStroke(Color.RED);
        needleLine.setStrokeWidth(3);

        pane.getChildren().add(needleLine);

        // Set up the scene and stage
        Scene scene = new Scene(pane, 400, 400);
        primaryStage.setTitle("Speedometer");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Handle key press events
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case W:
                    // Increase speed and rotate needle to the right
                    animateNeedle(speed.getSpeed(), speed.getSpeed() + 1);
                    speed.increaseSpeed(1);
                    break;
                case S:
                    // Decrease speed and rotate needle to the right
                    animateNeedle(speed.getSpeed(), speed.getSpeed() - 1);
                    speed.decreaseSpeed(1);
                    break;
            }
            // Pause the decreasing timeline
            decreaseTimeline.pause();
        });

        // Handle key release events to resume decreasing speed
        scene.setOnKeyReleased((KeyEvent event) -> {
            if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.S) {
                // Resume the decreasing timeline after releasing 'W'
                decreaseTimeline.playFromStart();
            }
        });

        // Start a continuous decrease in speed
        startDecreasingSpeed();
    }

    private void startDecreasingSpeed() {
        decreaseTimeline = new Timeline(new KeyFrame(Duration.seconds(0.2), e -> {
            if (speed.getSpeed() > 0) {
                animateNeedle(speed.getSpeed(), speed.getSpeed() - 2);
                speed.decreaseSpeed(1);
            }
        }));
        decreaseTimeline.setCycleCount(Timeline.INDEFINITE);
        decreaseTimeline.play();
    }

    private void animateNeedle(double oldSpeed, double newSpeed) {
        double newAngle = newSpeed;

        Timeline timeline = new Timeline();
        KeyValue keyValueX = new KeyValue(needleLine.endXProperty(),
                CENTER_X + NEEDLE_LENGTH * Math.cos(Math.toRadians(180 - newAngle)));
        KeyValue keyValueY = new KeyValue(needleLine.endYProperty(),
                CENTER_Y - NEEDLE_LENGTH * Math.sin(Math.toRadians(180 - newAngle)));

        KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValueX, keyValueY);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}