package functionalities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Arrays;
import java.util.StringTokenizer;

public class CreateGraphic {

    private String[] consulta;
    private int plan;
    private String name;
    public CreateGraphic(String consulta, String infoPlan) {
        StringTokenizer tokens=new StringTokenizer(infoPlan, ",");
        int nDatos=tokens.countTokens();
        this.consulta = new String[nDatos];
        int i=0;
        while(tokens.hasMoreTokens()){
            this.consulta[i]= (tokens.nextToken());
            i++;
        }
        plan = Integer.parseInt(this.consulta[0].substring(1,2));
        name = this.consulta[1].substring(0,this.consulta[1].length()-1);

        tokens=new StringTokenizer(consulta, ",");
        nDatos=tokens.countTokens();
        this.consulta = new String[nDatos];
        i=0;
        while(tokens.hasMoreTokens()){
            this.consulta[i]= (tokens.nextToken());
            i++;
        }
    }

    public  ChartPanel Createchart(int month) {
        DefaultCategoryDataset Datos = new DefaultCategoryDataset();
        String months[]={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio",
                "Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
            for(int j=0; j<month; j++){
                Datos.setValue(Float.parseFloat(consulta[1]),"Datos",months[j]);
                Datos.setValue(Float.parseFloat(consulta[2]),"Minutos",months[j]);
                Datos.setValue(Float.parseFloat(consulta[3]),"Mensajes",months[j]);
                Datos.setValue(Float.parseFloat(consulta[4]),"Datos de Whatsapp",months[j]);
                Datos.setValue(Float.parseFloat(consulta[5]),"Minutos de Whatsapp",months[j]);
                Datos.setValue(Float.parseFloat(consulta[6]),"Datos de Facebook",months[j]);

        }
        JFreeChart Grafica = ChartFactory.createBarChart("Consumo mensual Linea: "+ consulta[12]+ " Cliente: "+name+"\nPlan tipo: "+plan,
                "Tipo consumo", "Consumo", Datos,
                PlotOrientation.HORIZONTAL, true, true, false);
        ChartPanel Panel = new ChartPanel(Grafica);
        return Panel;
    }
}

