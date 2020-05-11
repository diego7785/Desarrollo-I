import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;

public class CreateReport {
    public String writeReport(String[] info, String documentNumber, String[] customerInfo, String number) {
        File directorio = new File(".\\Reports\\");
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

            Image logotype = Image.getInstance("functionalities\\administrador\\Bill\\src\\assets\\images\\logotype.png");
            logotype.setAlignment(Element.ALIGN_LEFT);
            logotype.scaleAbsolute(70, 70);
            doc.add(logotype);

            Image avatar = Image.getInstance("avatar.png");
            avatar.setAlignment(Element.ALIGN_CENTER);
            avatar.scaleAbsolute(100, 100);
            doc.add(avatar);

            PdfContentByte pb = writer.getDirectContent();

            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            pb.setFontAndSize(bf, 12);
            pb.beginText();

            // X, Y de abajo hacia arriba e izquierda a derecha
            pb.setTextMatrix(200, 730);
            pb.showText("......................................................................................."
                    + "..............................................................................");
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

            return directorio.getPath();
        } catch (Exception e) {
            return e.toString();
        }
    }
}
