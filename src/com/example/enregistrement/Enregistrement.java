package com.example.enregistrement;

import java.io.IOException;
import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 *
 * @author Joubert
 *
 */
public class Enregistrement extends Activity implements OnClickListener {

	private static final String LOG_TAG = "AudioRecordTest";
	private static String isNomFichier = null;

	private MediaRecorder mediaEnregistreur = null;
	private MediaPlayer mediaEcouteur = null;

	private ImageButton imageButtonEnregistrer;
	private ImageButton imageButtonEcouter;
	private TextView textViewMessage;

	private boolean ibEnregistrement;
	private boolean ibEcoute;

	/**
	 *
	 */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_enregistrement);

		isNomFichier = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		isNomFichier += "/audiorecordtest.3gp";

		imageButtonEnregistrer = (ImageButton) findViewById(R.id.imageButtonEnregistrer);
		imageButtonEnregistrer.setOnClickListener(this);
		imageButtonEcouter = (ImageButton) findViewById(R.id.imageButtonEcouter);
		imageButtonEcouter.setOnClickListener(this);

		textViewMessage = (TextView) findViewById(R.id.textViewMessage);

		ibEnregistrement = false;
		ibEcoute = false;
	} // / onCreate

	@Override
	public void onPause() {
		super.onPause();

		if (mediaEnregistreur != null) {
			mediaEnregistreur.release();
			mediaEnregistreur = null;
		}

		if (mediaEcouteur != null) {
			mediaEcouteur.release();
			mediaEcouteur = null;
		}
	} // / onPause

	@Override
	public void onClick(View v) {
		//
		if (v == imageButtonEnregistrer) {
			if (ibEnregistrement) {
				textViewMessage.setText("Arrêt de l'enregistrement");
				imageButtonEnregistrer
						.setImageResource(android.R.drawable.ic_btn_speak_now);
				imageButtonEnregistrer.setBackgroundColor(Color.GREEN);
				arreterEnregistrement();
			} else {
				textViewMessage.setText("Enregistrement");
				imageButtonEnregistrer
						.setImageResource(android.R.drawable.stat_notify_call_mute);
				imageButtonEnregistrer.setBackgroundColor(Color.RED);
				demarrerEnregistrement();
			}
			ibEnregistrement = !ibEnregistrement;
		}

		if (v == imageButtonEcouter) {
			if (ibEcoute) {
				textViewMessage.setText("Arrêt écoute enregistrement");
				imageButtonEcouter
						.setImageResource(android.R.drawable.ic_media_play);
				imageButtonEcouter.setBackgroundColor(Color.GREEN);
				arreterEcoute();
			} else {
				textViewMessage.setText("Ecoute enregistrement");
				imageButtonEcouter
						.setImageResource(android.R.drawable.ic_media_pause);
				imageButtonEcouter.setBackgroundColor(Color.RED);
				demarrerEcoute();
			}
			ibEcoute = !ibEcoute;
		}
	} // / onClick

	/**
	 *
	 */
	private void demarrerEnregistrement() {
		mediaEnregistreur = new MediaRecorder();
		mediaEnregistreur.setAudioSource(MediaRecorder.AudioSource.MIC);
		mediaEnregistreur.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mediaEnregistreur.setOutputFile(isNomFichier);
		mediaEnregistreur.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		try {
			mediaEnregistreur.prepare();
		} catch (IOException e) {
			Log.e(LOG_TAG, "Echec de la préparation");
		}

		mediaEnregistreur.start();
	} // / demarrerEnregistrement

	/**
	 *
	 */
	private void arreterEnregistrement() {
		mediaEnregistreur.stop();
		mediaEnregistreur.release();
		mediaEnregistreur = null;
	} // / arreterEnregistrement

	/**
	 *
	 */
	private void demarrerEcoute() {
		mediaEcouteur = new MediaPlayer();
		try {
			mediaEcouteur.setDataSource(isNomFichier);
			mediaEcouteur.prepare();
			mediaEcouteur.start();
		} catch (IOException e) {
			Log.e(LOG_TAG, "Echec de la préparation");
		}
	} // / demarrerEcoute

	/**
	 *
	 */
	private void arreterEcoute() {
		mediaEcouteur.release();
		mediaEcouteur = null;

	} // / arreterEcoute

} // / EnregistrementVoix