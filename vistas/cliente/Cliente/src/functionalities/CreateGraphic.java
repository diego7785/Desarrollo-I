package functionalities;

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
    private boolean ready = false;
    public CreateGraphic(String chartTitle, int IdTipo, int cantMeses) {

        // Creamos el conjunto de datos con las votaciones
        DefaultCategoryDataset dataset = createDataset(cantMeses);

        JFreeChart chart = createChart(dataset, chartTitle, IdTipo);
        // Ponemos el gráfico en un panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // Dejamos el tamaño por defecto
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        ready=true;
    }

    public boolean getReady(){
        return ready;
    }

    private DefaultCategoryDataset createDataset(int cantMeses) {
        //ESTO DEBE OBTENER LOS DATOS DE LA BASE DE DATOS
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        String months[]={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio",
                "Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        for(int i = 0; i<cantMeses; i++){
            result.setValue(154*i, months[i], "");
        }
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
            ChartUtilities.saveChartAsPNG(new File("vistas/cliente/Cliente/src/assets/images/consumo" + tipo + ".png"), chart, 400, 300);
        } catch (IOException e) {
            System.out.println("Can't generate bill");
        }
        return chart;

    }
}

