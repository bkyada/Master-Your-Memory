package com.cs442.bkyada.memorygame;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by BHAI on 11/11/2016.
 */
public class SendMail extends AsyncTask<Void,Void,Void>{
    private Context context;
    private Session session;

    //Information to send email
    private String email;
    private String password;
    private String message;

    //Progressdialog to show while sending email
    private ProgressDialog progressDialog;

    //Class Constructor
    public SendMail(Context context, String email,String password){
        //Initializing variables
        this.context = context;
        this.email = email;
        this.password = password;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
        progressDialog = ProgressDialog.show(context,"Sending message","Please wait...",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismissing the progress dialog
        progressDialog.dismiss();
        //Showing a success message
        Toast.makeText(context,"Your password has sent to your registered mail id", Toast.LENGTH_LONG).show();
    }
    @Override
    protected Void doInBackground(Void... params) {
        Properties props = new Properties();

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //Creating a new session
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("playmemorygame@gmail.com","memorygamerohan");
                    }
                });

        try {

            MimeMessage mm = new MimeMessage(session);
            mm.setFrom(new InternetAddress("playmemorygame@gmail.com"));
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mm.setSubject("Password for memory game");
            //mm.setDescription("Welcome, \n\n Your password : "+ password+" \n\n Thanks. ");
            // Now set the actual message
            mm.setText("Welcome, \n\n Your password : "+ password+" \n\n Thanks. ");

            Transport.send(mm);

        } catch (MessagingException e) {
            Log.e("doInBackground", "HHHHH : " + e.getMessage() );
            e.printStackTrace();
        }
        return null;
    }
}
