import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;

public class CreateGraphic {

    public CreateGraphic(String chartTitle, int IdTipo) {

        // Creamos el conjunto de datos con las votaciones
        DefaultCategoryDataset dataset = createDataset();

        JFreeChart chart = createChart(dataset, chartTitle, IdTipo);
        // Ponemos el gráfico en un panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // Dejamos el tamaño por defecto
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));

    }

    private DefaultCategoryDataset createDataset() {
        //ESTO DEBE OBTENER LOS DATOS DE LA BASE DE DATOS
        DefaultCategoryDataset result = new DefaultCategoryDataset();

        result.setValue(154, "Enero", "");
        result.setValue(169, "Febrero", "");
        result.setValue(10, "Marzo", "");

        return result;

    }

    private JFreeChart createChart(DefaultCategoryDataset dataset, String title, int IdTipo) {
        String tipo;
        switch (IdTipo) {
            case 1:
                tipo = "Minutos";
                break;
            case 2:
                tipo = "Internet";
                break;
            default:
                tipo = "Mensajes";
                break;
        }
        JFreeChart chart = ChartFactory.createBarChart3D(title, "Meses",
                tipo, dataset, // data
                PlotOrientation.VERTICAL, true, // include legend
                true, false);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis xAxis = (CategoryAxis) plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // Inclinamos 45 grados las etiquetas del eje X
        plot.setBackgroundAlpha(0.5f);
        try {
            ChartUtilities.saveChartAsPNG(new File("src/assets/images/consumo" + tipo + ".png"), chart, 400, 300);
        } catch (IOException e) {
            System.out.println("Can't generate bill");
        }
        return chart;

    }
}
