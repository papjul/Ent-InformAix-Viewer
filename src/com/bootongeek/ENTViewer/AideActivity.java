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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;


public class AideActivity extends Activity {
	
	private final String messageAide =	
			"<html>" +
				"<body>" +
					"<div style=\"text-align: center; font-weight: bold\">" +
						"Aide, trucs & astuces" +
					"</div>" +
					"<p>" +
						"Bienvenue dans la page d'aide de l'application Ent InformAix Viewer." +
						"Vous trouverez ici de l'aide mais aussi des informations<br />" +
					"</p>" +
					"<ul>" +
						"<li>" +
							"<div>" +
								"<strong>1. Licence</strong><br />" +
								"L'application Ent InformAix Viewer est sous licence " +
								"<a href=\"http://www.gnu.org/licenses/gpl.html\">GNU GPL</a>," +
								"c'est &agrave; dire que le code-source est OpenSource, modifiable &agrave; votre guise mais il doit rest&eacute; " +
								"OpenSource. Consultez le site de la <a href=\"http://www.fsf.org\">F.S.F</a> pour " +
								"plus d'informations." +
							"</div>" +
						"</li>" +
						"<hr /><li>" +
							"<div>" +
								"<strong>2. But</strong><br />" +
								"Ent InformAix Viewer a pour but principal de faciliter la consultation de l'emploi du temps g&eacute;n&eacute;r&eacute; par ADE" +
								" sur le site de l'ENT." +
							"</div>" +
						"</li>" +
						"<hr /><li>" +
							"<div>" +
								"<strong>3. Premi&egrave;re utilisation</strong><br />" +
								"Lors du premier d&eacute;marrage de l'application il est conseill&eacute; de v&eacute;rifier les param&egrave;tres de " +
								"l'application. Pour cela faire [Menu > Pr&eacute;f&eacute;rences], puis modifiez groupe et sous-groupe." +
								"Revenir sur la fen&egrave;tre principal pour actualiser." +
							"</div>" +
						"</li>" +
						"<hr /><li>" +
							"<div>" +
								"<strong>4. Param&egrave;tres</strong><br />" +
								"Il y a quelque param&egrave;tres pour bien utiliser l'application." +
								"<ul>" +
									"<li>" +
										"<strong>Groupe</strong> : Le groupe est la partie de la promotion dans laquelle vous &ecirc;tes. " +
										"C'est &eacute;galement dans ce menu que vous choisissez votre ann&eacute;e" +
									"</li>" +
									"<br/><li>" +
										"<strong>Sous-groupe</strong> : Le groupe est divis&eacute; en deux (A et B) choisissez si vous ne voulez " +
										"afficher que votre sous-groupe, ou bien les deux." +
									"</li>" +
									"<br /><li>" +
										"<strong>Semaine d&eacute;part</strong> : Le planning d'une nouvelle ann&eacute;e scolaire ne d&eacute;marre pas toujours " +
										"&agrave; la m&ecirc;me semaine tous les ans. Il faut donc modifier ce nombre pour obtenir la bonne semaine " +
										"courante. Pour la connaitre remontez le plus t&ocirc;t possible dans l'ENT." +
									"</li>" +
									"<br /><li>" +
										"<strong>Code d'indentification</strong> : Lors de votre connexion &agrave; l'ENT, il vous est g&eacute;n&eacute;r&eacute; un " +
										"identifiant (diff&eacute;rent de votre num&eacute;ro &eacute;tudiant) qui permet la g&eacute;n&eacute;ration du planning. " +
										"Or de temps &agrave; autre ce code d'identication change, il faut donc le changer sur l'application aussi.<br />" +
										"<span style=\"font-style: italic; color: orange;\">Un syst&egrave;me automatique sera s&ucirc;rement mis en place dans " +
										"les mises &agrave; jour future</span>"  +
									"</li>" +
									"<br /><li>" +
										"<strong>Taille automatique</strong> : Quand cette option est coch&eacute;e l'application choisie automatique " +
										"la meilleur r&eacute;solution d'image. Si vous la d&eacute;coch&eacute;e vous forcez la taille du planning " +
										"&agrave; la taille de votre &eacute;cran.<br /><strong>ATTENTION</strong> : Si votre &eacute;cran " +
										"est inf&eacute;rieure &agrave; la 800x600 il est fort probable que le planning n'apparaisse pas !" +
									"</li>" +
									"<br /><li>" +
										"<strong>Lundi - Dimanche</strong> : Jour de la semaine qui appara&icirc;tront sur le planning." +
									"</li>" +
								"</ul>" +
							"</div>" +
						"</li>" +
						"<hr /><li>" +
							"<div>" +
								"<strong>5. Navigation</strong><br />" +
								"Il n'y a que deux sens de navigation dans l'application, revenir dans le temps ou avancer " +
								"dans le temps. Pour cela il y a deux fl&egrave;ches sur l'&eacute;cran principal. " +
								"La fl&egrave;che vers la gauche recule d'une semaine; la fl&egrave;che vers la droite avance d'une semaine.<br />" +
								"Le chiffre sous les fl&egrave;ches repr&eacute;sentent le d&eacute;calage de semaine entre votre position et " +
								"la semaine en cours." +
							"</div>" +
						"</li>" +
						"<hr /><li>" +
							"<strong>6. Divers</strong><br />" +
							"Application d&eacute;velopp&eacute;e par <strong>Jonathan BAUDIN</strong> le 22 oct. 2012.<br />" +
							"Pour tout contact : <a href=\"mailto:contact@bootongeek.com\">contact@bootongeek.com</a>" +
						"</li>" +
					"</ul>" +
				"</body>" +
			"</html>";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_aide);
		
		WebView msgAide = (WebView)findViewById(R.id.webViewAide);
		msgAide.loadData(messageAide, "text/html", null);
		
		Button btn = (Button)findViewById(R.id.btnQuitAide);
		btn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				finish();
			}
		});
	}
}
