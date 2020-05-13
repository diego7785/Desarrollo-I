import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

public class listUsers {
    public void writeUsers(Vector<String[]> usersInfo) {
        File directorio = new File(".\\Lists");
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            }
        }

        try {
            FileOutputStream file = new FileOutputStream(directorio.getPath() + "\\listUsers.pdf");
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

            Paragraph table = new Paragraph();
            PdfPTable tabla = new PdfPTable(1);

            PdfPCell cell = new PdfPCell(new Phrase("Lista usuarios"));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setHorizontalAlignment(1);

            tabla.addCell(cell);
            tabla.setWidthPercentage(98);
            table.setIndentationLeft(-12);
            table.add(tabla);

            tabla = new PdfPTable(usersInfo.get(0).length);

            for (int i = 0; i < usersInfo.size(); i++) {
                for (int j = 0; j < usersInfo.get(i).length; j++) {
                    cell = new PdfPCell(new Phrase(usersInfo.get(i)[j]));
                    cell.setBorder(Rectangle.NO_BORDER);
                    pb.setColorFill(BaseColor.BLACK);
                    tabla.addCell(cell);
                }
            }

            table.add(tabla);
            doc.add(table);

            pb.endText();

            doc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}