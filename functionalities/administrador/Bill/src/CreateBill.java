import com.google.zxing.WriterException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class CreateBill {
    public static void main(String[] args) {
        CreateBill obj = new CreateBill();
        obj.WriteBill();
    }

    public void WriteBill() {
        try {
        FileOutputStream file = new FileOutputStream("bill.pdf");
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
        doc.add(new Paragraph("Cliente: xxxxxxxxxxxx"));
        doc.add(new Paragraph("Dirección: xxxxxxxxxxxx"));
        doc.add(new Paragraph("Nit o cédula: xxxxxxxxxxxx"));
        doc.add(new Paragraph("Celular: xxxxxxxxxxxx                 Cliente No: xxxxx"));
        doc.add(new Paragraph("Fecha expedición: xxxxxxxxxxxx"));
        doc.add(new Paragraph("Factura de venta No: xxxxxxxxxxxx"));
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
        cell = new PdfPCell(new Phrase("xxxxxxx"));
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
        cell = new PdfPCell(new Phrase("xxxxxxx"));
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
        cell = new PdfPCell(new Phrase("xxxxxxx"));
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
        cell = new PdfPCell(new Phrase("xxxxxxx"));
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
                + "o la suspensión del servicio, su servicio es del plan xxxxx cuyo cobro por mora es de xxxxx. Si el pago ya fue realizado "
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
        //Aquí van los gráficos
        this.Graphic("Consumo mensual minutos", 1);
        Image graphic = Image.getInstance("functionalities/administrador/Bill/src/assets/images/consumoMinutos.png");
        graphic.setAlignment(Element.ALIGN_LEFT);
        graphic.scaleAbsolute(200, 150);
        doc.add(graphic);
        this.Graphic("Consumo mensual internet", 2);
        graphic = Image.getInstance("functionalities/administrador/Bill/src/assets/images/consumoInternet.png");
        graphic.setAbsolutePosition(310, 227);
        graphic.scaleAbsolute(200, 150);
        doc.add(graphic);
        this.Graphic("Consumo mensual mensajes", 3);
        graphic = Image.getInstance("functionalities/administrador/Bill/src/assets/images/consumoMensajes.png");
        graphic.setAlignment(Element.ALIGN_LEFT);
        graphic.scaleAbsolute(200, 150);
        doc.add(graphic);
        //Investigar el uso de código de barras o qr
        this.QRCode(123456789);
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
        cell = new PdfPCell(new Phrase("Mes/Tipo"));
        tabla.addCell(cell);
        cell = new PdfPCell(new Phrase("Minutos"));
        tabla.addCell(cell);
        cell = new PdfPCell(new Phrase("Internet"));
        tabla.addCell(cell);
        cell = new PdfPCell(new Phrase("Mensajes"));
        tabla.addCell(cell);
        cell = new PdfPCell(new Phrase("Enero"));
        tabla.addCell(cell);
        cell = new PdfPCell(new Phrase("xxxxxxxx"));
        tabla.addCell(cell);
        cell = new PdfPCell(new Phrase("xxxxxxxx"));
        tabla.addCell(cell);
        cell = new PdfPCell(new Phrase("xxxxxxxx"));
        tabla.addCell(cell);
        cell = new PdfPCell(new Phrase("Febrero"));
        tabla.addCell(cell);
        cell = new PdfPCell(new Phrase("xxxxxxxx"));
        tabla.addCell(cell);
        cell = new PdfPCell(new Phrase("xxxxxxxx"));
        tabla.addCell(cell);
        cell = new PdfPCell(new Phrase("xxxxxxxx"));
        tabla.addCell(cell);
        cell = new PdfPCell(new Phrase("Marzo"));
        tabla.addCell(cell);
        cell = new PdfPCell(new Phrase("xxxxxxxx"));
        tabla.addCell(cell);
        cell = new PdfPCell(new Phrase("xxxxxxxx"));
        tabla.addCell(cell);
        cell = new PdfPCell(new Phrase("xxxxxxxx"));
        tabla.addCell(cell);
        tabla.setWidthPercentage(98);
        doc.add(tabla);
        doc.add(Chunk.NEWLINE);
        doc.add(new Paragraph("......................................................................................."
                + "....................................................................."));
        doc.add(new Paragraph("Desprenda esta parte para realizar el pago"));

        pb.endText();
        String StringDefault = "123456789";
        doc.addAuthor("Telefonía Raja");
        doc.addCreationDate();
        doc.addProducer();
        doc.addCreator("Telefonía Raja");
        doc.addTitle("Pago factura Telefonpia Raja Barcode");
        PdfContentByte cb = writer.getDirectContent();
        pb = writer.getDirectContent();

        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        pb.setFontAndSize(bf, 12);
        pb.beginText();
        pb.setTextMatrix(460, 500);
        logotype.scalePercent(10);
        logotype.setAbsolutePosition(515,567);
        pb.addImage(logotype);
        pb.endText();
        Barcode128 code128 = new Barcode128();
        code128.setCode(StringDefault.trim());
        code128.setCodeType(Barcode128.CODE128);
        Image code128Image = code128.createImageWithBarcode(cb, null, null);
        code128Image.setAbsolutePosition(40, 480);
        code128Image.scaleAbsolute(400, 85);
        doc.add(code128Image);
        this.QRCode(123456789);
        graphic = Image.getInstance("functionalities/administrador/Bill/src/assets/images/QrCode.png");
        graphic.setAbsolutePosition(460, 492);
        graphic.scaleAbsolute(100, 85);
        doc.add(graphic);

        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);
        tabla = new PdfPTable(4);
        cell = new PdfPCell(new Phrase("Numero cliente"));
        tabla.addCell(cell);
        cell = new PdfPCell(new Phrase("xxxxxxxxxxxxx"));
        tabla.addCell(cell);
        cell = new PdfPCell(new Phrase("Valor"));
        tabla.addCell(cell);
        cell = new PdfPCell(new Phrase("xxxxxxxxxxxxxx"));
        tabla.addCell(cell);
        tabla.setWidthPercentage(98);
        doc.add(tabla);

        doc.close();
        } catch (Exception e) {
            System.out.println("Something went wrong "+ e);
        }
    }

    public void Graphic(String tipo, int Idtipo) {
        CreateGraphic chart = new CreateGraphic(tipo, Idtipo);
    }

    public void QRCode(int numeroPagos) {
        try {
            CreateQr qr = new CreateQr(String.valueOf(numeroPagos), 350, 350, "functionalities/administrador/Bill/src/assets/images/QrCode.png");
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
    }
}
