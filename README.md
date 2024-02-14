# Energy System

## Rulare teste

Clasa Test#main
  * ruleaza solutia pe testele din checker/, comparand rezultatele cu cele de referinta
  * ruleaza checkstyle

Detalii despre teste: checker/README

Biblioteci necesare pentru implementare:
* Jackson Core 
* Jackson Databind 
* Jackson Annotations

Tutorial Jackson JSON: 
<https://ocw.cs.pub.ro/courses/poo-ca-cd/laboratoare/tutorial-json-jackson>

[Puteti sa folositi si alte biblioteci si sa editati aceasta sectiune]

## Implementare

### Entitati

Pachetul entities contine urmatoarele:
 - Interfata Entity, ce este implementata de clasele Consumer, Distributor,
   Producer (cele 3 entitati ale simularii);
 - Clasa Contract - contine toate informatiile cu privire la contractul
 dintre Un Distributor si un Consumer;  
 - Interfetele Observer si Observable, implementate de clasele Distributor,
   respectiv Producer -> ajuta la crearea Design Patter-ului de tip Observer;
 - EnergyType -> un enum cu toate tipurile de energii din joc;
 - Clasa MonthlyStat - contine informatii despre ceea ce trebuie sa contina un
   MonthlyState pentru Producer
 - EntityFactory - clasa de tip factory din care se creeaza un nou obiect de tip
   Entity.

Pachetul fileio contine doar clase care ajuta la citirea/afisarea datelor
  in format JSON.

Pachetul utils include o singura clasa:
- Constants - clasa care tine evidenta tuturor constantelor folosite de-a
  lungul programului.

Pachetul simulation este format din:
- Clasa Database - retine toate clasele care au facut parte din joc.
- Interfata EntityChanges, ce este implementata de clasele DistributorChanges
  si ProducerChanges si contine o metoda de update care seteaza schimbarile
  lunare ale celor doua entitati amintite anterior.
- MonthlyUpdate - retine toate informatiile despre un MonthlyUpdate (din input)
  si contine metode de realizare a acestor update-uri.
- Clasa Simulation - contine intreaga logica a programului. Mai precis, ea tine
  evidenta rundelor care se desfasoara si a entitatilor inca ramase in joc.
  Mai mult, majoritatea operatiilor ce implica mai multe tipuri de entitati se
  realizeaza in cadrul acestei clase

Pachetul strategies include:
- EnergyChoiceStrategyType - un enum cu toate tipurile de strategii
- 3 clase reprezentative pentru fiecare tip de strategie: GreenStrategy,
PriceStrategy, QuantityStrategy
- O interfata care este implementata de cele 3 clase (si care contine
  o metoda ce defineste strategia)
- StrategyFactory - clasa care creeaza o strategie particulara in functie
de inputul primit.


### Flow

Desi clasa Main este cea apelata de catre checker, ea nu serveste decat
citirii, afisarii si a initializarii Database-ului, respectiv al Simularii.

Astfel, clasa care controleaza logica programului este Simulation, care tine
evidenta tuturor entitatilor ramase in joc si ruleaza toate rundele.

O runda reprezinta totalitatea actiunilor care se petrec in decursul unei luni.
Pe langa rundele obisnuite, exista si o etapa de pregatire a jucatorilor numita
"Runda 0".

De asemenea, este important de mentionat faptul ca actiunile specifice unei
entitati au fost implementate in clasa reprezentativa lor. Pe langa asta,
pentru o mai buna coordonare a datelor, am ales sa includ in clasa Distributor
lista de Producers ce i-a fost asignata, iar in clasa Producer - lista de
Distributors pe care o are acesta.

In acelasi timp, fiecare Consumer are cate un Contract, in vreme ce fiecare
Distributor contine o lista de contracte pe care le are cu niste consumatori.

Strategiile distributorilor se aplica cu ajutorul metodei executeStrategy,
care, dupa ce strategia distributorului a sortat producatorii in mod
corespunzator, selecteaza producatorii din acea lista sortata pana cand
energia totala este cel putin cea de care are nevoie.

In continuare, voi descrie mai amanuntit flow-ul programului pornind
de la rundele jocului.

**Runda 0**
1. Toti distributorii inclusi in joc isi aleg, de la inceput, producatorii pe
baza strategiei ce le-a fost asignata in fisierele de input. Apoi, li se
calculeaza costul de productie, ce depinde de producatorii pe care ii are si
pregatesc ofertele pentru Consumatori. Dupa aceea, se alege cea mai buna oferta,
si se creeaza un contract pentru toti consumatorii. La final, consumatorii isi
primesc salariile, efectuaza platile impreuna cu distribuitorii, urmand ca 
toate entitatile care au dat faliment sa fie scoase din joc.

** Runda obisnuita**
Intr-o runda obisnuita, exista niste update-uri lunare care se efectueaza la
inceputul acesteia, ordinea fiind: update-uri pentru consumatori, distributori,
respectiv producatori (pentru update-urile producatorilor, distributorii sunt 
notificati pentru a-si alege din nou producatorii la finalul lunii). 
Apoi, se efectueaza majoritatea operatiilor despre care am amintit in runda 0:
calcularea ofertelor pentru consumatori, asignarea celei mai bune oferte de
la distributor (daca este cazul), primirea salariilor, plata
consumatorilor/distributorilor si eliminarea entitatilor care au dat faliment.
Dupa toate aceste operatii, toti distributorii care au fost notificati, isi
vor realege producatorii si isi vor recalcula costul de productie. La final,
se realizeaza un MonthlyStat pentru fiecare producator, unde se tine evidenta
tuturor distributorilor pe care ii au in acea luna.  
  
### Elemente de design OOP

* Incapsularea: Majoritatea operatiilor se bazeaza pe incapsulare, folosind,
de fiecare data cand am avut nevoie sa efectuez schimbari ale proprietatilor,
getteri si setteri. Cateva exemple de folosire a getterilor este acela cand am
avut nevoie sa aflu lista de contracte ale distributorilor. De asemenea, o
situatie cand am avut nevoie mare de setteri a fost atunci cand am realizat
MonthlyUpdate-urile pentru fiecare entitate. (situatia care include producatorii
nu numai ca imi realizeaza modificarile, dar imi si notifica toti distributorii
ca acestea s-au efectuat -> Design Pattern de tip Observer).

* Polimorfism -> exista doua zone din cod in care m-am folosit de polimorfism.
Prima dintre ele se afla in MonthlyUpdate, existand doua metode care
implementeaza metoda update, insa una este fara parametri, iar cealalta are la
intrare doua liste (una cu producatori, iar cealalta cu distributori). A doua
situatie este in clasa Distributor la metoda pay, una fiind suprascrisa pentru
ca Distributor implementeaza interfata Entity, iar cea de-a doua - efectueaza
o plata in gol (pentru ca Distributor nu plateste niciunei entitati speciale).

* Abstractizarea - folosita doar o singura data in cadrul clasei InputEntity
(pentru a usura citirea, era nevoie de mostenire sau de abstractizare in cazul
in care existau metode comune tuturor celor 3 clase de InputX).

* Mostenirea - Nu a fost folosita (in mod explicit) decat in cazul claselor
de tip InputX, care mostenesc InputEntity. Au fost, insa, implementate multe
interfete care ar fi putut, foarte bine, sa fie gandite ca niste clase
abstracte. Motivul pentru care nu am dorit sa decurg la a crea clase abstracte
este pentru ca nu existau decat 1-2 metode maxim de implementat si nici o
proprietate comuna. Astfel, interfetele pe care le-am creat sunt: 
  1. EntityChanges (care include o metoda de tip update(List<? extends Entity>)
     si este implementata de ProducerChanges, respectiv DistributorChanges;
  2. Observer, Observable - folosite pentru a realiza Design Pattern-ul Observer;
  3. Entity - folosita pentru a folosi Design Pattern-ul Factory pentru alegerea
  entitatilor.
  4. StrategyPriorities - folosita pentru realizarea Design Pattern-ului Strategy   
     
### Design patterns

Design Pattern-urile folosite sunt:
- Factory -> folosit de doua ori: o data pentru alegerea entitatilor si o data
pentru alegerea strategiilor.  
- Singleton -> folosit pentru clasele Simulation, Database si cele doua
Factory-uri.  
- Strategy -> folosit pentru a sorta o lista de producatori in functie de tipul
strategiei avute (Green, Price sau Quantity). Toate cele 3 implementeaza
interfata StrategyPriorities, iar aceasta metoda se aplica in clasa Distributor
in metoda executeStrategy.
- Observer -> folosit pentru a notifica toti distributorii in cazul in care un
Producer efectueaza un update in acea luna. Astfel, mi-am creat doua interfete:
una pentru Observer (Distributorii) si una pentru Observable (Producatorul); in
momentul in care un Producator isi schimba EnergyPerDistributor, toti
distributorii sunt notificati, setandu-si un "flag" ca fiind True astfel incat
la finalul lunii acestia sa-si reaplice strategia.
