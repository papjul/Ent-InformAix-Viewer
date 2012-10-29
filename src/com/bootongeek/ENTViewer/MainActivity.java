/*
 * 	LICENSE
 * 
 * 	This file is part of Ent InformAix Viewer.
 *
 *  Ent InformAix Viewer is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Ent InformAix Viewer is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Ent InformAix Viewer.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.bootongeek.ENTViewer;

import java.util.Calendar;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/*
 * Modification :
 * v1.1:
 * 		- Apparition des boutons de navigation (Semaines précédentes / suivantes) et actualisation
 * 
 * v1.2:
 * 		- Ajout de l'option "Taille automatique" permettant le reglage de la taille du planning soit par le programme
 * 			soit TOUJOURS à la taille de l'écran de l'utilisateur
 * 		- Ajout du bouton "Annuler" lors du chargement '(via internet) du planning.
 * 		- Modification message dans ProgressDialog
 */

public class MainActivity extends Activity {

	private final int PREF_ID = 0;
	private final int ACTU_ID = 1;
	private final int QUIT_ID = 2;

	private final int LARGEUR_PLANNING_DEFAUT = 800;
	private final int LONGUEUR_PLANNING_DEFAUT = 600;
	
	private final String strInto = "<html><body>Bienvenue sur ENT InformAix Viewer !<br /><br />" + 
			"Si vous d&eacute;marrez cette application pour la premi&egrave;re fois, rendez-vous dans les " + 
			"pr&eacute;f&eacute;rences (Option -> Pr&eacute;f&eacute;rences).<br />Si l'emploi du temps n'apparait " +
			"pas et que vous avez une erreur : <ul><li>V&eacute;rifiez votre connexion internet</li><li>" +
			"Actualisez la page (Option -> Actualiser)</li></ul><br /><div style=\"text-align: center;\">" +
			"Logiciel cr&eacute;&eacute; par Jonathan BAUDIN<br /><a href=\"http://www.bootongeek.com/\">" + 
			"http://www.bootongeek.com/</a><br />Sous licence <a href=\"http://www.gnu.org/licenses/gpl.html\">GNU GPL</a>" +
			"</div><br /><span style=\"text-color: blue\">Trucs et astuces :</span>" +
			"<p>La <strong>Taille automatique</strong>" +
			"(dans les pr&eacute;f&eacute;rences) peut prendre deux valeurs:<ul><li>Activ&eacute;e : " +
			"Le programme choisie automatique la taille du planning.</li><li>D&eacute;sactiv&eacute;e : " +
			"La taille du planning sera forc&eacute;e &agrave; la taille de votre &eacute;cran.</li></ul><br />" +
			"La <strong>semaine en cours</strong> : <br />Le programme affiche la semaine en cours (&agrave; condition d'avoir " +
			"bien r&eacute;gl&eacute; la semaine de d&eacute;part du planning). Donc si vous regardez le planning un dimanche, " +
			"il sera afficher le planning de la semaine ACTUELLE donc pensez &agrave; vous d&eacute;placez d'une semaine ;-)" +
			"</p></body></html>";
	
	private int offsetWeek = 0, largFenetre = 0, longFenetre = 0;
	private SharedPreferences pref;
	private WebView wView;
	private ProgressDialog prgDialog;
	private ImageButton imgBtnSuiv, imgBtnPrec, imgBtnActu;
	private TextView txtBtnPrec, txtBtnSuiv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.activity_main);

		wView = (WebView) findViewById(R.id.webView);
		wView.loadData(strInto, "text/html", null);
		
		largFenetre = wView.getWidth();
		longFenetre = wView.getHeight();

		prgDialog = new ProgressDialog(this);
		prgDialog.setMessage("Patientez...");
		prgDialog.setCancelable(false);
		prgDialog.setButton(ProgressDialog.BUTTON_POSITIVE, "Annuler", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				wView.stopLoading();
				prgDialog.dismiss();
				
				Toast.makeText(prgDialog.getContext(), "Chargement annulé", Toast.LENGTH_SHORT).show();
			}
		});
		

		wView.setWebChromeClient(new WebChromeClient() {

			public void onProgressChanged(WebView view, int progress) {
				prgDialog.setMessage("Chargement du planning... " + String.valueOf(progress) +"%");

				if (progress >= 100) {
					prgDialog.dismiss();
				}
				
				etatBoutonFleche(true);
			}
		});

		wView.setWebViewClient(new WebViewClient() {

			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				prgDialog.dismiss();
				Toast.makeText(view.getContext(), "Erreur : " + description,
						Toast.LENGTH_LONG).show();
				
				etatBoutonFleche(false);
			}
		});
		
		imgBtnPrec = (ImageButton)findViewById(R.id.btnPrecedent);
		txtBtnPrec = (TextView)findViewById(R.id.labelPrecedent);
		
		imgBtnSuiv = (ImageButton)findViewById(R.id.btnSuivant);
		txtBtnSuiv = (TextView)findViewById(R.id.labelSuivant);
		
		imgBtnActu = (ImageButton)findViewById(R.id.btnActu);

		imgBtnPrec.getBackground().setAlpha(100);
		imgBtnSuiv.getBackground().setAlpha(100);
		imgBtnActu.getBackground().setAlpha(100);
		
		
		imgBtnPrec.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				int tmpWeek = Integer.parseInt(calculeNumeroSemaine()) + offsetWeek;
				if(tmpWeek > 0){
					--offsetWeek;
					actualiserWebView();
				}
			}
		});
		
		imgBtnSuiv.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				int tmpWeek = Integer.parseInt(calculeNumeroSemaine()) + offsetWeek;
				if(tmpWeek < 52){
					++offsetWeek;
					actualiserWebView();
				}
			}
		});
		
		imgBtnActu.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				actualiserWebView();
			}
		});
		

		etatBoutonFleche(false);
		appliquerValeurBoutonSemaine();
	}
	
	/*@Override
	public void onResume(){
		actualiserWebView();
	}*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, PREF_ID, Menu.NONE, "Préférences");
		menu.add(Menu.NONE, ACTU_ID, Menu.NONE, "Actualiser");
		menu.add(Menu.NONE, QUIT_ID, Menu.NONE, "Quitter");

		return (super.onCreateOptionsMenu(menu));
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case PREF_ID:
			startActivity(new Intent(this, EditPreference.class));
			return (true);

		case ACTU_ID:
			actualiserWebView();
			return (true);
			
		case QUIT_ID:
			System.exit(0);
			return(true);
		}

		return (super.onOptionsItemSelected(item));
	}

	private void actualiserWebView() {
		String tmp = "";

		tmp += "http://planning.univmed.fr/ade/imageEt?identifier=8ba834238410a5a92ecb1729024b7871&projectId=22";
		tmp += "&idPianoWeek=" + calculeNumeroSemaine();
		tmp += "&idPianoDay=" + genererPianoDay();
		tmp += "&idTree=" + genererIdTree();
		tmp += "&width=" + genererLargeur();
		tmp += "&height=" + genererLongueur();
		tmp += "&lunchName=REPAS";
		tmp += "&displayMode=1057855";
		tmp += "&showLoad=false";
		tmp += "&ttl=1350836224000";
		tmp += "&displayConfId=41";

		wView.loadUrl(tmp);

		prgDialog.show();
		appliquerValeurBoutonSemaine();
	}

	private String calculeNumeroSemaine() {
		pref = PreferenceManager.getDefaultSharedPreferences(this);

		Calendar cal = Calendar.getInstance();
		int currentWeek = cal.get(Calendar.WEEK_OF_YEAR);
		int beginWeek = Integer.parseInt(pref.getString("pref_sem", "0"));

		if (beginWeek <= currentWeek) {
			return (String.valueOf((currentWeek - beginWeek) + offsetWeek));
		} else {
			return (String.valueOf(((52 - beginWeek) + currentWeek) + offsetWeek));
		}
	}

	private String genererPianoDay() {
		String tmpPiano = "";
		pref = PreferenceManager.getDefaultSharedPreferences(this);

		if (pref.getBoolean("pref_check_lun", true)) {
			tmpPiano += "0";
		}

		if (pref.getBoolean("pref_check_mar", true)) {
			if (tmpPiano.length() > 0)
				tmpPiano += "%2C";

			tmpPiano += "1";
		}

		if (pref.getBoolean("pref_check_mer", true)) {
			if (tmpPiano.length() > 0)
				tmpPiano += "%2C";

			tmpPiano += "2";

		}

		if (pref.getBoolean("pref_check_jeu", true)) {
			if (tmpPiano.length() > 0)
				tmpPiano += "%2C";

			tmpPiano += "3";

		}

		if (pref.getBoolean("pref_check_ven", true)) {
			if (tmpPiano.length() > 0)
				tmpPiano += "%2C";

			tmpPiano += "4";
		}

		if (pref.getBoolean("pref_check_sam", false)) {
			if (tmpPiano.length() > 0)
				tmpPiano += "%2C";

			tmpPiano += "5";
		}

		if (pref.getBoolean("pref_check_dim", false)) {
			if (tmpPiano.length() > 0)
				tmpPiano += "%2C";

			tmpPiano += "6";
		}

		return tmpPiano;
	}

	private String genererIdTree() {
		String tmpTree = "";
		pref = PreferenceManager.getDefaultSharedPreferences(this);

		int grp = Integer.parseInt(pref.getString("pref_groupe", "0"));

		// On charge le tableau de value_groupe (array.xml) pour exclure les
		// groupe qui n'on qu'un seul group
		Resources res = new Resources(getAssets(), new DisplayMetrics(), null);
		String[] tabIntVal = res.getStringArray(R.array.value_groupe);

		//Condition des groupe exception (qui n'ont qu'un seul groupe)
		if (Integer.valueOf(tabIntVal[9]) == grp) { // LP
			return String.valueOf(grp);
		}

		switch (Integer.parseInt(pref.getString("pref_sous_groupe", "0"))) {

		case 0: // Sous groupe A
			tmpTree += String.valueOf(grp);
			break;

		case 1: // Sous groupe B
			tmpTree += String.valueOf(grp + 1);
			break;

		case 2: // Les deux sous groupes
			tmpTree += String.valueOf(grp) + "%2C" + String.valueOf(grp + 1);
			break;

		default:
			break;
		}

		return tmpTree;
	}
	
	private void etatBoutonFleche(boolean etat){
		imgBtnPrec.setEnabled(etat);
		imgBtnSuiv.setEnabled(etat);
	}
	
	private void appliquerValeurBoutonSemaine(){
		if(offsetWeek > 0){	//c-a-d On "avance" dans le temps
			txtBtnPrec.setText("0");
			txtBtnSuiv.setText(String.valueOf(offsetWeek));
		}
		else if(offsetWeek < 0){	//c-a-d On "recule" dans le temps
			txtBtnPrec.setText(String.valueOf(Math.abs(offsetWeek)));
			txtBtnSuiv.setText("0");
		}
		else{ //si offsetWeek == 0 : c-a-d La semaine courante
			txtBtnPrec.setText("0");
			txtBtnSuiv.setText("0");
		}
	}
	
	private String genererLargeur(){
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		
		if(pref.getBoolean("pref_affichage_auto", true)){	//L'utilisateur à activé la taille auto
			if(largFenetre <= LARGEUR_PLANNING_DEFAUT){
				return String.valueOf(LARGEUR_PLANNING_DEFAUT);
			}
			else{	//largFenetre > LARGEUR_PLANNING_DEFAUT
				return String.valueOf(largFenetre);
			}
		}
		else{	//L'utilisateur force l'affichage à la taille de l'écran
			return String.valueOf(largFenetre);
		}
	}
	
	private String genererLongueur(){
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		
		if(pref.getBoolean("pref_affichage_auto", true)){	//L'utilisateur à activé la taille auto
			if(longFenetre <= LONGUEUR_PLANNING_DEFAUT){
				return String.valueOf(LONGUEUR_PLANNING_DEFAUT);
			}
			else{	//longFenetre > LONG_PLANNING_DEFAUT
				return String.valueOf(longFenetre);
			}
		}
		else{	//L'utilisateur force l'affiche à la taille de l'écran
			return String.valueOf(longFenetre);
		}
	}
}
