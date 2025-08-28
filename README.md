# Hexas BOT
Un bot multi-fonction pour mes usages personnels comprenant : 
- un Crawler de site marchamps pour trouver les prix des composants qui m'intérresse
- un Crawler pour humble bundle pour recuperer les pack de jeu
- un bot discord pour la notification

# Installation 
Le bot s'install dans un serveur tomcat avec une base de donnée mysql (mariadb).

## BDD
Dans votre mariadb, crer une BDD **hexas**, importer les fichiers structure.sql et view.sql du projet HexasBotDbConnector.

## Configuration tomcat

A completer y a un truc special à faire avec un clone du repo à un endroit particulier du tomcat pour permettre l'execution des script de scan

## Déployement tomcat 
Completer votre profile maven avec les variable suivante : 
- hexas.mysql.host : ip de votre serveur mariadb
- hexas.mysql.user / hexas.mysql.pass : user et mot de pass du compte pour la bdd hexas

ajouter également un serveur dans voter profile maven 
~~~xml
		<server>
			<id>ID-SERVER</id>
			<username>user-avec-droit-admin-sur-tomca</username>
			<password>pass-qui-va-avec</password>
		</server>
~~~

Dans le projet HexasBotFront modififier le plugin de déployement : 

~~~xml
			<plugin>
				<groupId>org.apache.tomcat.maven</groupId>
				<artifactId>tomcat7-maven-plugin</artifactId>
				<configuration>
					<url>http://HOST-SERVEUR:PORT-SERVER/manager/text</url>
					<server>ID-SERVER</server>
					<path>/</path>
					<skipTests>true</skipTests>
				</configuration>
			</plugin>
~~~

Puis normalement lancer le script `deploy.sh`
