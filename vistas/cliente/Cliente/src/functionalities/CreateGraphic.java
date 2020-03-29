    package functionalities;

    import org.jfree.chart.ChartFactory;
    import org.jfree.chart.ChartPanel;
    import org.jfree.chart.JFreeChart;
    import org.jfree.chart.plot.PlotOrientation;
    import org.jfree.data.category.DefaultCategoryDataset;

    import java.util.Vector;


    public class CreateGraphic {

        public  ChartPanel Createchart(Vector<String[]> queryV, String[] infoPlan, int tipo) {
            int plan = Integer.parseInt(infoPlan[0]);
            String months[]={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio",
                    "Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
            if(tipo == 1){
                String[] query = queryV.get(0);
                int month = Integer.parseInt(query[13].substring(5,7));
                DefaultCategoryDataset Datos = new DefaultCategoryDataset();
                Datos.setValue(Float.parseFloat(query[1]),"Datos",months[month-1]);
                Datos.setValue(Float.parseFloat(query[2]),"Minutos",months[month-1]);
                Datos.setValue(Float.parseFloat(query[3]),"Mensajes",months[month-1]);
                Datos.setValue(Float.parseFloat(query[4]),"Datos de Whatsapp",months[month-1]);
                Datos.setValue(Float.parseFloat(query[5]),"Minutos de Whatsapp",months[month-1]);
                Datos.setValue(Float.parseFloat(query[6]),"Datos de Facebook",months[month-1]);

                if(plan == 4){
                    Datos.setValue(Float.parseFloat(query[7]),"Datos de Waze",months[month-1]);
                } else if(plan == 5){
                    Datos.setValue(Float.parseFloat(query[8]),"Minutos internacionales",months[month-1]);
                    Datos.setValue(Float.parseFloat(query[9]), "Datos para compartir", months[month-1]);
                }

                JFreeChart Graphic = ChartFactory.createBarChart("Consumo mensual Linea: "+ query[12]+ " Cliente: "+infoPlan[1]+"\nPlan tipo: "+plan+" Mes: "+months[month-1],
                        "Tipo consumo", "Consumo", Datos,
                        PlotOrientation.HORIZONTAL, true, true, false);
                ChartPanel Panel = new ChartPanel(Graphic);
                return Panel;
            } else {
                DefaultCategoryDataset Datos = new DefaultCategoryDataset();
                String monthsShow = "";
                String line="";
                for(int j=0; j<queryV.size(); j++){
                    String[] query = queryV.get(j);
                    line = query[12];
                    int month = Integer.parseInt(query[13].substring(5,7));
                    monthsShow += months[month-1]+" ";
                    Datos.setValue(Float.parseFloat(query[1]), "Datos", months[month-1]);
                    Datos.setValue(Float.parseFloat(query[2]), "Minutos", months[month-1]);
                    Datos.setValue(Float.parseFloat(query[3]), "Mensajes", months[month-1]);
                    Datos.setValue(Float.parseFloat(query[4]), "Datos de Whatsapp", months[month-1]);
                    Datos.setValue(Float.parseFloat(query[5]), "Minutos de Whatsapp", months[month-1]);
                    Datos.setValue(Float.parseFloat(query[6]), "Datos de Facebook", months[month-1]);
                    if (plan == 4) {
                        Datos.setValue(Float.parseFloat(query[7]), "Datos de Waze", months[month-1]);
                    } else if (plan == 5) {
                        Datos.setValue(Float.parseFloat(query[8]), "Minutos internacionales", months[month-1]);
                        Datos.setValue(Float.parseFloat(query[9]), "Datos para compartir", months[month-1]);
                    }
                }

                JFreeChart Graphic = ChartFactory.createBarChart("Consumo mensual Linea: " + line + " Cliente: " + infoPlan[1] + "\nPlan tipo: " + plan + " Mes: " + monthsShow,
                        "Tipo consumo", "Consumo", Datos,
                        PlotOrientation.HORIZONTAL, true, true, false);
                ChartPanel Panel = new ChartPanel(Graphic);
                return Panel;
            }
        }
    }

