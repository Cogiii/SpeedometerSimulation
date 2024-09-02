import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.Optional;
import java.util.List;

public class Speedometer extends Application {
    private static final double INITIAL_SPEED = 0;
    private static final double CENTER_X = 250;
    private static final double CENTER_Y = 240;
    private static final double NEEDLE_LENGTH = 150;
    private static final double DECREASE_SPEED_INTERVAL = 0.2;
    private static final double ANIMATION_DURATION = 300;

    private Line needleLine;
    private Timeline decreaseTimeline;
    private ProgressBar gasTank;
    
    private ComboBox<String> brandComboBox;
    private ComboBox<String> modelComboBox;
    private Label carDetailsLabel, instructionsLabel;
    
    private final HashMap<String, HashMap<String, List<Double>>> CarDetails = new HashMap<>() {{
        put("Toyota", new HashMap<>() {{ // [CarAccelerationRate, CarDeaccelerationRate, CarGasConsumption, CarGasCapacity]
            put("Model A", List.of(2.0, 1.0, 0.005, 200.0)); 
            put("Model B", List.of(1.5, 1.5, 0.001, 100.0));
            put("Model C", List.of(1.0, 1.2, 0.0005, 70.0));
        }});
        put("Honda", new HashMap<>() {{ // [CarAccelerationRate, CarDeaccelerationRate, CarGasConsumption, CarGasCapacity]
            put("Model A", List.of(1.8, 1.5, 0.005, 200.0));
            put("Model B", List.of(1.2, 1.6, 0.001, 100.0));
            put("Model C", List.of(1.0, 1.1, 0.0005, 70.0));
        }});
        put("Ford", new HashMap<>() {{ // [CarAccelerationRate, CarDeaccelerationRate, CarGasConsumption, CarGasCapacity]
            put("Model A", List.of(2.2, 1.4, 0.005, 200.0));
            put("Model B", List.of(1.6, 1.3, 0.001, 100.0));
            put("Model C", List.of(2.1, 1.2, 0.0005, 70.0));
        }});
    }};
    
    
    private Car car = new Car(
        "Toyota", 
        "Model A", 
        CarDetails.get("Toyota").get("Model A").get(3), 
        CarDetails.get("Toyota").get("Model A").get(2), 
        INITIAL_SPEED,
        CarDetails.get("Toyota").get("Model A").get(0),
        CarDetails.get("Toyota").get("Model A").get(1)
    );

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();

       // Initialize ComboBoxes
        brandComboBox = new ComboBox<>();
        modelComboBox = new ComboBox<>();
        instructionsLabel = new Label("Press 'W' to Accelerate\nPress 'S' to brake");
        instructionsLabel.setTextFill(Color.WHITE);

        // Populate the ComboBoxes
        brandComboBox.getItems().addAll("Toyota", "Honda", "Ford");
        modelComboBox.getItems().addAll("Model A", "Model B", "Model C");

        // Set default values
        brandComboBox.setValue("Toyota");
        modelComboBox.setValue("Model A");

        // Set the background color of the pane
        pane.setStyle("-fx-background-color: black;");

        // Display car details below the speedometer
        carDetailsLabel = new Label();
        carDetailsLabel.setLayoutX(50);
        carDetailsLabel.setLayoutY(250);
        carDetailsLabel.setTextFill(Color.WHITE);
        pane.getChildren().add(carDetailsLabel);

        // Add listeners to update the car details when a selection changes
        brandComboBox.setOnAction(e -> updateCar());
        modelComboBox.setOnAction(e -> updateCar());

        // Set up the layout
        brandComboBox.setLayoutX(50);
        brandComboBox.setLayoutY(320);
        modelComboBox.setLayoutX(150);
        modelComboBox.setLayoutY(320);
        instructionsLabel.setLayoutX(10);
        instructionsLabel.setLayoutY(10);
        instructionsLabel.setTextFill(Color.WHITE);

        // Display the car details
        updateCar();

        // Add ComboBoxes and Label to the pane
        pane.getChildren().addAll(brandComboBox, modelComboBox, instructionsLabel);

        // Load and display the background image
        ImageView speedometerImage = new ImageView(new Image("/images/speedometer.png"));
        speedometerImage.setFitWidth(400);
        speedometerImage.setFitHeight(200);
        speedometerImage.setX(50);
        speedometerImage.setY(54);
        pane.getChildren().add(speedometerImage);

        // Create the needle (line)
        needleLine = createNeedleLine(INITIAL_SPEED);
        pane.getChildren().add(needleLine);

        // Create the gas tank as a vertical ProgressBar
        gasTank = createGasTankProgressBar();
        pane.getChildren().add(gasTank);

        // Add gas tank icon
        ImageView gasTankIcon = createGasTankIcon();
        pane.getChildren().add(gasTankIcon);

        // Create the refill button
        Button refillButton = createRefillButton();
        pane.getChildren().add(refillButton);

        // Set up the scene and stage
        Scene scene = new Scene(pane, 530, 400);
        primaryStage.setTitle("Speedometer");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Handle key press events when car is moving
        scene.setOnKeyPressed(event -> handleKeyPress(event));

        // Start a continuous decrease in speed
        startDecreasingSpeed();
    }

    private Line createNeedleLine(double initialSpeed) {
        Line line = new Line(
            CENTER_X,
            CENTER_Y,
            CENTER_X + NEEDLE_LENGTH * Math.cos(Math.toRadians(180 - initialSpeed)),
            CENTER_Y - NEEDLE_LENGTH * Math.sin(Math.toRadians(180 - initialSpeed))
        );
        line.setStroke(Color.RED);
        line.setStrokeWidth(3);
        return line;
    }

    private ProgressBar createGasTankProgressBar() {
        ProgressBar progressBar = new ProgressBar(1);
        progressBar.setPrefWidth(180);
        progressBar.setPrefHeight(20);
        progressBar.setLayoutX(380);
        progressBar.setLayoutY(135);
        progressBar.setRotate(270);
        progressBar.setStyle("-fx-accent: red;");
        return progressBar;
    }

    private ImageView createGasTankIcon() {
        ImageView icon = new ImageView(new Image("/images/gasTank.png"));
        icon.setFitWidth(50);
        icon.setFitHeight(50);
        icon.setLayoutX(475);
        icon.setLayoutY(195);
        return icon;
    }

    private Button createRefillButton() {
        Button button = new Button("Refill Gas");
        button.setLayoutX(440);
        button.setLayoutY(320);
        button.setOnAction(e -> showRefillPrompt());
        return button;
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                if (gasTank.getProgress() > 0) {
                    car.increaseSpeed(car.GetAccelerationRate());
                    decreaseGas();
                    animateNeedle(car.getSpeed(), car.getSpeed() + car.GetAccelerationRate());
                }
                break;
            case S:
                if (car.getSpeed() > 0) {
                    car.decreaseSpeed(car.GetDeaccelerationRate());
                    animateNeedle(car.getSpeed(), car.getSpeed() - car.GetDeaccelerationRate());
                }
                break;
        }
    }

    private void startDecreasingSpeed() {
        decreaseTimeline = new Timeline(new KeyFrame(Duration.seconds(DECREASE_SPEED_INTERVAL), e -> {
            if (car.getSpeed() > 0) {
                car.decreaseSpeed(car.GetDeaccelerationRate());
                animateNeedle(car.getSpeed(), car.getSpeed() - car.GetDeaccelerationRate());
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

        KeyFrame keyFrame = new KeyFrame(Duration.millis(ANIMATION_DURATION), keyValueX, keyValueY);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private void decreaseGas() {
        double currentProgress = gasTank.getProgress();
        double newProgress = Math.max(0, currentProgress - car.getGasConsumptionRate());
        gasTank.setProgress(newProgress);
    }

    private void showRefillPrompt() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Refill Gas");
        dialog.setHeaderText("Enter the amount of gas to refill:");
        dialog.setContentText("Amount (0 - "+car.getGasCapacity()+"):");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(amountStr -> {
            try {
                double refillAmount = Double.parseDouble(amountStr) / car.getGasCapacity();
                if (gasTank.getProgress() >= 100) {
                    showErrorPrompt("Full Tank", "Cannot refill because the car full tank.");
                }else if(refillAmount > 1 || refillAmount < 0){
                    showErrorPrompt("Invalid input", "Please enter a number between 0 and "+car.getGasCapacity()+".");
                } else if(car.getSpeed() > 0){
                    showErrorPrompt("Invalid input", "Cannot refill because car is running.");
                } else {
                    refillGasTank(refillAmount);
                }
            } catch (NumberFormatException ex) {
                showErrorPrompt("Invalid input", "Please enter a valid number.");
            }
        });
    }

    /**
     * @param title - header of the error prompt
     * @param message - show message error
     */
    private void showErrorPrompt(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /** Update Cars Detail
     *  Including the Gas Consumptions, Brands, and Model
     */
    private void updateCar() {
        String selectedBrand = brandComboBox.getValue();
        String selectedModel = modelComboBox.getValue();
        car.setGasConsumptionRate(car.getGasConsumptionRate());
        car.setBrand(selectedBrand);
        car.setModel(selectedModel);
        car.SetAccelerationRate(CarDetails.get(selectedBrand).get(selectedModel).get(0));
        car.SetDeaccelerationRate(CarDetails.get(selectedBrand).get(selectedModel).get(1));
        car.setGasConsumptionRate(CarDetails.get(selectedBrand).get(selectedModel).get(2));
        car.setGasCapacity(CarDetails.get(selectedBrand).get(selectedModel).get(3));

        // Update the car details label
        carDetailsLabel.setText("Car Brand\t\t\t: " + car.getBrand() + "\nModel\t\t\t: " + car.getModel() + "\nGas Capacity\t\t: " + car.getGasCapacity()+"L" + "\nGas Consumption\t: " + car.getGasConsumptionRate()*car.getGasCapacity()+"L/km");
    }

    /**
     * @param amount - is the amount of gas to be refilled in a gas tank
     */
    private void refillGasTank(double amount) {
        double currentProgress = gasTank.getProgress();
        double newProgress = Math.min(car.getGasCapacity(), currentProgress + amount);
        gasTank.setProgress(newProgress);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
