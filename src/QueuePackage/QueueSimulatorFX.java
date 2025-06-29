/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package QueuePackage;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class QueueSimulatorFX extends Application {

    private int[] queue;
    private int front = 0; 
    private int rear = -1; 
    private int capacity = 4; 
    private HBox queueDisplay = new HBox(10);

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        queue = new int[capacity];
        primaryStage.setTitle("Queue Operation Simulator");

        TextField inputField = new TextField();
        inputField.setPromptText("Enter value");

        Button enqueueButton = new Button("Enqueue");
        Button dequeueButton = new Button("Dequeue");
        Button displayButton = new Button("Display Queue");

        enqueueButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        dequeueButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px;");
        displayButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px;");

        enqueueButton.setOnAction(e -> {
            try {
                int value = Integer.parseInt(inputField.getText());
                enqueue(value);
                inputField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Input Error", "Please enter a valid number.", Alert.AlertType.WARNING);
            }
        });

        dequeueButton.setOnAction(e -> {
            if (!isEmpty()) {
                dequeue();
            } else {
                showAlert("Queue Error", "The queue is empty!", Alert.AlertType.WARNING);
            }
        });

        displayButton.setOnAction(e -> displayQueue());

        VBox mainLayout = new VBox(20, inputField, enqueueButton, dequeueButton, displayButton, queueDisplay);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: #f0f8ff;");

        Scene scene = new Scene(mainLayout, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void enqueue(int value) {
        if (rear == capacity - 1) {
            showAlert("Queue Error", "Queue Overflow! Cannot enqueue.", Alert.AlertType.ERROR);
            return;
        }
        rear++;                           // Move rear forward
        queue[rear] = value;             // Add value to the queue
        updateQueueDisplay();
        animateEnqueue();
    }

    public void dequeue() {
        if (isEmpty()) {
            showAlert("Queue Error", "Queue Underflow! Cannot dequeue.", Alert.AlertType.ERROR);
            return;
        }
        front++;                    // Move front forward
        updateQueueDisplay();
    }

    private boolean isEmpty() {
        return front > rear;          // Queue is empty 
    }

    private void displayQueue() {
        if (isEmpty()) {
            showAlert("Queue Status", "The queue is currently empty.", Alert.AlertType.WARNING);
            return;
        }
        StringBuilder queueContents = new StringBuilder("Queue: ");
        for (int i = front; i <= rear; i++) {
            queueContents.append(queue[i]).append(" ");
        }
        showAlert("Queue Contents", queueContents.toString(), Alert.AlertType.INFORMATION);
    }

    private void updateQueueDisplay() {
        queueDisplay.getChildren().clear();
        
        for (int i = front; i <= rear; i++) {
            StackPane node = createQueueNode(queue[i]);
            queueDisplay.getChildren().add(node);
        }
    }

    private StackPane createQueueNode(int value) {
        Rectangle rectangle = new Rectangle(100, 50);
        rectangle.setFill(Color.LIGHTSKYBLUE);
        rectangle.setStroke(Color.BLACK);

        Text text = new Text(String.valueOf(value));
        text.setFont(Font.font(18));
        text.setFill(Color.CHOCOLATE);

        StackPane stackPane = new StackPane(rectangle, text);
        stackPane.setAlignment(Pos.CENTER);
        return stackPane;
    }

    private void animateEnqueue() {
        if (!queueDisplay.getChildren().isEmpty()) {
            StackPane lastNode = (StackPane) queueDisplay.getChildren().get(queueDisplay.getChildren().size() - 1);

            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), lastNode);
            transition.setFromY(-100);
            transition.setToY(0);
            transition.play();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
        
    }
}