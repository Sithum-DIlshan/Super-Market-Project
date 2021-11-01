package bo.custom.impl;

import bo.BoFactory;
import bo.custom.OrderBO;
import bo.custom.PieChartBO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.SQLException;
import java.util.ArrayList;

public class PieChartBOImpl implements PieChartBO {

    private final OrderBO orderBO = (OrderBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.ORDER);

    @Override
    public ObservableList<PieChart.Data> loadPieChart() throws SQLException, ClassNotFoundException {
        ArrayList<String> ids = orderBO.getItemOrderQuantities();

        ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();

        if (ids != null) {
            for (String id :
                    ids) {
                pie.add(
                        new PieChart.Data(id, orderBO.getTotalQty(id))
                );
            }
        }

        return pie;
    }
}
