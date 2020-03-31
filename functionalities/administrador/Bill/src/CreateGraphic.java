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


    public CreateGraphic(String[] consume, int idPlan) {
        DefaultCategoryDataset dataset = createDataset(consume,1,3);
        JFreeChart chart = createChart(dataset, "Consumo mensual comun", 1);

        if(idPlan == 4){
            dataset = createDataset(consume,4,7);
            JFreeChart  chart2 = createChart(dataset, "Consumo mensual adicionales", 3);
            ChartPanel chartPanel = new ChartPanel(chart2);
            chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        } else if(idPlan == 5){
            dataset = createDataset(consume,4,7);
            JFreeChart  chart2 = createChart(dataset, "Consumo mensual adicionales", 3);
            ChartPanel chartPanel = new ChartPanel(chart2);
            chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
            dataset = createDataset(consume,8,9);
            JFreeChart  chart3 = createChart(dataset, "Consumo mensual internacional y compartido", 4);
            chartPanel = new ChartPanel(chart3);
            chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        } else {
            dataset = createDataset(consume,4,6);
            JFreeChart chart1 = createChart(dataset, "Consumo mensual adicionales", 2);
            ChartPanel chartPanel = new ChartPanel(chart1);
            chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        }
        // Put graph on a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
    }

    private DefaultCategoryDataset createDataset(String[] consume, int start, int end) {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        String[] types = {"Consumo datos (MB)","Consumo minutos","Consumo mensajes","Consumo datos Whatsapp", "Consumo minutos Whatsapp",
                "Consumo datos Facebook","Consumo datos Waze","Consumo minutos internaciones","Consumo datos compartidos"};
        for(int i=start; i<= end; i++){
            result.setValue(Float.parseFloat(consume[i]),types[i-1],"");
        }

        return result;
    }

    private JFreeChart createChart(DefaultCategoryDataset dataset, String title, int IdTipo) {
        String tipo;
        switch (IdTipo) {
            case 1:
                tipo = "Comun";
                break;
            case 2:
                tipo = "Adicionales";
                break;
            case 3:
                tipo = "Adicionalesplus";
                break;
            default:
                tipo = "internacionalycompartido";
                break;
        }
        JFreeChart chart = ChartFactory.createBarChart3D(title, "Tipo",
                "Consumo", dataset, // data
                PlotOrientation.VERTICAL, true, // include legend
                true, false);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis xAxis = (CategoryAxis) plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        plot.setBackgroundAlpha(0.5f);
        try {
            ChartUtilities.saveChartAsPNG(new File("functionalities/administrador/Bill/src/assets/images/consumo" + tipo + ".png"), chart, 400, 300);
        } catch (IOException e) {
            System.out.println("Can't generate bill");
        }
        return chart;

    }
}
