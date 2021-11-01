package controller;

import bo.BoFactory;
import bo.custom.ItemBO;
import bo.custom.OrderBO;
import bo.custom.PieChartBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import dto.ItemDTO;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;

public class AdminAccessFormController {

    public AnchorPane manageItem;
    public AnchorPane systemReports;
    public JFXButton button;
    public JFXButton button2;
    public JFXButton button3;
    public AnchorPane registerNewItem;
    public AnchorPane modifyItem;
    public AnchorPane removeItem;
    public JFXTextField itemCode;
    public JFXTextField itemDescription;
    public JFXTextField itemPackSize;
    public JFXTextField itemUnitPrice;
    public JFXTextField qtyOnHand;
    public AnchorPane defaultAnchor;
    public JFXTextField searchCode;
    public JFXTextField updtDsc;
    public JFXTextField updtPackSize;
    public JFXTextField updtUntP;
    public JFXTextField updtQty;
    public JFXTextField deleteCode;
    public PieChart pieChart;
    public AnchorPane adminAnchor;
    private final PieChartBO pieChartBO = (PieChartBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.PIE_CHART);
    private final ItemBO itemBO = (ItemBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.ITEM);
    private final OrderBO orderBO = (OrderBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.ORDER);

    public void initialize() {
        try {
            loadDataToPieChart();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadDataToPieChart() throws SQLException, ClassNotFoundException {

        ObservableList<PieChart.Data> pie = pieChartBO.loadPieChart();

        pie.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), "\namount: ", data.pieValueProperty()
                        )
                )
        );

        pieChart.getData().addAll(pie);
    }

    public void manageItemsOnAction(ActionEvent actionEvent) {
        systemReports.setVisible(false);
        manageItem.setVisible(true);
        defaultAnchor.setVisible(true);
    }

    public void systemReportsOnAction(ActionEvent actionEvent) {
        manageItem.setVisible(false);
        modifyItem.setVisible(false);
        removeItem.setVisible(false);
        defaultAnchor.setVisible(false);
        registerNewItem.setVisible(false);
        systemReports.setVisible(true);
    }

    public void changeColour(MouseEvent event) {
        if (event.getTarget() == button) {
            button.setOpacity(1);
        } else if (event.getTarget() == button2) {
            button2.setOpacity(1);
        } else if (event.getTarget() == button3) {
            button3.setOpacity(1);
        }

    }

    public void backToNormal(MouseEvent event) {
        if (event.getTarget() == button) {
            button.setOpacity(0.70);
        } else if (event.getTarget() == button2) {
            button2.setOpacity(0.70);
        } else if (event.getTarget() == button3) {
            button3.setOpacity(0.70);
        }
        //button2.setOpacity(0.70);
    }

    public void openRegisterItem(ActionEvent actionEvent) {
        modifyItem.setVisible(false);
        removeItem.setVisible(false);
        defaultAnchor.setVisible(false);
        registerNewItem.setVisible(true);
    }

    public void openModifyItem(ActionEvent actionEvent) {
        registerNewItem.setVisible(false);
        removeItem.setVisible(false);
        defaultAnchor.setVisible(false);
        modifyItem.setVisible(true);
    }

    public void openRemoveItem(ActionEvent actionEvent) {
        registerNewItem.setVisible(false);
        modifyItem.setVisible(false);
        defaultAnchor.setVisible(false);
        removeItem.setVisible(true);
    }

    public void loadDefault(MouseEvent event) {
        registerNewItem.setVisible(false);
        modifyItem.setVisible(false);
        removeItem.setVisible(false);
        defaultAnchor.setVisible(true);
    }

    public void saveItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemDTO itemDTO = new ItemDTO(
                itemCode.getText(), itemDescription.getText(),
                itemPackSize.getText(), Double.parseDouble(itemUnitPrice.getText()),
                Integer.parseInt(qtyOnHand.getText())
        );
        if (itemBO.ItemSaved(itemDTO)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Item Saved").show();
        }
    }

    public void updateItemDetails(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemDTO itemDTO = new ItemDTO(searchCode.getText(), updtDsc.getText(), updtPackSize.getText(), Double.parseDouble(updtUntP.getText()), Integer.parseInt(updtQty.getText()));
        if (itemBO.itemUpdated(itemDTO)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Update Successfully...").show();
        } else new Alert(Alert.AlertType.WARNING, "Try Again...").show();
    }

    public void deleteItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (itemBO.itemDeleted(deleteCode.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        } else new Alert(Alert.AlertType.WARNING, "Try Again").show();
    }

    public void searchItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
         ItemDTO itemDTO= itemBO.search(searchCode.getText());
        if (itemDTO != null) {
            updtDsc.setText(itemDTO.getDescription());
            updtPackSize.setText(itemDTO.getPackSize());
            updtUntP.setText(String.valueOf(itemDTO.getUnitPrice()));
            updtQty.setText(String.valueOf(itemDTO.getQtyOnHand()));
        } else new Alert(Alert.AlertType.WARNING, "Empty Set").show();
    }

    public void logoutOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) adminAnchor.getScene().getWindow();
        stage.close();
    }

    public void openMonthlyIncome(ActionEvent actionEvent) {

    }

    public void openYearlyIncome(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, JRException {
        JasperDesign load = JRXmlLoader.load(getClass().getResourceAsStream("/view/reports/AnnuallyIncomeReportSuperMarket.jrxml"));
        JasperReport report = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, null, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);

    }
}
