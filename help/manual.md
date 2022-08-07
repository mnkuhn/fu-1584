%Bedienungsanleitung

## Inhaltsverzeichnis

* [Allgemein][]
*  [Die grafische Benutzeroberfläche][]
*  [Die Zeichenfläche][]
*  [Darstellung der Elemente][]
*  [Bearbeiten der Punktmenge][]
*  [Ändern der Darstellung][]
*  [Die Menüleiste][]
*  [Das Datei-Menü][]
*  [Das Bearbeiten-Menü][]
*  [Das Ansicht-Menü][]
*  [Das Hilfe-Menü][]
*  [Die Statusleiste][]

## Allgemein
Dieses Programm berechnet die konvexe Hülle einer Menge von Punkten. Punkte können aus einer Datei [geladen](#datei-öffnen) werden, durch eine [Zufallsfunktion](#zufallspunkte-einfügen) erzeugt, oder von Hand auf der grafischen Benutzeroberfläche [eingetragen](#punkt-einfügen) werden.
Neben der [konvexen Hülle](#konvexe-hülle) kann das Programm den [Durchmesser](#durchmesser), das [grösste Viereck](#grösstes-viereck) der Punktmenge und eine [Animation](#animation) von einem Tangentenpaar anzeigen.
Das Programm verfügt über eine [Undo-](#befehl-rückgängig-machen) und [Redo-Funktion](#rückgängig-gemachten-befehl-wiederherstellen).
Die [grafische Benutzeroberfläche](#die-grafische-benutzeroberfläche) bietet die Möglichkeit, die Darstellung zu [zentrieren](#darstellung-zentrieren), zu [verschieben](#zeichenfläche-verschieben) und durch einen Zoom zu [vergrössern](#ausschnitt-der-zeichenfläche-vergössern-oder-verkleinern) bzw. zu [verkleinern](#ausschnitt-der-zeichenfläche-vergössern-oder-verkleinern).

&nbsp;
&nbsp;

## Die grafische Benutzeroberfläche
Die grafische Benutzeroberfläche besteht aus der Menüleiste, der Zeichenfläche und der Statusleiste, so wie in der Abbildung dargestellt.
![GUI_allg.png](images/GUI_allg.png)

&nbsp;

### Die Zeichenfläche
Die Zeichenfläche stellt die Punktmenge, die konvexe Hülle, den Durchmesser und das grösste Viereck dar. Sie dient ebenfalls zur [Bearbeitung der Punktmenge](#bearbeiten-der-punktmenge) durch Benutzereingaben.

#### Darstellung der Elemente

##### Punkte
Punkte werden schwarz gefüllte Kreise dargestellt.

##### Konvexe Hülle
Die Punkte der konvexen Hülle werden durch eine rote Linie verbunden.

##### Durchmesser
Der Durchmesser wird durch eine blaue Linie angezeigt.

##### Grösstes Viereck
Das grösste Viereck wird in einem matten grün dargestellt.

##### Animation
Während die Animation läuft, werden die beiden Punkte des Antipodenpaares durch eine schwarze Linie verbunden. Die an den Punkten des Antipodenpaares anliegenden Tangenten werden schwarz dargestellt. Das zum jeweiligen Antipodenpaar gehörende grösste Viereck wird in einem kräftigen grün eingezeichnet.
![Animation](images/Animation.png)

&nbsp;

#### Bearbeiten der Punktmenge

##### Punkt einfügen
Bewegen Sie den Mauszeiger auf der Zeichenfläche auf die gewünschte Stelle. In der Statusleiste werden nach dem Label mit der Anschrift "Maus: " die Koordinaten der Maus angezeigt. Sie können den Bereich, in welchen Sie einen Punkt eintragen können, durch [verschieben](#zeichenfläche-verschieben) oder [Zoomen](#ausschnitt-der-zeichenfläche-vergössern-oder-verkleinern) verändern. Um den Punkt einzutragen, drücken Sie die linke Maustaste.

##### Punkt löschen
Wählen Sie den Punkt, indem sie den Mauszeiger auf bzw. in die Nähe des zu löschenden Punktes bewegen. Sobald der Punkt gewählt ist, wird er von einem schwarzen Quadrat, welches den Punkt umgibt, markiert. Die Koordinaten des gewählten Punktes werden in der Statusleiste nach dem Label "Gewählter Punkt: " angezeigt. Drücken Sie nun die rechte Maustaste, um den Punkt zu löschen.

##### Punkt verschieben
Bewegen Sie den Mauszeiger auf bzw. in die Nähe des Punktes, welchen Sie verschieben möchten. Sobald der Punkt gewählt ist, wird er von einem schwarzen Quadrat, welches den Punkt umgibt, markiert. Drücken Sie nun `Alt` und die linke Maustaste in dieser Reihenfolge. Nun können Sie den gewählten Punkt verschieben. Bewegen Sie den Punkt an die gewünschte Stelle und lassen Sie dann die Taste `Alt` und die linke Maustaste los (die Reihenfolge spielt keine Rolle). Die Koordinaten des gewählten Punktes werden vor und während dem Verschieben in der Statusleiste nach dem Label "Gewählter Punkt: " angezeigt.
Befindet sich an der Stelle, auf welche der gewählte Punkt verschoben wird schon ein Punkt, so wird dieser Punkt gelöscht. Die Anzahl der Punkte - angezeigt nach dem Label "Anzahl Punkte: " - reduziert sich dann um 1. Wird der Befehl [rückgängig gemacht](#befehl-rückgängig-machen), so wird der Punkt an seiner ursprünglichen Stelle eingetragen und ein allfälliger gelöschter Punkt wird der  wieder hinzugefügt.

##### Zufallspunkte einfügen
Sie können 10, 50, 100, 500 oder 1000 Zufallspunkte in den sichtbaren Bereich der Zeichenfläche einfügen. Wählen Sie *Bearbeiten* und dann den entsprechenden Eintrag.

&nbsp;

#### Ändern der Darstellung

##### Zeichenfläche verschieben
Drücken Sie `Ctrl` und die linke Maustaste in dieser Reihenfolge. Nun können Sie die Zeichenfläche durch Bewegen der Maus verschieben.

##### Ausschnitt der Zeichenfläche vergössern oder verkleinern
Drücken Sie `Ctrl`. Nun können Sie durch Drehen des Mausrades je nach Drehrichtung hinein- bzw. hinauszoomen.

&nbsp;
&nbsp;

### Die Menüleiste

#### Das Datei-Menü

##### Neue Zeichenfläche erzeugen
Wählen Sie *Datei* → *Neu*. Falls Änderungen an der Punktmenge vorgenommen wurden, werden Sie zuvor gefragt, ob die bestehende Punktmenge in einer Datei gespeichert werden soll.

##### Datei öffnen
Wählen Sie *Datei* → *Öffnen*. Nun können Sie die gewünschte Datei auswählen und öffnen. Falls Änderungen an der Punktmenge vorgenommen wurden, werden Sie zuvor gefragt, ob die bestehende Punktmenge in einer Datei gespeichert werden soll. Die Punktmenge der Datei wird nach dem öffnen zentriert dargestellt, falls es intern nicht zu einem Überlauf kommen kann. Ansonsten wird die Punktmenge nicht zentriert dargestellt.

##### Datei speichern
Falls Sie die Punktmenge in einer neuen Datei speichern möchten, wählen Sie *Datei*  →  *Speichern unter*. Nun können Sie einen Dateinamen eingeben und die Punktmenge unter diesem Dateinamen in einer Datei ablegen. Wählen Sie *Datei* →  *Speichern*, falls Sie zuvor die Punktmenge mit *Datei* → *Öffnen* geladen haben und die geänderte Punktmenge in der schon geöffneten Datei abgespeichert werden soll.

##### Programm Beenden
Wählen Sie *Datei* → *Beenden*. Das Programm wird beendet. Falls Änderungen an der Punktmenge vorgenommen wurden, werden Sie zuvor gefragt, ob die bestehende Punktmenge in einer Datei gespeichert werden soll.
Sie können das Programm ebenfalls beenden, indem sie auf das Kreuz in der oberen rechten Ecke des Hauptfensters klicken.

&nbsp;

#### Das Bearbeiten-Menü

##### Befehl rückgängig machen
Wählen Sie *Bearbeiten*  → *Rückgängig*. Der entsprechende Menü-Eintrag wird matt dargestellt, falls es keine Befehle gibt, die Rückgängig gemacht werden können.

##### Rückgängig gemachten Befehl wiederherstellen
Wählen Sie *Bearbeiten*  → *Wiederherstellen*. Der entsprechende Menü-Eintrag wird matt dargestellt, falls es keine Befehle gibt, die wiederhergestellt werden können.

##### Zufällig erzeugte Punkte einfügen
Sie können 10, 50, 100, 500 oder 1000 Punkte mit Hilfe einer Zufallsfunktion erzeugen. Diese Funktion sorgt dafür, dass die Punkte in den sichtbaren Bereich der Zeichenfläche eingetragen werden. Wählen Sie dazu im Menü *Bearbeiten* den entsprechenden Eintrag. Falls es der sichtbare Bereich der Zeichenfläche nicht zulässt, dass eine gewisse Anzahl an zufällig erzeugten Punkten eingetragen wird, weil er zu klein ist, so werden keine Punkte eingetragen.

&nbsp;

#### Das Ansicht-Menü

##### Konturpolygon anzeigen
Setzen Sie das Häkchen bei *Ansicht* → *Konturpolygon*.

##### Durchmesser anzeigen
Setzen Sie das Häkchen bei *Ansicht* → *Durchmesser*.

##### Grösstes Viereck anzeigen
Setzen Sie das Häkchen bei *Ansicht* → *Grösstes Viereck*.

##### Grösstes Dreieck anzeigen
Setzen Sie das Häkchen bei *Ansicht* → *Grösstes Dreieck*.

##### Animation anzeigen
Setzen Sie das Häkchen bei *Ansicht* → *Animation*.

##### Darstellung zentrieren
Wählen Sie *Ansicht* -> *Zentrieren*. Die Darstellung wird nur zentriert, falls es intern nicht zu einem Überlauf kommen kann.

&nbsp;

#### Das Hilfe-Menü

##### Anzeigen der Bedienungsanleitung
Wählen *Hilfe* → *Anleitung*. Die Bedienungsanleitung wird im Webbrowser angezeigt.

&nbsp;
&nbsp;

### Die Statusleiste

##### Anzahl der Punkte in der Punktmenge
Hinter dem Label "Anzahl Punkte: " wird die Anzahl der Punkte in der Punktmenge angezeigt.

##### Koordinaten des Mauszeigers
Falls sich der Mauszeiger über dem sichtbaren Bereich der Zeichenfläche befindet, werden seine Koordinaten nach dem Label "Maus: " angezeigt. Die x- und die y-Koordinate sind durch einen vertikalen Strich getrennt.

##### Koordinaten des gewählten Punktes
Befindet sich ein oder befinden sich mehrere Punkte innerhalb einer gewissen Distanz zum Mauszeiger, so wird der zum Mauszeiger nächstgelegene Punkt ausgewählt. Die Koordinaten des ausgewählten Punktes werden nach dem Label "Gewählter Punkt: " angezeigt. Die x- und die y-Koordinate sind durch einen vertikalen Strich getrennt. Der gewählte Punkt selber wird durch ein ihn umgebendes schwarzes Quadrat markiert.
