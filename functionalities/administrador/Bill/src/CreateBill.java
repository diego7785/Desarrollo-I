import com.google.zxing.WriterException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.w3c.dom.css.Rect;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

public class CreateBill {
    public void WriteBill(String[] info, String actualDate, String cutDate, String month) {
        String address = "";
        if (info[29] == null) {
            address = info[27] + " " + info[28];
        } else {
            address = info[29];
        }

        try {
            FileOutputStream file = new FileOutputStream("bill" + info[12] + ".pdf");
            Document doc = new Document();
            PdfWriter writer = PdfWriter.getInstance(doc, file);
            Image logotype = Image.getInstance("functionalities/administrador/Bill/src/assets/images/logotype.png");
            doc.open();
            logotype.setAlignment(Element.ALIGN_LEFT);
            logotype.scaleAbsolute(70, 70);
            doc.add(logotype);

            PdfContentByte pb = writer.getDirectContent();

            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            pb.setFontAndSize(bf, 12);
            pb.beginText();
            // X, Y de abajo hacia arriba e izquierda a derecha
            pb.setTextMatrix(20, 730);
            pb.showText("......................................................................................."
                    + "..............................................................................");
            doc.add(new Paragraph("Cliente: " + info[25]));
            doc.add(new Paragraph("Dirección: " + address));
            doc.add(new Paragraph("Nit o cédula: " + info[18]));
            doc.add(new Paragraph("Celular: " + info[17] + "                   Tipo plan: " + info[20]));
            doc.add(new Paragraph("Fecha expedición: " + actualDate));
            doc.add(new Paragraph("Factura de venta No: " + info[0]));
            pb.setFontAndSize(bf, 7);
            pb.setTextMatrix(337, 90);
            pb.showText("Escanee este código para realizar el pago");
            pb.setFontAndSize(bf, 12);
            doc.add(Chunk.NEWLINE);
            Paragraph table = new Paragraph();
            PdfPTable tabla = new PdfPTable(5);
            pb.setColorFill(BaseColor.WHITE);
            PdfPCell cell = new PdfPCell(new Phrase("Factura mes: "));
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setBorderColor(BaseColor.WHITE);
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase(month));
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setBorderColor(BaseColor.WHITE);
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase("        "));
            cell.setBorder(Rectangle.NO_BORDER);
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase("Número para pagos: "));
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setBorderColor(BaseColor.WHITE);
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase(info[15]));
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setBorderColor(BaseColor.WHITE);
            tabla.addCell(cell);
            tabla.setWidthPercentage(90);
            table.add(tabla);
            table.setIndentationLeft(-32);
            doc.add(table);
            doc.add(Chunk.NEWLINE);
            table = new Paragraph();
            tabla = new PdfPTable(5);
            cell = new PdfPCell(new Phrase("Límite de pago: "));
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setBorderColor(BaseColor.WHITE);
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase(cutDate));
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setBorderColor(BaseColor.WHITE);
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase("        "));
            cell.setBorder(Rectangle.NO_BORDER);
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase("TOTAL A PAGAR: "));
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setBorderColor(BaseColor.WHITE);
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase(info[11]));
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setBorderColor(BaseColor.WHITE);
            tabla.addCell(cell);
            tabla.setWidthPercentage(90);
            table.add(tabla);
            table.setIndentationLeft(-32);
            doc.add(table);
            doc.add(Chunk.NEWLINE);
            table = new Paragraph();
            tabla = new PdfPTable(1);
            cell = new PdfPCell(new Phrase("Estimado cliente, le recomendamos estar al tiempo con sus pagos para evitar el pago por mora "
                    + "o la suspensión del servicio, su servicio es del plan " + info[20] + " cuyo cobro por mora es de " + (Integer.parseInt(info[11]) * 0.01) + ". Si el pago ya fue realizado "
                    + "haga caso omiso."));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            tabla.addCell(cell);
            tabla.setWidthPercentage(98);
            table.setIndentationLeft(-12);
            table.add(tabla);
            doc.add(table);
            doc.add(Chunk.NEWLINE);
            table = new Paragraph();
            tabla = new PdfPTable(1);
            cell = new PdfPCell(new Phrase("RESUMEN DE LA CUENTA"));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setHorizontalAlignment(1);
            tabla.addCell(cell);
            tabla.setWidthPercentage(98);
            table.setIndentationLeft(-12);
            table.add(tabla);
            doc.add(table);
            pb.setColorFill(BaseColor.BLACK);
            //Graphs here
            int plan = Integer.parseInt(info[20]);
            this.Graphic(info, plan);
            Image graphic = Image.getInstance("functionalities/administrador/Bill/src/assets/images/consumoComun.png");
            graphic.setAlignment(Element.ALIGN_LEFT);
            graphic.scaleAbsolute(200, 150);
            doc.add(graphic);
            if(plan == 4){
                graphic = Image.getInstance("functionalities/administrador/Bill/src/assets/images/consumoAdicionalesplus.png");
                graphic.setAbsolutePosition(310, 227);
                graphic.scaleAbsolute(200, 150);
                doc.add(graphic);
            } else if(plan == 5){
                graphic = Image.getInstance("functionalities/administrador/Bill/src/assets/images/consumoAdicionalesplus.png");
                graphic.setAbsolutePosition(310, 227);
                graphic.scaleAbsolute(200, 150);
                doc.add(graphic);
                graphic = Image.getInstance("functionalities/administrador/Bill/src/assets/images/consumointernacionalycompartido.png");
                graphic.setAlignment(Element.ALIGN_LEFT);
                graphic.scaleAbsolute(200, 150);
                doc.add(graphic);
            } else{
                graphic = Image.getInstance("functionalities/administrador/Bill/src/assets/images/consumoAdicionales.png");
                graphic.setAbsolutePosition(310, 227);
                graphic.scaleAbsolute(200, 150);
                doc.add(graphic);
            }

            //Investigar el uso de código de barras o qr
            this.QRCode(info[15]);
            graphic = Image.getInstance("functionalities/administrador/Bill/src/assets/images/QrCode.png");
            graphic.setAbsolutePosition(310, 77);
            graphic.scaleAbsolute(200, 150);
            doc.add(graphic);

            pb.endText();
            doc.newPage();
            doc.add(logotype);
            pb = writer.getDirectContent();

            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            pb.setFontAndSize(bf, 12);
            pb.beginText();
            pb.setTextMatrix(20, 720);
            pb.showText("......................................................................................."
                    + "..............................................................................");
            doc.add(Chunk.NEWLINE);
            pb.setTextMatrix(30, 700);
            pb.showText("Tabla de consumo: ");
            tabla = new PdfPTable(4);
            cell = new PdfPCell(new Phrase("Consumo datos"));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase(info[1]));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase("Consumo minutos"));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase(info[2]));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase("Consumo mensajes"));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase(info[3]));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase("Consumo datos Whatsapp"));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase(info[4]));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase("Consumo datos Facebook"));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase(info[6]));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase("Consumo minutos Whatsapp"));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase(info[5]));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase("Consumo datos Waze"));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase(info[7]));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase("Consumo minutos internacionales"));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase(info[8]));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase("Consumo datos compartidos"));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase(info[9]));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase("        "));
            cell.setBorder(Rectangle.NO_BORDER);
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase("        "));
            cell.setBorder(Rectangle.NO_BORDER);
            tabla.addCell(cell);
            tabla.setWidthPercentage(98);
            doc.add(tabla);
            doc.add(Chunk.NEWLINE);
            doc.add(new Paragraph("......................................................................................."
                    + "....................................................................."));
            doc.add(new Paragraph("Desprenda esta parte para realizar el pago"));

            pb.endText();
            String StringDefault = info[15];
            doc.addAuthor("Telefonía Raja");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("Telefonía Raja");
            doc.addTitle("Pago factura Telefonía Raja Barcode");
            PdfContentByte cb = writer.getDirectContent();
            pb = writer.getDirectContent();

            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            pb.setFontAndSize(bf, 12);
            pb.beginText();
            pb.setTextMatrix(460, 502);
            logotype.scalePercent(10);
            logotype.setAbsolutePosition(515, 504);
            pb.addImage(logotype);
            pb.endText();
            Barcode128 code128 = new Barcode128();
            code128.setCode(StringDefault.trim());
            code128.setCodeType(Barcode128.CODE128);
            Image code128Image = code128.createImageWithBarcode(cb, null, null);
            code128Image.setAbsolutePosition(40, 420);
            code128Image.scaleAbsolute(400, 85);
            doc.add(code128Image);
            this.QRCode(info[15]);
            graphic = Image.getInstance("functionalities/administrador/Bill/src/assets/images/QrCode.png");
            graphic.setAbsolutePosition(460, 432);
            graphic.scaleAbsolute(100, 85);
            doc.add(graphic);

            doc.add(Chunk.NEWLINE);
            doc.add(Chunk.NEWLINE);
            doc.add(Chunk.NEWLINE);
            doc.add(Chunk.NEWLINE);
            doc.add(Chunk.NEWLINE);
            tabla = new PdfPTable(6);
            cell = new PdfPCell(new Phrase("Cliente"));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase(info[18]));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase("Linea"));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase(info[17]));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase("Valor"));
            tabla.addCell(cell);
            cell = new PdfPCell(new Phrase(info[11]));
            tabla.addCell(cell);
            tabla.setWidthPercentage(98);
            doc.add(tabla);

            doc.close();
        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        }
    }

    public void Graphic(String[] consume, int type) {
        CreateGraphic chart = new CreateGraphic(consume, type);
    }

    public void QRCode(String numeroPagos) {
        try {
            CreateQr qr = new CreateQr(numeroPagos, 350, 350, "functionalities/administrador/Bill/src/assets/images/QrCode.png");
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
    }
}
