package com.example.usuario.agendaapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by Usuario on 10/05/2016.
 */
public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Object[] mensagens = (Object[]) bundle.get("pdus");
        byte[] mensagem = (byte[])mensagens[0];
        SmsMessage msg = SmsMessage.createFromPdu(mensagem);
        String telefone = msg.getDisplayOriginatingAddress();

        ContatoDAO dao = new ContatoDAO(context);

        if (dao.isContato(telefone)){
//            MediaPlayer player = MediaPlayer.create(context, R.raw.msg); //Player com o som de alerta - Arquivo de audio não importado!
//            player.start();
        }
    }
}
