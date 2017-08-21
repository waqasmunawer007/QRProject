package com.qr.qrattendance.pdf_handler;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

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
            //  Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
            Bitmap returnedBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),Bitmap.Config.ARGB_8888);
            //Bind a canvas to it
            Canvas canvas = new Canvas(returnedBitmap);
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
            return returnedBitmap;
        }


        public  void makeDocument(Bitmap screen){
            try
            {
                Document document = new Document();

                PdfWriter.getInstance(document, new FileOutputStream(FILE));
                document.open();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                screen.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                addImage(document,byteArray);
                document.close();
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
        }


        private static void addImage(Document document,byte[] byteArray)
        {
            try
            {
                image = Image.getInstance(byteArray);
               // image.setAbsolutePosition(0, 0);
            }
            catch (BadElementException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (MalformedURLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // image.scaleAbsolute(150f, 150f);
            try
            {
                document.add(image);
            } catch (DocumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

}
