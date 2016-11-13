package com.example.roberto.pdfcreate;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "pdf_test";

    @BindView(R.id.ftext) TextView file_text;
    @BindView(R.id.btnwrite) Button btn_write;
    @BindView(R.id.btnread) Button btn_read;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnwrite)
    public void writePdf() {
        write(FILE_NAME, file_text.getText().toString());
    }

    @OnClick(R.id.btnread)
    public void openPdf() {
        read(FILE_NAME);
    }


    private void write(String fname, String fcontent) {
        try {
            File file = getFile(fname);
            // If file does not exists, then create it
            if (!file.exists())
                file.createNewFile();

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));
            document.open();

            document.add(new Paragraph(fcontent));

            document.close();

            Log.d("Sucess", "Sucess");

        } catch (IOException | DocumentException e) { e.printStackTrace(); }
    }

    private void read(String fname) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);

        Uri uri = FileProvider.getUriForFile(this, "com.example.roberto.pdfcreate.files", getFile(fname));
        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(Intent.createChooser(intent, "Open with: "));
    }

    private File getFile(String fname) {
        File fpath = this.getApplicationContext().getFilesDir();
        File file = new File(fpath, fname + ".pdf");

        return file;
    }


}
