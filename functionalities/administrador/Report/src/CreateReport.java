import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
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
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateReport {
    public String writeReport(String[] info, String documentNumber, String[] customerInfo, String number, String month, int plan, String[] price) {
        File directorio = new File("./Reports");
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            }
        }

        try {
            FileOutputStream file = new FileOutputStream(directorio.getPath()+"/reports" + documentNumber +".pdf");
            Document doc = new Document();

            PdfWriter writer = PdfWriter.getInstance(doc, file);
            doc.open();

            Image logotype = Image.getInstance("functionalities/administrador/Bill/src/assets/images/logotype.png");
            logotype.setAlignment(Element.ALIGN_LEFT);
            logotype.scaleAbsolute(70, 70);
            doc.add(logotype);

            Image avatar = Image.getInstance("functionalities/administrador/Report/src/avatar.png");
            avatar.setAlignment(Element.ALIGN_CENTER);
            avatar.scaleAbsolute(100, 100);
            doc.add(avatar);

            PdfContentByte pb = writer.getDirectContent();

            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            pb.setFontAndSize(bf, 12);
            pb.beginText();

            // X, Y de abajo hacia arriba e izquierda a derecha
            pb.setTextMatrix(200, 730);

            doc.add(new Paragraph("Nombre: " + customerInfo[0]));
            doc.add(new Paragraph("Cédula: " + documentNumber));
            doc.add(new Paragraph("Email: " + customerInfo[1]));
            doc.add(new Paragraph("Ciudad: " + customerInfo[2]));
            doc.add(new Paragraph("Celular: " + number));

            pb.setFontAndSize(bf, 7);
            doc.add(Chunk.NEWLINE);

            Paragraph table = new Paragraph();
            PdfPTable tabla = new PdfPTable(1);

            PdfPCell cell = new PdfPCell(new Phrase("RESUMEN DE LA CUENTA"));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setHorizontalAlignment(1);

            tabla.addCell(cell);
            tabla.setWidthPercentage(98);
            table.setIndentationLeft(-12);
            table.add(tabla);

            doc.add(table);
            pb.setColorFill(BaseColor.BLACK);

            table = new Paragraph();
            tabla = new PdfPTable(1);
            doc.add(Chunk.NEWLINE);

            cell = new PdfPCell(new Phrase(month));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(1);

            tabla.addCell(cell);
            tabla.setWidthPercentage(98);
            table.setIndentationLeft(-12);
            table.add(tabla);

            doc.add(table);
            pb.endText();

            CreateGraphic(info, plan, price);
            Image graphic = Image.getInstance("functionalities/administrador/Report/src/consumoComun.png");

            graphic.setAlignment(Element.ALIGN_LEFT);
            graphic.scaleAbsolute(200, 150);
            doc.add(graphic);

            switch (plan) {
                case 4: {
                    graphic = Image.getInstance("functionalities/administrador/Report/src/consumoAdicionalesplus.png");
                    graphic.setAbsolutePosition(310, 285);
                    graphic.scaleAbsolute(200, 150);
                    doc.add(graphic);

                    break;
                }

                case 5: {
                    graphic = Image.getInstance("functionalities/administrador/Report/src/consumoAdicionalesplus.png");
                    graphic.setAbsolutePosition(310, 285);
                    graphic.scaleAbsolute(200, 150);
                    doc.add(graphic);

                    graphic = Image.getInstance("functionalities/administrador/Report/src/consumointernacionalycompartido.png");
                    graphic.setAlignment(Element.ALIGN_LEFT);
                    graphic.scaleAbsolute(200, 150);
                    doc.add(graphic);
                    break;
                }

                default: {
                    graphic = Image.getInstance("functionalities/administrador/Report/src/consumoAdicionales.png");
                    graphic.setAbsolutePosition(310, 285);
                    graphic.scaleAbsolute(200, 150);
                    doc.add(graphic);

                    break;
                }
            }

            doc.close();

            return directorio.getPath();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e);
            return e.toString();
        }
    }

    public void CreateGraphic(String[] consume, int idPlan, String[] price) {
        DefaultCategoryDataset dataset = createDataset(consume, 1, 3, price);
        JFreeChart chart = createChart(dataset, "Consumo mensual comun", 1);

        switch (idPlan) {
            case 4: {
                dataset = createDataset(consume, 4, 7, price);
                JFreeChart chart2 = createChart(dataset, "Consumo mensual adicionales", 3);

                ChartPanel chartPanel = new ChartPanel(chart2);
                chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));

                break;
            }

            case 5: {
                dataset = createDataset(consume, 4, 7, price);
                JFreeChart chart2 = createChart(dataset, "Consumo mensual adicionales", 3);

                ChartPanel chartPanel = new ChartPanel(chart2);
                chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));

                dataset = createDataset(consume, 8, 9, price);
                JFreeChart chart3 = createChart(dataset, "Consumo mensual internacional y compartido", 4);

                chartPanel = new ChartPanel(chart3);
                chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));

                break;
            }

            default: {
                dataset = createDataset(consume, 4, 6, price);
                JFreeChart chart1 = createChart(dataset, "Consumo mensual adicionales", 2);

                ChartPanel chartPanel = new ChartPanel(chart1);
                chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));

                break;
            }
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
                switch (i) {
                    case 1: {
                        value = 2 * Float.parseFloat(price[2]) - Float.parseFloat(consume[1]);
                        break;
                    }

                    case 2: {
                        value = 2 * Float.parseFloat(price[1]) - Float.parseFloat(consume[2]);
                        break;
                    }

                    default: {
                        value = 2 * Float.parseFloat(price[i]) - Float.parseFloat(consume[i]);
                        break;
                    }
                }
            }
            switch (i) {
                case 1: {
                    value = Float.parseFloat(price[2]) - Float.parseFloat(consume[1]);
                    break;
                }

                case 2: {
                    value = Float.parseFloat(price[1]) - Float.parseFloat(consume[2]);
                    break;
                }

                default: {
                    value = Float.parseFloat(price[i]) - Float.parseFloat(consume[i]);
                    break;
                }
            }


            result.setValue(value,types[i-1],"");
        }

        return result;
    }

    private JFreeChart createChart(DefaultCategoryDataset dataset, String title, int tipoID) {
        String tipo;
        switch (tipoID) {
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

        JFreeChart chart = ChartFactory.createBarChart3D(title, "Tipo", "Consumo", dataset, // data
                PlotOrientation.VERTICAL, true, true, false);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis xAxis = (CategoryAxis) plot.getDomainAxis();

        xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        plot.setBackgroundAlpha(0.5f);
        try {
            ChartUtilities.saveChartAsPNG(new File("functionalities/administrador/Report/src/consumo" + tipo + ".png"), chart, 400, 300);
        } catch (IOException e) {
            System.out.println("Can't generate bill");
        }

        return chart;
    }
}
