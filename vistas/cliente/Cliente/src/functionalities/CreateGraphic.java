package functionalities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.*;
import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;

public class CreateGraphic {

    public  ChartPanel Createchart(int month) {
        //ESTO DEBE OBTENER LOS DATOS DE LA BASE DE DATOS
        DefaultCategoryDataset Datos = new DefaultCategoryDataset();
        String months[]={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio",
                "Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        String tipo[]={"Minutos","Internet", "Mensajes"};
        for(int i=0; i<=2; i++){
            for(int j=0; j<month; j++){
                Datos.setValue(154*(i+1),tipo[i],months[j]);
            }
        }
        JFreeChart Grafica = ChartFactory.createBarChart("Consumo mensual",
                "Tipo consumo", "Consumo", Datos,
                PlotOrientation.HORIZONTAL, true, true, false);
        ChartPanel Panel = new ChartPanel(Grafica);
        return Panel;
    }
}

