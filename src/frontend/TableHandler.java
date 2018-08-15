package frontend;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellEditEvent;

public class TableHandler {
    JFXTreeTableColumn<FileDetails, String> chapterColumn = new JFXTreeTableColumn<>("Chapter No");
    JFXTreeTableColumn<FileDetails, String> marksColumn = new JFXTreeTableColumn<>("Marks");
    TreeItem<FileDetails> root;

    public void start(ObservableList<FileDetails> fileDetails) {

        chapterColumn.setPrefWidth(150);
        chapterColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<FileDetails, String> param) -> {
            if (chapterColumn.validateValue(param)) {
                return param.getValue().getValue().chapterNo;
            } else {
                return chapterColumn.getComputedValue(param);
            }
        });

        marksColumn.setPrefWidth(150);
        marksColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<FileDetails, String> param) -> {
            if (marksColumn.validateValue(param)) {
                return param.getValue().getValue().marks;
            } else {
                return marksColumn.getComputedValue(param);
            }
        });

        

        root = new RecursiveTreeItem<>(fileDetails, RecursiveTreeObject::getChildren);
    }

    public static final class FileDetails extends RecursiveTreeObject<FileDetails> {
        final StringProperty marks;
        final StringProperty chapterNo;

        FileDetails(String chapterNo, String marks) {
            this.chapterNo = new SimpleStringProperty(chapterNo);
            this.marks = new SimpleStringProperty(marks);
        }
    }

    public JFXTreeTableColumn<FileDetails, String> getChapterColumn() {
        return chapterColumn;
    }

    public JFXTreeTableColumn<FileDetails, String> getMarksColumn() {
        return marksColumn;
    }

    public TreeItem<FileDetails> getRoot() {
        return root;
    }

}
