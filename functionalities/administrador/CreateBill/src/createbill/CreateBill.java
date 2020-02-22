/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createbill;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.*;
/**
 *
 * @author Diego Andrés Bonilla
 */
public class CreateBill {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CreateBill obj = new CreateBill();
        obj.WriteBill();
    }
    
    public void WriteBill(){
        try{
            FileOutputStream file = new FileOutputStream("bill.pdf");
            Document doc = new Document();
            PdfWriter writer = PdfWriter.getInstance(doc, file);
            Image logotype= Image.getInstance("C:/Users/Diego Andrés Bonilla/Desktop/Univalle/Sexto Semestre/Desarrollo de Software I/Proyecto/assets/images/logotype.png");
            doc.open();
            logotype.setAlignment(Element.ALIGN_LEFT);
            logotype.scaleAbsolute(70,70);
            doc.add(logotype);
            
            PdfContentByte pb = writer.getDirectContent();
            
            BaseFont bf =   BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            pb.setFontAndSize(bf,12);
            pb.beginText();
            // X, Y de abajo hacia arriba e izquierda a derecha
            pb.setTextMatrix(100,790);
            pb.showText("Telefonía");
            pb.setTextMatrix(110,780);
            pb.showText("    Raja");
            pb.setTextMatrix(20, 730);
            pb.showText("......................................................................................."
                    + "..............................................................................");
            doc.add(new Paragraph("Cliente: xxxxxxxxxxxx"));
            doc.add(new Paragraph("Dirección: xxxxxxxxxxxx"));
            doc.add(new Paragraph("Nit o cédula: xxxxxxxxxxxx"));
            doc.add(new Paragraph("Celular: xxxxxxxxxxxx                 Cliente No: xxxxx"));
            doc.add(new Paragraph("Fecha expedición: xxxxxxxxxxxx"));
            doc.add(new Paragraph("Factura de venta No: xxxxxxxxxxxx"));
            doc.add( Chunk.NEWLINE );
            Paragraph table = new Paragraph();
            PdfPTable tabla = new PdfPTable(5);
            tabla.addCell(new PdfPCell(new Phrase("Factura mes: ")));
            tabla.addCell(new PdfPCell(new Phrase("xxxxxxx")));
            PdfPCell cell = new PdfPCell(new Phrase("        "));
            cell.setBorder(Rectangle.NO_BORDER);
            tabla.addCell(cell);
            tabla.addCell(new PdfPCell(new Phrase("Número para pagos: ")));
            tabla.addCell(new PdfPCell(new Phrase("xxxxxxx")));
            tabla.setWidthPercentage(90);
            table.add(tabla);
            table.setIndentationLeft(-32);
            doc.add(table);
            doc.add(Chunk.NEWLINE);
            table = new Paragraph();
            tabla = new PdfPTable(5);
            tabla.addCell(new PdfPCell(new Phrase("Factura límite de pago: ")));
            tabla.addCell(new PdfPCell(new Phrase("xxxxxxx")));
            cell = new PdfPCell(new Phrase("        "));
            cell.setBorder(Rectangle.NO_BORDER);
            tabla.addCell(cell);
            tabla.addCell(new PdfPCell(new Phrase("TOTAL A PAGAR: ")));
            tabla.addCell(new PdfPCell(new Phrase("xxxxxxx")));
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
            //Aquí debería ir el gráfico
            //Aquí lo textual
            //Investigar el uso de código de barras o qr
            
         
            pb.endText();
            
            doc.close();
            
        } catch(Exception e){
            
        }
        /*File f;
        FileWriter file;
        BufferedWriter br;
        PrintWriter pw;
        try{
        f=new File("bill.pdf");
        file=new FileWriter(f);
        br = new BufferedWriter(file);
        pw = new PrintWriter(br);
        for(int i=0; i<10; i++){
            pw.write("fila: "+i+"\n");
        }
        pw.append("final");
        pw.close();
        br.close();
        } catch(IOException e){
            System.out.println("Can't do this");
        }*/
    }
    
}
