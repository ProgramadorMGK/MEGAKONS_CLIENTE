package interfaces.PDF_Stream;

import android.os.AsyncTask;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RecibidorPDF_DocumentosTablaPromociones extends AsyncTask<String, Void, InputStream> {

    PDFView visorPDF;

    public RecibidorPDF_DocumentosTablaPromociones(PDFView visorPDF) {
        this.visorPDF = visorPDF;
    }

    @Override
    protected InputStream doInBackground(String... strings) {
        InputStream inputStream = null;
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == 200){
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            return null;
        }
        return inputStream;
    }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        visorPDF.fromStream(inputStream).load();
    }
}
