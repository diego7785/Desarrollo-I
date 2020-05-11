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

    public CreateGraphic(String[] consume, int idPlan, String[] price) {
        DefaultCategoryDataset dataset = createDataset(consume,1,3, price);
        JFreeChart chart = createChart(dataset, "Consumo mensual comun", 1);

        if(idPlan == 4){
            dataset = createDataset(consume,4,7, price);
            JFreeChart  chart2 = createChart(dataset, "Consumo mensual adicionales", 3);

            ChartPanel chartPanel = new ChartPanel(chart2);
            chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        }
        else if(idPlan == 5){
            dataset = createDataset(consume,4,7, price);
            JFreeChart  chart2 = createChart(dataset, "Consumo mensual adicionales", 3);

            ChartPanel chartPanel = new ChartPanel(chart2);
            chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));

            dataset = createDataset(consume,8,9, price);
            JFreeChart  chart3 = createChart(dataset, "Consumo mensual internacional y compartido", 4);

            chartPanel = new ChartPanel(chart3);
            chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        }
        else {
            dataset = createDataset(consume,4,6, price);
            JFreeChart chart1 = createChart(dataset, "Consumo mensual adicionales", 2);

            ChartPanel chartPanel = new ChartPanel(chart1);
            chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        }

        // Put graph on a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
    }

    private DefaultCategoryDataset createDataset(String[] consume, int start, int end, String[] price) {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        String[] types = {"Consumo datos (MB)","Consumo minutos","Consumo mensajes","Consumo datos Whatsapp", "Consumo minutos Whatsapp",
                "Consumo datos Facebook","Consumo datos Waze","Consumo minutos internaciones","Consumo datos compartidos"};

        for(int i=start; i<= end; i++){
            float value=0;
            if(Integer.parseInt(consume[12]) != 0){
                if(i == 1){
                    value = 2 * Float.parseFloat(price[2]) - Float.parseFloat(consume[1]);
                }
                else if(i == 2){
                    value = 2 * Float.parseFloat(price[1]) - Float.parseFloat(consume[2]);
                }
                else{
                    value = 2 * Float.parseFloat(price[i]) - Float.parseFloat(consume[i]);
                }
            }
            else {
                if(i == 1){
                    value = Float.parseFloat(price[2]) - Float.parseFloat(consume[1]);
                }
                else if(i == 2){
                    value = Float.parseFloat(price[1]) - Float.parseFloat(consume[2]);
                }
                else{
                    value = Float.parseFloat(price[i]) - Float.parseFloat(consume[i]);
                }
            }
            result.setValue(value,types[i-1],"");
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
