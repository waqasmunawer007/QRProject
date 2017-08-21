package com.qr.qrattendance.pdf_handler;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ScrollView;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by waqar on 8/21/2017.
 */

public class PDF_Manger{
        static Image image;
        private static String FILE = "mnt/sdcard/invoice.pdf";
        public static Bitmap getBitmapFromView(View view) {
            //Define a bitmap with the same size as the view
            int totalWidth=((ScrollView)view).getChildAt(0).getWidth();
            int totalHeight=((ScrollView)view).getChildAt(0).getHeight();
            Bitmap bitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
            //Bind a canvas to it
            Canvas canvas = new Canvas(bitmap);
            view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            //Get the view's background
            Drawable bgDrawable =view.getBackground();
            if (bgDrawable!=null)
                //has background drawable, then draw it on the canvas
                bgDrawable.draw(canvas);
            else
                //does not have background drawable, then draw white background on the canvas
                canvas.drawColor(Color.WHITE);
            // draw the view on the canvas
            view.draw(canvas);
            //return the bitmap
            return bitmap;
        }


        public  void makeDocument(Bitmap screen){
            try
            {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                screen.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                image = Image.getInstance(stream.toByteArray());
                image.setAbsolutePosition(0, 0);
                Document document = new Document(image);
                PdfWriter.getInstance(document, new FileOutputStream(FILE));
                document.open();
                document.add(image);
                document.close();
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
        }




}
