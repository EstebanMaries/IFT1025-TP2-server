# IFT1025-TP2-server

## Groupe
- Esteban Maries 20235999
- Herve Ngisse 20204609

## Fonctionnement
### 1.
Les jars ```server.jar``` et ```client-simple.jar``` marche en faisant l'appel :

```
java -jar server.jar
java -jar client-simple.jar
```

Cependant, pour faire marcher ```ClientFX.jar```, veuillez utiliser la commande suivante :

```
$libPath = Resolve-Path lib
java --module-path $libPath --add-modules=javafx.controls -jar ClientFX.jar
```
Il faut être dans le dossier zip pour faire marcher les jars.

### 2.
Il est nécessaire d'avoir un dossier data contenant cours.txt (préférable de garder le fichier déjà présent) et inscription.txt dans le dossier zip.

### 3.
Nous avons utilisé ```JDK-19``` pour faire ce TP.


## [Démo Vidéo 1:00](https://udemontreal-my.sharepoint.com/:v:/g/personal/herve_ngisse_umontreal_ca/EYCDXLahxw1KjvSQ0Ya5thsBZFwZ0DdmBvojIYsc3U3Y6Q?e=MUaHxr)

### [Lien GitHub](https://github.com/EstebanMaries/IFT1025-TP2-server)