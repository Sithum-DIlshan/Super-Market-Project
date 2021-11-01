package controller;

import bo.BoFactory;
import bo.custom.CustomerBO;
import bo.custom.ItemBO;
import bo.custom.OrderBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import tdm.AddToCartTM;
import tdm.ManageOrderTM;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CashierAccessFormController {

    public Button makeOrder;
    public Button manageOrderSideButton;
    public AnchorPane makeOrderAndAddCustomer;
    public AnchorPane addCustomer;
    public JFXTextField cId;
    public JFXTextField cTitle;
    public JFXTextField cName;
    public JFXTextField cAddress;
    public JFXTextField cCity;
    public JFXTextField cProvince;
    public JFXTextField cPostal;
    public AnchorPane sideButton;
    public AnchorPane defaultAnchorWithImage;
    public AnchorPane mkOrderAndAddCustomerDefault;
    public AnchorPane orderConfirm;
    public Label lblTtl;
    public Label lblTime;
    public Label lblDate;
    public JFXComboBox<String> customerComboBox;
    public JFXComboBox<String> itemComboBox;
    public TextField sltdDescription;
    public TextField sltdPackSize;
    public TextField sltdUnitPrice;
    public TextField sltdQtyOnHand;
    public TextField odredQty;
    public TextField discount;
    public TableView<AddToCartTM> addToCartTable;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colOdrQty;
    public TableColumn colTtl;
    public JFXButton placeOrder;
    public Label orderId;
    public AnchorPane manageOrderAnchor;
    public JFXComboBox<String> mngOdrCustomerIds;
    public JFXComboBox<String> mngOdrOrderIds;
    public TextField mngOdrCName;
    public TextField mngOdrNoOfItems;
    public TableView<ManageOrderTM> tblManageOrder;
    public TableColumn<ManageOrderTM, String> mngOdrCode;
    public TableColumn<ManageOrderTM, String> mngOdrDescription;
    public TableColumn<ManageOrderTM, Integer> mngOdrQty;
    public TableColumn<ManageOrderTM, Double> mngOdrUnitPrice;
    public JFXButton cancelOrderButton;
    int selectedRowForRemove = -1;
    public ObservableList<AddToCartTM> obList = FXCollections.observableArrayList();
    public ManageOrderTM removeItemFromDB;
    private final OrderBO orderBO = (OrderBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.ORDER);
    private final ItemBO itemBO = (ItemBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.ITEM);
    private final CustomerBO customerBO = (CustomerBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.CUSTOMER);
    //  private final OrderBO orderBO = (OrderBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.ORDER);

    public static String superItemId;

    public void initialize() throws SQLException, ClassNotFoundException {

        tblManageOrder.setEditable(true);

        colCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("itemUnitPrice"));
        colOdrQty.setCellValueFactory(new PropertyValueFactory<>("orderedQty"));
        colTtl.setCellValueFactory(new PropertyValueFactory<>("ttl"));

        mngOdrCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));

        mngOdrDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        mngOdrDescription.setCellFactory(TextFieldTableCell.forTableColumn());

        mngOdrQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        mngOdrQty.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        mngOdrUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        setOrderId();
        loadCustomersToComboBox();
        loadItemsToComboBox();
        setDate();
        setTime();
        setManageOrderCustomerIds();
        //setManageOrderOrderIds();


        itemComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemDetails(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        addToCartTable.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRowForRemove = (int) newValue;
        });

        mngOdrCustomerIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setManageOrderOrderIds(newValue);
                setManageOrderCustomerName(newValue);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        mngOrderOIdsListener();

        tblManageOrder.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            removeItemFromDB = tblManageOrder.getSelectionModel().getSelectedItem();
        });

        manageOrderTableEditOnAction();

    }

    private void enableCancelOrderButton() {
        if (!mngOdrOrderIds.getSelectionModel().isEmpty()) {
            cancelOrderButton.setDisable(false);
        }
    }

    private void mngOrderOIdsListener() {
        mngOdrOrderIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setManageOrderNoOfItems(newValue);
                loadManageOrderTable(newValue);
                enableCancelOrderButton();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void manageOrderTableEditOnAction() {
        mngOdrQty.setOnEditCommit(event -> {
            ManageOrderTM tm = event.getRowValue();
            if (tm.getOrderQty() > event.getNewValue()) {
                try {
                    if (itemBO.updateItemQty((tm.getOrderQty() - event.getNewValue()), tm.getItemCode()) &&
                            orderBO.updateOrderDetail(tm.getItemCode(), event.getNewValue())) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Quantity Edited ...").show();
                        loadManageOrderTable(mngOdrOrderIds.getSelectionModel().getSelectedItem());
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try Again ...").show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    if (orderBO.updateQty(tm.getItemCode(), (event.getNewValue() - tm.getOrderQty())) &&
                            orderBO.updateOrderDetail(tm.getItemCode(), event.getNewValue())) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Quantity Edited ...").show();
                        loadManageOrderTable(mngOdrOrderIds.getSelectionModel().getSelectedItem());
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try Again ...").show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        mngOdrDescription.setOnEditCommit(event -> {
            ManageOrderTM tm = event.getRowValue();
            try {
                if (itemBO.updateItemDescription(tm.getItemCode(), event.getNewValue())) {
                    loadManageOrderTable(mngOdrOrderIds.getSelectionModel().getSelectedItem());
                    new Alert(Alert.AlertType.CONFIRMATION, "Description Edited...").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again...").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadManageOrderTable(String orderId) throws SQLException, ClassNotFoundException {
        ObservableList<ManageOrderTM> manageOrderTMS = orderBO.getDataForTable(orderId);

        tblManageOrder.setItems(manageOrderTMS);
    }

    private void setManageOrderNoOfItems(String orderId) throws SQLException, ClassNotFoundException {
        int no = orderBO.getNoOfItems(orderId);
        mngOdrNoOfItems.setText(String.valueOf(no));
    }

    private void setManageOrderCustomerName(String custId) throws SQLException, ClassNotFoundException {
        CustomerDTO customerDTO = customerBO.getCustomerDetails(custId);

        if (customerDTO != null) {
            mngOdrCName.setText(customerDTO.getName());
        }
    }

    private void setManageOrderOrderIds(String custId) throws SQLException, ClassNotFoundException {
        ObservableList<String> obs = orderBO.getMngOrderOrderIds(custId);
        mngOdrOrderIds.setItems(obs);
    }

    private void setManageOrderCustomerIds() throws SQLException, ClassNotFoundException {
        mngOdrCustomerIds.setItems(customerBO.getCustomerIds());
    }


    private void setOrderId() throws SQLException, ClassNotFoundException {
        //System.out.println(new OrderBOImpl().getOrderId());
        orderId.setText(orderBO.getOrderId());
    }

    private void setTime() {
        Thread timerThread = new Thread(() -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            while (true) {
                try {
                    Thread.sleep(1000); //1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final String time = simpleDateFormat.format(new Date());
                Platform.runLater(() -> {
                    lblTime.setText(time);
                });
            }
        });
        timerThread.start();

    }

    private void setDate() {
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        Date dateShow = new Date();
        lblDate.setText(formatter1.format(dateShow));
    }

    public void changeColour(MouseEvent event) {
        if (event.getTarget() == makeOrder) {
            makeOrder.setStyle("-fx-background-color: #3498db; ");
        } else if (event.getTarget() == manageOrderSideButton) {
            manageOrderSideButton.setStyle("-fx-background-color: #3498db; ");
        }
    }

    public void defaultColour(MouseEvent event) {
        if (event.getTarget() == makeOrder) {
            makeOrder.setStyle("-fx-background-color: #ecf0f1; ");
        } else if (event.getTarget() == manageOrderSideButton) {
            manageOrderSideButton.setStyle("-fx-background-color: #ecf0f1; ");
        }
    }

    public void saveCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        CustomerDTO c = new CustomerDTO(
                cId.getText(),
                cTitle.getText(),
                cName.getText(),
                cAddress.getText(),
                cCity.getText(),
                cProvince.getText(),
                cPostal.getText()
        );
        if (cId.getText().isEmpty() || cTitle.getText().isEmpty() || cName.getText().isEmpty() || cAddress.getText().isEmpty() || cCity.getText().isEmpty() || cProvince.getText().isEmpty() || cPostal.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please Fill All Fields ...").show();
        } else {
            if (customerBO.customerSaved(c)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved ...").show();
            } else new Alert(Alert.AlertType.WARNING, "Try Again ...").show();
        }
        loadCustomersToComboBox();
    }

    public void openAddCustomer(ActionEvent actionEvent) {
        mkOrderAndAddCustomerDefault.setVisible(false);
        orderConfirm.setVisible(false);
        addCustomer.setVisible(true);
    }

    public void openMkOdrAddCstmr(ActionEvent actionEvent) {
        sideButton.setVisible(false);
        defaultAnchorWithImage.setVisible(false);
        orderConfirm.setVisible(false);
        makeOrderAndAddCustomer.setVisible(true);
    }

    public void openMakeOrder(ActionEvent actionEvent) {
        mkOrderAndAddCustomerDefault.setVisible(false);
        addCustomer.setVisible(false);
        orderConfirm.setVisible(true);
    }

    public void addToCartOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        double unitPriceAfterDiscount = 0;
        if (discount.getText() != null) {
            if (itemBO.addDiscountToItem(discount.getText()) != -1) {
                unitPriceAfterDiscount =
                        Double.parseDouble(sltdUnitPrice.getText()) - itemBO.addDiscountToItem(discount.getText());
                new Alert(Alert.AlertType.CONFIRMATION, "Discount Added").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Discount Not Added.. Invalid Discount Code").show();
                unitPriceAfterDiscount =
                        Double.parseDouble(sltdUnitPrice.getText());
            }
        } else {
            unitPriceAfterDiscount =
                    Double.parseDouble(sltdUnitPrice.getText());
        }


        int ordredQty = Integer.parseInt(odredQty.getText());

        if (ordredQty <= Integer.parseInt(sltdQtyOnHand.getText())) {

            double ttl = unitPriceAfterDiscount * ordredQty;

            AddToCartTM tm = new AddToCartTM(
                    itemComboBox.getValue(),
                    sltdDescription.getText(),
                    customerComboBox.getValue(),
                    Double.parseDouble(sltdUnitPrice.getText()),
                    ordredQty,
                    ttl
            );

            int rowNumber = isExists(tm);

            if (rowNumber == -1) {
                obList.add(tm);
            } else {
                AddToCartTM temp = obList.get(rowNumber);
                obList.add(new AddToCartTM(
                        temp.getItemCode(),
                        temp.getItemDescription(),
                        temp.getCustomerId(),
                        temp.getItemUnitPrice(),
                        (temp.getOrderedQty() + ordredQty),
                        (temp.getTtl() + ttl)
                ));
                obList.remove(rowNumber);
            }
        } else new Alert(Alert.AlertType.WARNING, "Invalid Order Quantity ...").show();


        addToCartTable.setItems(obList);

        if (obList != null) {
            placeOrder.setVisible(true);
        }

        setSubTtl();
    }

    private void setSubTtl() {
        double ttl = 0;
        for (AddToCartTM temp :
                obList) {
            ttl += temp.getTtl();
        }
        lblTtl.setText("-/" + ttl);
    }

    public void clearSltdItem(ActionEvent actionEvent) {
        if (selectedRowForRemove != -1) {
            obList.remove(selectedRowForRemove);
            addToCartTable.refresh();
            setSubTtl();
        } else {
            new Alert(Alert.AlertType.WARNING, "Not Selected ...").show();
        }

    }

    private void loadCustomersToComboBox() throws SQLException, ClassNotFoundException {
        if (customerBO.getCustomerIds() != null) {
            customerComboBox.setItems(customerBO.getCustomerIds());
        }
    }


    private void loadItemsToComboBox() throws SQLException, ClassNotFoundException {
        itemComboBox.setItems(itemBO.getItemIds());
    }

    public void setItemDetails(String itemId) throws SQLException, ClassNotFoundException {
        superItemId = itemId;
        ItemDTO item = itemBO.search(itemId);

        if (item == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Set ...").show();
        } else {
            sltdDescription.setText(item.getDescription());
            sltdPackSize.setText(item.getPackSize());
            sltdUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            sltdQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
        }
    }

    private int isExists(AddToCartTM tm) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getItemCode().equals(obList.get(i).getItemCode())) {
                return i;
            }
        }
        return -1;
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        System.out.println(orderId.getText());
        ArrayList<OrderDetailsDTO> item = new ArrayList<>();

        for (AddToCartTM temp :
                obList) {
            item.add(new OrderDetailsDTO(
                    orderId.getText(),
                    temp.getItemCode(),
                    temp.getOrderedQty(),
                    temp.getItemUnitPrice() - (temp.getTtl() / temp.getOrderedQty())
            ));
        }

        OrderDTO order = new OrderDTO(
                orderId.getText(),
                new Date(),
                customerComboBox.getValue(),
                item
        );

        if (orderBO.saveOrder(order)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Done ...").show();
        }

        setOrderId();

    }

    public void backToSideButton(ActionEvent actionEvent) {
        makeOrderAndAddCustomer.setVisible(false);
        manageOrderAnchor.setVisible(false);
        sideButton.setVisible(true);
        defaultAnchorWithImage.setVisible(true);
    }

    public void cancelOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!mngOdrOrderIds.getSelectionModel().isEmpty()) {
            if (orderBO.removeOrderAndUpdateQty(mngOdrOrderIds.getSelectionModel().getSelectedItem())) {
                mngOrderOIdsListener();
                setManageOrderOrderIds(mngOdrCustomerIds.getSelectionModel().getSelectedItem());
                new Alert(Alert.AlertType.CONFIRMATION, "Order Cancelled...").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Order Not Selected...").show();
            }
        }
    }

    public void removeItemFromMngOrderTableOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (removeItemFromDB != null && Integer.parseInt(mngOdrNoOfItems.getText()) > 1) {
            if (itemBO.removeItem(removeItemFromDB.getItemCode())) {
                if (itemBO.updateItemQty(removeItemFromDB.getOrderQty(), removeItemFromDB.getItemCode())) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Item Removed ...").show();
                    loadManageOrderTable(mngOdrOrderIds.getSelectionModel().getSelectedItem());
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again ...").show();
                }
            }
        } else if (removeItemFromDB != null && Integer.parseInt(mngOdrNoOfItems.getText()) == 1) {
            if (itemBO.removeItem(removeItemFromDB.getItemCode())) {
                if (itemBO.updateItemQty(removeItemFromDB.getOrderQty(), removeItemFromDB.getItemCode()) && orderBO.removeOrder(mngOdrOrderIds.getSelectionModel().getSelectedItem())) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Item Removed ...").show();
                    loadManageOrderTable(mngOdrOrderIds.getSelectionModel().getSelectedItem());
                    setManageOrderOrderIds(mngOdrCustomerIds.getSelectionModel().getSelectedItem());
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again ...").show();
                }
            }
        } else
            new Alert(Alert.AlertType.WARNING, "Item Not Selected ...").show();
    }

    public void openManageOrder(ActionEvent actionEvent) {
        sideButton.setVisible(false);
        defaultAnchorWithImage.setVisible(false);
        orderConfirm.setVisible(false);
        makeOrderAndAddCustomer.setVisible(false);
        manageOrderAnchor.setVisible(true);
    }

    /*public void tblMangeOrderEdit(TableColumn.CellEditEvent cellEditEvent) {
        ManageOrderTM tm = tblManageOrder.getSelectionModel().getSelectedItem();
        System.out.println("Hello World");
    }*/
    public void logoutOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) defaultAnchorWithImage.getScene().getWindow();
        stage.close();
    }

}

