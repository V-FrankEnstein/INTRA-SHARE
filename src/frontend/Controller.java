package frontend;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import frontend.TableHandler.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static com.intrashare.server.backend.FileFolderOperation.readFileContents;

public class Controller {

    public JFXTextField txtUserName;
    public JFXTextField txtLastName;
    public JFXTextField txtFirstName;
    public JFXPasswordField txtConfirmPassword;
    public Login login;
    public Register register;
    public JFXButton registerLoginPane;
    public JFXButton btnLogin;
    public JFXTextField txtUserNameLogin;
    public JFXPasswordField txtPasswordLogin;
    public JFXTextField userName;
    public JFXPasswordField txtPassword;
    public JFXPasswordField confirmPassword;
    public JFXButton btnRegister;
    public JFXButton cancel;
    public AnchorPane halfDashboard;
    public AnchorPane registerationPane;
    public AnchorPane loginPane;
    public AnchorPane mainFrame;
    public JFXTreeTableView dataTable;
    ObservableList<FileDetails> fileDetails = FXCollections.observableArrayList();
    TableHandler tableHandler;
    private String fixUserNameForReference;
    public Controller() {
        login = new Login();
        register = new Register();
    }

    public void initializer() {
        tableHandler = new TableHandler();
    }

    /**
     * Action on Login Button
     */
    public void verifyLogin() {
        String userName = txtUserNameLogin.getText().trim();
        String password = txtPasswordLogin.getText().trim();
        JOptionPane.showMessageDialog(null, userName + password, "title", JOptionPane.INFORMATION_MESSAGE);
        try {
            if (login.processLogin(userName, password)) {
                fixUserNameForReference = userName.trim();
                tableHandler();
            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void tableHandler() {
        initializer();
        HashMap<String, HashMap<String,HashMap<Long, Long>>> hm;
        hm = LoginInfoToServer.getHm();
        System.out.println(hm.size());
        for (String key : hm.keySet()) {
            for(String key1 : hm.get(key).keySet()) {
                for(Long key2 : hm.get(key).get(key1).keySet()){
                    fileDetails.add(new FileDetails(key1, new SimpleDateFormat("dd/MM/yy").format(new Date(hm.get(key).get(key1).get(key2)))));
                }
            }
        }
        tableHandler.start(fileDetails);
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
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String userName = txtUserName.getText().trim();
        String password = txtPassword.getText().trim();
        String confirmPassword = txtConfirmPassword.getText().trim();
        JOptionPane.showMessageDialog(null, firstName + lastName + userName + password + confirmPassword, "title", JOptionPane.INFORMATION_MESSAGE);
        try {
            if (register.processRegistration(firstName, lastName, userName, password, confirmPassword)) {

            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


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
        try {
            FileToServer fts = new FileToServer(fixUserNameForReference);
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(null);
            File sourceFile = fileChooser.getSelectedFile();
            if (sourceFile != null) {
                String filePath = sourceFile.getAbsolutePath();
                String fileName = sourceFile.getName();
                byte[] fileData = readFileContents(filePath);
                if ( fts.uploadFileToServer( fileName, fileData ) ) {
                    TreeItem<FileDetails> selectedItem = (TreeItem<FileDetails>) dataTable.getSelectionModel().getSelectedItem();
                    TreeItem<FileDetails> tt = (TreeItem<FileDetails>) new RecursiveTreeItem<>(new FileDetails(fileName, new Date().toString()), RecursiveTreeObject::getChildren);
                    System.out.println(selectedItem.getParent().getClass()); //this will return com.jfoenix.controls.RecursiveTreeItem.
                    selectedItem.getParent().getChildren().add(tt);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "upload k time pe execption aaya hai", "MainControl.java", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * this method deals with the action event of the Download Button visible on the MainFrame Panel.
     */
    public void downloadAction() {
        DownloadInfoToServer dits = new DownloadInfoToServer();
        TreeItem<FileDetails> selectedItem = (TreeItem<FileDetails>) dataTable.getSelectionModel().getSelectedItem();
        FileDetails fd =  selectedItem.getValue();
        String fileName = fd.chapterNo.getValue();
        try {
            dits.sendFileInfo(fixUserNameForReference, fileName);
        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }


    /**
     * this method deals with the action event of the Delete Button visible on the MainFrame Panel.
     */
    public void deleteAction() {
        TreeItem<FileDetails> selectedItem = (TreeItem<FileDetails>) dataTable.getSelectionModel().getSelectedItem();
        FileDetails fd =  selectedItem.getValue();
        String userName = "pp";
        String fileName = fd.chapterNo.getValue();
        try {
            int i = JOptionPane.showConfirmDialog(null, "Are You Sure You want to Delete?", "Delete", JOptionPane.YES_NO_OPTION);
            if (i != 1) {
                if (processDelete( userName, fileName )) {
                    JOptionPane.showMessageDialog(null, "Deletion Successfull", "DeleteServer", JOptionPane.INFORMATION_MESSAGE);
                    selectedItem.getParent().getChildren().remove(selectedItem);
                } else {
                    JOptionPane.showMessageDialog(null, "Deletion Unsuccessfull", "DeleteServer", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (ClassNotFoundException ex) {
            ex.getStackTrace();
        }
    }

    /**
     * this method deals with the action event of the Logout Button visible on the MainFrame Panel.
     */
    public void logoutAction() {
        System.exit(0);
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

    public void setDataTable(JFXTreeTableView dataTable) {
        this.dataTable = dataTable;
    }

    public void setFileDetails(ObservableList<FileDetails> fileDetails) {
        this.fileDetails = fileDetails;
    }

    boolean processDelete(String userName, String fileName) throws ClassNotFoundException {
        DeleteInfoToServer dits = new DeleteInfoToServer();
        try {
            return dits.sendFileInfo(userName, fileName);
        } catch (IOException ex) {
        }
        return false;
    }

}