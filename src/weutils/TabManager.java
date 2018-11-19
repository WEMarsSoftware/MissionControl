/*

    EXPERIMENTAL: Tab Manager that stubs tab panes found within JavaFX, that
    introduces dockable tabs to the TabPane system.
    Possibly super buggy and needs more testing but works outside of the project


    Usage:

    @FXML
    private TabPane weTabPane;              // Reference new TabPane from FXML

    @FXML
    public void initialize() {
        TabManager.create().setTabsDockable(weTabPane);  // Convert to WEMars TabManager Utility
    }


*/

package weutils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;


public class TabManager {

    private TabPane tPane;                      // Tab Pane
    private Tab currentActiveTab;
    private final List<Tab> defaultTabs;        // Default Tabs on startup
    private final Map<Integer, Tab> map;
    private String[] stylesheets;
    private final BooleanProperty forceTop;     // Property holding default


    /**
     * Gets pane.
     *
     * @return the pane
     */
    public TabPane gettPane() {
        return tPane;
    }

    /**
     * Gets current tab.
     *
     * @return the current tab
     */
    public Tab getCurrentActiveTab() {
        return currentActiveTab;
    }

    /**
     * Gets default tabs.
     *
     * @return the default tabs
     */
    public List<Tab> getDefaultTabs() {
        return defaultTabs;
    }

    /**
     * Gets map.
     *
     * @return the map
     */
    public Map<Integer, Tab> getMap() {
        return map;
    }

    /**
     * Get stylesheets string [ ].
     *
     * @return the string [ ]
     */
    public String[] getStylesheets() {
        return stylesheets;
    }



    private TabManager() {
        defaultTabs = new ArrayList<>();
        stylesheets = new String[]{};
        map = new HashMap<>();
        forceTop = new SimpleBooleanProperty();
    }

    /**
     * Creates a new instance of the TabManager
     *
     * @return The new instance of the TabManager.
     */
    public static TabManager create() {
        return new TabManager();
    }

    /**
     * Force top property boolean property.
     *
     * @return the boolean property
     */
    public BooleanProperty forceTopProperty() {
        return forceTop;
    }

    /**
     * Returns boolean value of forceTop
     *
     * @return The current TabManager instance.
     */
    public Boolean getForceTop() {
        return forceTop.get();
    }

    /**
     * Sets whether undocked Tabs should be always on top.
     *
     * @param alwaysOnTop The state to be set.
     * @return The current TabManager instance.
     */
    public TabManager alwaysOnTop(boolean alwaysOnTop){
        forceTopProperty().set(alwaysOnTop);
        return this;
    }

    /**
     * Sets the stylesheets that should be assigend to the new created {@link Stage}.
     *
     * @param stylesheets The stylesheets to be set.
     * @return The current TabManager instance.
     */
    public TabManager stylesheets(String... stylesheets) {
        this.stylesheets = stylesheets;
        return this;
    }

    /**
     * Makes all the tabs dockable
     *
     * @param tab The {@link TabPane} to take over.
     * @return The current TabManager instance.
     */
    public TabManager setAllDockable(TabPane tab) {

        // Reference the current tab pane
        this.tPane = tab;
        System.out.println(tab.getTabs());
        // Store the original tabs, use a map for speed!
        defaultTabs.addAll(tab.getTabs());
        for (int i = 0; i < tab.getTabs().size(); i++) {
            map.put(i, tab.getTabs().get(i));
        }

        // Get all the tabs and disable their closability
        tab.getTabs().stream().forEach(t -> {
            t.setClosable(false);
        });

        // STUB: when drag is deteched, save a snapshot of the data
        tab.setOnDragDetected(
                (MouseEvent e) -> {
                    if (e.getSource() instanceof TabPane) {
                        Pane rootPane = (Pane) tab.getScene().getRoot();
                        rootPane.setOnDragOver((DragEvent e1) -> {
                            e1.acceptTransferModes(TransferMode.ANY);
                            e1.consume();
                        });
                        currentActiveTab = tab.getSelectionModel().getSelectedItem();
                        SnapshotParameters snapshotParams = new SnapshotParameters();
                        snapshotParams.setTransform(Transform.scale(0.4, 0.4));
                        WritableImage snapshot = currentActiveTab.getContent().snapshot(snapshotParams, null);
                        Dragboard db = tab.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent clipboardContent = new ClipboardContent();
                        clipboardContent.put(DataFormat.PLAIN_TEXT, "");
                        db.setDragView(snapshot, 40, 40);
                        db.setContent(clipboardContent);
                    }
                    e.consume();
                }
        );

        // On Finish drag open it in a new window
        tab.setOnDragDone(
                (DragEvent e) -> {
                    openTabInStage(currentActiveTab);
                    tab.setCursor(Cursor.DEFAULT);
                    e.consume();
                }
        );
        return this;
    }

    /**
     * Creates a new stage with the contents of the removed tab pane. Content is removed from the tab, and added
     * to the root of the new stage.
     * Sets new Window title to the name of tab.
     *
     * @param tab The {@link Tab} to get the content from.
     */
    public void openTabInStage(final Tab tab) {

        // error guard
        if(tab == null) return;

        // Get the original tabe index and remove it from the map
        int originalTab = defaultTabs.indexOf(tab);
        map.remove(originalTab);


        Pane content = (Pane) tab.getContent();


        tab.setContent(null);       // set old content to nothing to remove

        // Create a new scene and apply the previous styles
        final Scene scene = new Scene(content, content.getPrefWidth(), content.getPrefHeight());
        scene.getStylesheets().addAll(stylesheets);

        // Create new stage and add the scene from the previous tab
        Stage stage = new Stage();
        stage.setScene(scene);                  // Set contents
        stage.setTitle(tab.getText());          // Set title text
        stage.setAlwaysOnTop(getForceTop());
//        Point2D p = MouseRobot.getMousePosition();
//        stage.setX(p.getX());
//        stage.setY(p.getY());

        // STUB: On Close request, return back to the tab pane
        stage.setOnCloseRequest((WindowEvent e) -> {
            stage.close();
            tab.setContent(content);
            int originalTabIndex = defaultTabs.indexOf(tab);
            map.put(originalTabIndex, tab);
            int index = 0;
            SortedSet<Integer> keys = new TreeSet<>(map.keySet());
            for (Integer key : keys) {
                Tab value = map.get(key);
                if(!tPane.getTabs().contains(value)){
                    tPane.getTabs().add(index, value);
                }
                index++;
            }
            tPane.getSelectionModel().select(tab);
        });


        // STUB: Remove Tab from pane
        stage.setOnShown((WindowEvent e) -> {
            tab.getTabPane().getTabs().remove(tab);
        });

        stage.show();
    }

}