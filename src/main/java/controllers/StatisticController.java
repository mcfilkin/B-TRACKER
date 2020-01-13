package controllers;

import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;

public class StatisticController {
    @FXML
    public PieChart pie;

    @FXML
    void initialize() {
        pie.getData().add(new PieChart.Data("Открыто (2)", 0.4));
        pie.getData().add(new PieChart.Data("Закрыто (2)", 0.4));
        pie.getData().add(new PieChart.Data("В исправлении (1)", 0.2));
        pie.setLegendSide(Side.BOTTOM);
        pie.setLegendVisible(false);
    }
}
