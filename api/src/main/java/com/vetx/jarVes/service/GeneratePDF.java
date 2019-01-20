package com.vetx.jarVes.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.vetx.jarVes.model.VoyEstimate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class GeneratePDF {
  public static ByteArrayInputStream voyEstimatePDF(VoyEstimate voyEstimate) {
    Document document = new Document();
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    try {

      PdfPTable table = new PdfPTable(4);
      table.setWidthPercentage(60);
      table.setWidths(new int[] {1, 3, 3, 3});

      Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

      PdfPCell hcellHeaders;

      hcellHeaders = new PdfPCell(new Phrase("Id", headFont));
      hcellHeaders.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(hcellHeaders);

      hcellHeaders = new PdfPCell(new Phrase("Name", headFont));
      hcellHeaders.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(hcellHeaders);

      hcellHeaders = new PdfPCell(new Phrase("Account", headFont));
      hcellHeaders.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(hcellHeaders);

      hcellHeaders = new PdfPCell(new Phrase("Voyage", headFont));
      hcellHeaders.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(hcellHeaders);

      PdfPCell cell;

      cell = new PdfPCell(new Phrase(voyEstimate.getId().toString()));
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(cell);

      cell = new PdfPCell(new Phrase(voyEstimate.getName()));
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(cell);

      cell = new PdfPCell(new Phrase(voyEstimate.getAccount()));
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(cell);

      cell = new PdfPCell(new Phrase(voyEstimate.getVoyage()));
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(cell);

      PdfWriter.getInstance(document, out);
      document.open();
      document.add(table);
      document.close();
    } catch (DocumentException ex) {

    }
    return new ByteArrayInputStream(out.toByteArray());
  }
}
