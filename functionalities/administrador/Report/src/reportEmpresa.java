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
import java.util.Vector;

public class reportEmpresa {
    public void writeReport(String nit, String[] empresaInfo, Vector<String[]> linesInfo) {
        File directorio = new File(".\\Reports");
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            }
        }

        try {
            FileOutputStream file = new FileOutputStream(directorio.getPath()+"\\reports" + nit +".pdf");
            Document doc = new Document();

            PdfWriter writer = PdfWriter.getInstance(doc, file);
            doc.open();

            Image logotype = Image.getInstance("functionalities\\administrador\\Bill\\src\\assets\\images\\logotype.png");
            logotype.setAlignment(Element.ALIGN_LEFT);
            logotype.scaleAbsolute(70, 70);
            doc.add(logotype);

            Image avatar = Image.getInstance("functionalities\\administrador\\Report\\src\\avatarEmpresa.png");
            avatar.setAlignment(Element.ALIGN_CENTER);
            avatar.scaleAbsolute(100, 100);
            doc.add(avatar);

            PdfContentByte pb = writer.getDirectContent();

            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            pb.setFontAndSize(bf, 12);
            pb.beginText();

            // X, Y de abajo hacia arriba e izquierda a derecha
            pb.setTextMatrix(200, 730);

            doc.add(new Paragraph("Nombre: " + empresaInfo[0]));
            doc.add(new Paragraph("Cédula: " + nit));
            doc.add(new Paragraph("Email: " + empresaInfo[1]));
            doc.add(new Paragraph("Ciudad: " + empresaInfo[2]));

            pb.setFontAndSize(bf, 7);
            doc.add(Chunk.NEWLINE);

            Paragraph table = new Paragraph();
            PdfPTable tabla = new PdfPTable(1);

            PdfPCell cell = new PdfPCell(new Phrase("RESUMEN LINEAS ASOCIADAS"));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setHorizontalAlignment(1);

            tabla.addCell(cell);
            tabla.setWidthPercentage(98);
            table.setIndentationLeft(-12);
            table.add(tabla);

            tabla = new PdfPTable(5);

            cell = new PdfPCell(new Phrase("NUMERO"));
            cell.setBorder(Rectangle.NO_BORDER);
            pb.setColorFill(BaseColor.BLACK);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("PLAN"));
            cell.setBorder(Rectangle.NO_BORDER);
            pb.setColorFill(BaseColor.BLACK);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("ACTIVO"));
            cell.setBorder(Rectangle.NO_BORDER);
            pb.setColorFill(BaseColor.BLACK);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("PRECIO"));
            cell.setBorder(Rectangle.NO_BORDER);
            pb.setColorFill(BaseColor.BLACK);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("PAGADO"));
            cell.setBorder(Rectangle.NO_BORDER);
            pb.setColorFill(BaseColor.BLACK);
            tabla.addCell(cell);

            for (int i = 0; i < linesInfo.size(); i++) {
                cell = new PdfPCell(new Phrase(linesInfo.get(i)[0]));
                cell.setBorder(Rectangle.NO_BORDER);
                pb.setColorFill(BaseColor.BLACK);
                tabla.addCell(cell);

                cell = new PdfPCell(new Phrase(linesInfo.get(i)[1]));
                cell.setBorder(Rectangle.NO_BORDER);
                pb.setColorFill(BaseColor.BLACK);
                tabla.addCell(cell);

                String[] billInfo = linesInfo.get(i)[2].split("/");

                cell = new PdfPCell(new Phrase(billInfo[0]));
                cell.setBorder(Rectangle.NO_BORDER);
                pb.setColorFill(BaseColor.BLACK);
                tabla.addCell(cell);

                cell = new PdfPCell(new Phrase(billInfo[1]));
                cell.setBorder(Rectangle.NO_BORDER);
                pb.setColorFill(BaseColor.BLACK);
                tabla.addCell(cell);

                cell = new PdfPCell(new Phrase(billInfo[2]));
                cell.setBorder(Rectangle.NO_BORDER);
                pb.setColorFill(BaseColor.BLACK);
                tabla.addCell(cell);
            }
            table.add(tabla);
            doc.add(table);

            pb.endText();

            doc.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
