# PostCutter backend

Ide o logickú časť k aplikácií **PostCutter**. Tá je súčasť bakalárskej práce.

# Autor
Patrik Ondriga (xondri08)
xondri08@stud.fit.vutbr.cz

# Abstrakt

Aplikácia je napísaná v programovacom jazyku *Java* a využíva knižnicu *OpenCV*. Účelom tohto projektu je automaticky navrhovať orezy snímok obrazovky z niektorej sociálnej siete. Po orezaní snímky by mal zostať samotný obrázok bez prostredia sociálnej siete. Druhou funkcionalitou je odstraňovanie určitej oblasti z obrázka.

# Aplikácia

## Spúšťanie

Táto aplikácia je prekladaná a spúšťaná pomocou **Ant**. 

- **ant** - Tento príkaz spúšťa grafické prostredie, ktoré slúžilo pri vývoji algoritmu hľadania vodorovných a zvislých hrán na snímke. Ďalej sa pomocou týchto čiar skladal návrh orezu. Pri spustení aplikácie týmto príkazom sa používajú obrázky, ktoré sa nachádzajú v priečinku "screenshots" a "inpainting_photo". **Tieto priečinky pri spustení nemôžu byť prázdne**:exclamation: 
- **ant tests** - Týmto príkazom sa spustia jednotkové a integračné testy.
- **ant profiling** - Po spustení tohto príkazu sa program spustí v režime monitorovania aplikácie. Program čaká na stlačenie klávesy *Enter*. Počas tohto čakania aplikácia umožňuje nájdenie procesu, na ktorom beží pomocou niektorej profilovacej aplikácie akou je napríklad **VisualVM**. Pri spustení tohto príkazu aplikácia pracuje s priečinkami "profiling_screenshots". **Tento priečinok pri spustení nemôže byť prázdny**:exclamation:
- **ant final** - Prostredníctvom tohto príkazu sa vygeneruje *jar* súbor pre použitie v mobilnej aplikácii **PostCutter**. Vygenerovaný súbor neobsahuje grafické prostredie používané počas vývoja. Nachádzajú sa v nej iba tie triedy, ktoré sú potrebné pre návrh orezu a odstránenie oblasti z obrázka.

 ## Hierarchia priečinkov

 - **build** - Obsahuje preložené triedy programu a po spustení *ant final* aj jar súbor.
 - **lib** - Obsahuje knižnice Junit a OpenCV.
 - **profiling** - Obsahuje triedu, ktorá spúšťa program v režime monitorovania.
 - **src** - Obsahuje triedy samotnej aplikácie.
 - **tests** - Obsahuje triedu pre spúšťanie testov a samotné testy.

## Grafické prostredie

Pri používaní grafického prostredia sa pomocou tlačidiel na spodnej časti dajú prepínať obrázky a z toho tretie tlačidlo prepína zobrazenú funkcionalitu aplikácie. Na pravej časti sa zase dá prepínať medzi jednotlivými hranovými funkciami, ale to iba v režime zobrazenia výsledkov hranových funkcií, ktorá je ako prvá funkcionalita, ktorá sa zobrazuje po spustení aplikácie.

## Prevzatý kód

V triede *PostCutter.java* sa nachádza metóda *mat2BufferedImage*. Táto metóda je prevzatá z internetového zdroja [How to convert OpenCV Mat object to BufferedImage object using Java?](https://www.tutorialspoint.com/how-to-convert-opencv-mat-object-to-bufferedimage-object-using-java). Autorom tohto kódu je **Krishna Kasyap**.

## Licencia

Tento program je poskytovaný pod licenciou [GPL-3.0](/LICENSE).
