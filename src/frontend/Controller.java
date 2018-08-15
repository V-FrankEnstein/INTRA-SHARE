package frontend;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import frontend.TableHandler.*;

public class Controller {


    public JFXButton registerLoginPane;
    public JFXButton login;
    public JFXTextField txtUserNameLogin;
    public JFXPasswordField txtPasswordLogin;
    public JFXTextField firstName;
    public JFXTextField lastName;
    public JFXTextField userName;
    public JFXPasswordField password;
    public JFXPasswordField confirmPassword;
    public JFXButton register;
    public JFXButton cancel;
    public AnchorPane halfDashboard;
    public AnchorPane registerationPane;
    public AnchorPane loginPane;
    public AnchorPane mainFrame;
    public JFXTreeTableView dataTable;
    ObservableList<FileDetails> weightage = FXCollections.observableArrayList();
    TableHandler tableHandler;

    public void initializer() {
        tableHandler = new TableHandler();
    }

    /**
     * Action on Login Button
     */
    public void verifyLogin() {
        String userName = txtUserNameLogin.getText();
        String password = txtPasswordLogin.getText();
        initializer();

        weightage.add(new FileDetails("sdf", "CD 1"));
        weightage.add(new FileDetails("sdf", "Employee 1"));
        weightage.add(new FileDetails("sdf", "Employee 2"));
        tableHandler.start(weightage);
        dataTable = new JFXTreeTableView<>(tableHandler.getRoot());
        dataTable.setShowRoot(false);
        dataTable.setEditable(true);
        dataTable.getColumns().setAll(tableHandler.getChapterColumn(), tableHandler.getMarksColumn());
        dataTable.setLayoutX(50);
        dataTable.setLayoutY(50);
        dataTable.setPrefSize(700, 350 );
        mainFrame.getChildren().add(dataTable);

        showMainFrame();
}

    /**
     * Action on RegisterLoginPanel Button of Login Pane.
     */
    public void registerPanel() {
        loginPane.setVisible(false);
        registerationPane.setVisible(true);
    }

    /**
     * Register panel k Register Button ka action.
     */
    public void registerAction() {

        registerPanel();
    }

    /**
     * Register panel k cancel Button ka action
     */
    public void cancelAction() {
        registerationPane.setVisible(false);
        loginPane.setVisible(true);
    }

    /**
     * this method deals with the action event of the Upload Button visible on the MainFrame Panel.
     */
    public void uploadAction() {

    }

    /**
     * this method deals with the action event of the Download Button visible on the MainFrame Panel.
     */
    public void downloadAction() {

    }

    /**
     * this method deals with the action event of the Delete Button visible on the MainFrame Panel.
     */
    public void deleteAction() {

    }

    /**
     * this method deals with the action event of the Logout Button visible on the MainFrame Panel.
     */
    public void logoutAction() {

    }

    /**
     * This method set all the other panels visibility false and only the mainFrame panels Visibility is set True.
     */
    public void showMainFrame() {
        registerationPane.setVisible(false);
        loginPane.setVisible(false);
        halfDashboard.setVisible(false);
        mainFrame.setVisible(true);
    }





    public ObservableList<FileDetails> getWeightage() {
        return weightage;
    }

    public void setDataTable(JFXTreeTableView dataTable) {
        this.dataTable = dataTable;
    }

    public void setWeightage(ObservableList<FileDetails> weightage) {
        this.weightage = weightage;
    }
}