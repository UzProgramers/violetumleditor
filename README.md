VIOLET UML EDITOR
======================
* Jakub Homlala
* 331 IDZ B
* jhomlala@gmail.com
* 2016


Co zostalo dodane:

*  Dodano notacje ball/socket do diagramu klas
*  Dodano notacje wejscia/wyjscia na zewnetrzne systemy w diagramie stanu
*  Dodano diagram rozlokowania
*  Dodano typowe grafiki do diagramu rozlokowania ( w stereotype node)
*  Dodano mozliwosc dodania wlasnych grafik w diagramie rozlokowania ( w stereotype node)

Testy:
Notacja ball i socket powtarza sie zarowno w digramie klas jak i w diagramie rozlokowania(klasy dla obu diagramow sa indentyczne) dlatego tez testy dla tych klas znajduja sie w jednym miejscu, a dokladnie w diagramie rozlokowania.
W diagramie rozlokowania dodano testy tylko dla komponentow pisanych przeze mnie. Nie dodawano testow komponentow skopiowanych z innych diagramow, np. package node.
Testy dodawania plikow za pomoca FilePropertyEditora znajduja sie w paczce test w violet-framework. W diagramie stanu dodano testy klas odpowiadajacych za wejscie/wyjscie na zewnetrzne systemy.  Testy nie zostaly dodane tylko do klasy FileProperty, poniewaz ta klasa to tylko settery i gettery. W pozostalych przypadkach testy byly tworzone w miare mozliwosci(nie kiedy problemy wynikaly z tego ze trzeba bylo testowac grafike).

































How to get the software
=======================

Here, you will only find source code. If you just want to get and use the software, go to sourceforge, download it and enjoy easy diagram's drawing! https://sourceforge.net/projects/violet/


How to compile Violet's source code
===================================

Violet is developped in Java and is packaged with Maven.
So, prerequisites are :
+ Java Development Kit 6 or greater (http://www.oracle.com/technetwork/java/javase/downloads/index.html)
+ Maven 2 or greater (http://maven.apache.org/)
 
Once you grabbed the source code (git pull), just run 'mvn clean package' from the root directory. This command will compile and package everything. 

As Violet project is composed of several sub-projects (plugins, products, etc...), it is organized as a main maven project (parent) with modules (children). Once everything is compiled and package, go to [module directory]/target to get the result of this packaging. For example : violetproduct-exe/target/violetumleditor-xxx.exe
 

How to contribute
=================

If you want to go further and contribute (even for small fixes), please read the developed guide from the website here : http://alexdp.free.fr/violetumleditor/page.php?id=en:developerguide

Once you think you have something great, you can create a git pull request. I will examine your request and contact you back.



Kind Regards,
Alex