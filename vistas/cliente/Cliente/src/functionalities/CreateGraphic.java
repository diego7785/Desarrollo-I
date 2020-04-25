    package functionalities;

    import DB_Connection.DBConnection;
    import org.jfree.chart.ChartFactory;
    import org.jfree.chart.ChartPanel;
    import org.jfree.chart.JFreeChart;
    import org.jfree.chart.plot.PlotOrientation;
    import org.jfree.data.category.DefaultCategoryDataset;

    import java.util.Vector;


    public class CreateGraphic {

        private DBConnection connection = new DBConnection("", "", "", "", "", "");


        public  ChartPanel Createchart(Vector<String[]> infoV, String[] infoPlan, int tipo) {
            int plan = Integer.parseInt(infoPlan[0]);
            String months[]={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio",
                    "Agosto","Septiembre","Octubre","Noviembre","Diciembre"};

            String[] info = infoV.get(0);

            float data_consumption = Float.parseFloat(info[1]);
            int minutes_consumption = Integer.parseInt(info[2]);
            int sms_consumption = Integer.parseInt(info[3]);
            float data_wpp = Float.parseFloat(info[4]);
            int minutes_wpp = Integer.parseInt(info[5]);
            float data_fb = Float.parseFloat(info[6]);
            float data_waze = Float.parseFloat(info[7]);
            int minutes_international = Integer.parseInt(info[8]);
            float data_shared = Float.parseFloat(info[9]);

            Object[] resultO = connection.read_DB("SELECT minutes, dataplan, messages, data_wpp, minutes_wpp, data_fb, data_waze, minutes_international, data_shared " +
                    "FROM Plan,Lines WHERE lines.number='"+info[13]+"' AND planID = id; ");
            Vector<String[]> result = (Vector<String[]>) resultO[1];
            String[] newResult = result.get(0);

            if(Integer.parseInt(info[12]) != 0){
                data_consumption = 2 * Integer.parseInt(newResult[1])-data_consumption;
                minutes_consumption = 2 * Integer.parseInt(newResult[0])-minutes_consumption;
                sms_consumption = 2 * Integer.parseInt(newResult[2])-sms_consumption;
                data_wpp = 2 * Integer.parseInt(newResult[3])-data_wpp;
                minutes_wpp = 2 * Integer.parseInt(newResult[4])-minutes_wpp;
                data_fb = 2 * Integer.parseInt(newResult[5])-data_fb;
                data_waze = 2 * Integer.parseInt(newResult[6])-data_waze;
                minutes_international = 2 * Integer.parseInt(newResult[7])-minutes_international;
                data_shared = 2 * Integer.parseInt(newResult[8])-data_shared;
            } else{
                data_consumption = Integer.parseInt(newResult[1])-data_consumption;
                minutes_consumption = Integer.parseInt(newResult[0])-minutes_consumption;
                sms_consumption = Integer.parseInt(newResult[2])-sms_consumption;
                data_wpp = Integer.parseInt(newResult[3])-data_wpp;
                minutes_wpp = Integer.parseInt(newResult[4])-minutes_wpp;
                data_fb = Integer.parseInt(newResult[5])-data_fb;
                data_waze = Integer.parseInt(newResult[6])-data_waze;
                minutes_international = Integer.parseInt(newResult[7])-minutes_international;
                data_shared = Integer.parseInt(newResult[8])-data_shared;
            }

            if(tipo == 1){
                int month = Integer.parseInt(info[14].substring(5,7));
                DefaultCategoryDataset Datos = new DefaultCategoryDataset();
                Datos.setValue(data_consumption,"Datos",months[month-1]);
                Datos.setValue(minutes_consumption,"Minutos",months[month-1]);
                Datos.setValue(sms_consumption,"Mensajes",months[month-1]);
                Datos.setValue(data_wpp,"Datos de Whatsapp",months[month-1]);
                Datos.setValue(minutes_wpp,"Minutos de Whatsapp",months[month-1]);
                Datos.setValue(data_fb,"Datos de Facebook",months[month-1]);

                if(plan == 4){
                    Datos.setValue(data_waze,"Datos de Waze",months[month-1]);
                } else if(plan == 5){
                    Datos.setValue(minutes_international,"Minutos internacionales",months[month-1]);
                    Datos.setValue(data_shared, "Datos para compartir", months[month-1]);
                }

                JFreeChart Graphic = ChartFactory.createBarChart("Consumo mensual Linea: "+ info[13]+ "\n\tCliente: "+infoPlan[1]+"\n\tPlan tipo: "+plan+" Mes: "+months[month-1],
                        "Tipo consumo", "Consumo", Datos,
                        PlotOrientation.HORIZONTAL, true, true, false);
                ChartPanel Panel = new ChartPanel(Graphic);
                return Panel;
            } else {
                DefaultCategoryDataset Datos = new DefaultCategoryDataset();
                String monthsShow = "";
                String line="";
                for(int j=0; j<infoV.size(); j++){

                    info = infoV.get(j);

                    data_consumption = Float.parseFloat(info[1]);
                    minutes_consumption = Integer.parseInt(info[2]);
                    sms_consumption = Integer.parseInt(info[3]);
                    data_wpp = Float.parseFloat(info[4]);
                    minutes_wpp = Integer.parseInt(info[5]);
                    data_fb = Float.parseFloat(info[6]);
                    data_waze = Float.parseFloat(info[7]);
                    minutes_international = Integer.parseInt(info[8]);
                    data_shared = Float.parseFloat(info[9]);

                    if(Integer.parseInt(info[12]) != 0){
                        data_consumption = 2 * Integer.parseInt(newResult[1])-data_consumption;
                        minutes_consumption = 2 * Integer.parseInt(newResult[0])-minutes_consumption;
                        sms_consumption = 2 * Integer.parseInt(newResult[2])-sms_consumption;
                        data_wpp = 2 * Integer.parseInt(newResult[3])-data_wpp;
                        minutes_wpp = 2 * Integer.parseInt(newResult[4])-minutes_wpp;
                        data_fb = 2 * Integer.parseInt(newResult[5])-data_fb;
                        data_waze = 2 * Integer.parseInt(newResult[6])-data_waze;
                        minutes_international = 2 * Integer.parseInt(newResult[7])-minutes_international;
                        data_shared = 2 * Integer.parseInt(newResult[8])-data_shared;
                    } else{
                        data_consumption = Integer.parseInt(newResult[1])-data_consumption;
                        minutes_consumption = Integer.parseInt(newResult[0])-minutes_consumption;
                        sms_consumption = Integer.parseInt(newResult[2])-sms_consumption;
                        data_wpp = Integer.parseInt(newResult[3])-data_wpp;
                        minutes_wpp = Integer.parseInt(newResult[4])-minutes_wpp;
                        data_fb = Integer.parseInt(newResult[5])-data_fb;
                        data_waze = Integer.parseInt(newResult[6])-data_waze;
                        minutes_international = Integer.parseInt(newResult[7])-minutes_international;
                        data_shared = Integer.parseInt(newResult[8])-data_shared;
                    }

                    line = info[13];

                    int month = Integer.parseInt(info[14].substring(5,7));
                    monthsShow += months[month-1]+" ";
                    Datos.setValue(data_consumption, "Datos", months[month-1]);
                    Datos.setValue(minutes_consumption, "Minutos", months[month-1]);
                    Datos.setValue(sms_consumption, "Mensajes", months[month-1]);
                    Datos.setValue(data_wpp, "Datos de Whatsapp", months[month-1]);
                    Datos.setValue(minutes_wpp, "Minutos de Whatsapp", months[month-1]);
                    Datos.setValue(data_fb, "Datos de Facebook", months[month-1]);
                    if (plan == 4) {
                        Datos.setValue(data_waze, "Datos de Waze", months[month-1]);
                    } else if (plan == 5) {
                        Datos.setValue(minutes_international, "Minutos internacionales", months[month-1]);
                        Datos.setValue(data_shared, "Datos para compartir", months[month-1]);
                    }
                }

                JFreeChart Graphic = ChartFactory.createBarChart("Consumo mensual Linea: " + line + "\n\tCliente: " + infoPlan[1] + "\n\tPlan tipo: " + plan + " Mes: " + monthsShow,
                        "Tipo consumo", "Consumo", Datos,
                        PlotOrientation.HORIZONTAL, true, true, false);
                ChartPanel Panel = new ChartPanel(Graphic);
                return Panel;
            }
        }
    }

