/**
 * <h1>PetrusFX</h1>
 * Simple weather app that displays various meterological parameters for a city.
 * Weather data is dynamically pulled from the OpenWeatherMap-API
 * (https://openweathermap.org/api). The app implements the
 * Model-View-Controler(/Presenter) scheme.
 *
 * @author Ilyas Keskin
 * @version 0.1
 * @since 2019-01-23
 */
package de.ilkeskin.petrusfx.main;

import de.ilkeskin.petrusfx.data.DataModel;
import de.ilkeskin.petrusfx.menu.MenuController;
import de.ilkeskin.petrusfx.widget.WidgetController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PetrusApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();

        Font.loadFont(getClass().getResource("/fonts/weathericons-regular-webfont.ttf").toExternalForm(), 24);

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
        root.setTop(menuLoader.load());
        MenuController menuController = menuLoader.getController();

        FXMLLoader widgetLoader = new FXMLLoader(getClass().getResource("/fxml/widget.fxml"));
        root.setCenter(widgetLoader.load());
        WidgetController widgetController = widgetLoader.getController();

        DataModel model = new DataModel();
        menuController.initModel(model);
        widgetController.initModel(model);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("PetrusFX");
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
