package bo.custom;

import bo.SuperBO;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.SQLException;

public interface PieChartBO extends SuperBO {
    ObservableList<PieChart.Data> loadPieChart() throws SQLException, ClassNotFoundException;
}
