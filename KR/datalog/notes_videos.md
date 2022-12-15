# Indice

1. Introduction to easy answer set
2. Examples
   1. Longer Example
   2. Summary
3. Variables
   1. Examples with variables
4. Recursion
   1. Realistic cases
5. Recursion and Negation
6. Summary

Link al notebook

      https://hub.gke2.mybinder.org/user/potassco-asp-course-notebooks-ramtcgoe/notebooks/tutorial/introduction/introduction.ipynb

# 1. Introduction to easy answer set

Risolvere problemi in modo dichiarativo, dichiariamo come la soluzione dovrà essere.

Dalla soluzione otteniamo un answer set. La soluzione è ottenuta da un "solver". L'interpretazione dell'answer set ci da la soluzione del problema.

![Answer set programming](imgs/Schermata%202022-12-15%20alle%2016.53.29.png)

Un esempio è: vogliamo assegnare le lezioni alle aule, in modo tale da evitare conflitti trai corsi o coi professori. La soluzione sarà un assegnamento, una timetable per i corsi universitari.

Cosa è nello specifico un **logic program** e un **answer set**?

Outline:
- examples
- variables
- recursion
- recursion and negation


# 2. Examples

Gli atomi sono tipo le variabili. b :- a, sono a e b ecc... .

Gli answer set non sono altro che sottoinsiemi degli atomi.

I letterali sono atomi positivi o negati (e.g. a, not b ecc...)

- {a} **choice rule** possiamo aggiungere a in un AS, ci da la possibilità.
- b :- a. **normal rule** (se a è nell'AS deve esserci anche b)
- :- not b. **constraint rule**, elimina un AS se b non è presente


a :- b, b corpo, a testa


Un **programma** è un insieme di regole (come quelle viste prima).

Considerando il programma di prima, quali sono gli AS?

- 1 Partiamo da un insieme vuoto
- 2 applichiamo {a}. Abbiamo due opzioni, con o senza a
- 3.1 poiché a non è presente non dobbiamo aggiungere niente
- 3.2 per b :- a, poiché a è presente, ci deve essere anche b
- 4.1 applicando :- not b, poiché b non è presente l'AS non va bene
- 4.2 b è presente quindi l'AS sopravvive

{a,b} è l'AS restituito

![Sviluppo AS programma](imgs/Schermata%202022-12-15%20alle%2017.03.30.png)

Le regole sono state applicate in **ordine**, ma in che senso?

La choice non dipende da nulla, la seconda può essere applicata solo dopo che ho considerato tutte le regole che hanno a nella testa, cosicché a sia stato già sviluppato. Simile discorso per la b. Il che ci dice che la valutazione è stata effettuata in ordine. 

Per tale motivo, possiamo dire che quello ottenuto {a,b} è l'unico AS del programma. E l'AS corrisponde alla soluzione. 


Nel caso in cui nel programma non ci fosse :- not b, avremmo ottenuto due AS: $\empty$ e {a,b}



        %%clingo - 0
        {a}
        b :- a.
        :- not b


## 2.1 Longer example

{a}
b :- a.
{c} :- not a.
d :- not b, not c.
:- c, not a.

Non vi è un unico ordine, tutti sono equivalenti. 

![Sviluppo AS programma](imgs/Schermata%202022-12-15%20alle%2017.34.59.png)

AS è sinonimo di **stable set**.


Se in clingo usiamo un numero n diverso da zero, questo cercherà n AS, segnalandoci che potrebbero essercene più di n, eventualmente, con un +.


## 2.2 Summary

- Una choice crea nuovi AS
- una normal rule aggiunge elementi all'AS
- un constraint elimina AS


# 3. Variables

                n(1)
                {a(X)} :- n(X).
                b(X) :- a(X).
                :- n(X), not b(X).

Abbiamo **costanti**, e.g. 1, e **variabili**, e.g. X. 

La quantificazione è supposta universale ($\forall$).

I **termini** sono sia variabili che costanti.

I **predicati** sono i simboli a,n,b ecc... Ognuno ha un'_arità_, che è il numero di parametri che il predicato può avere.

Un atomo è un predicato con variabili o costanti. I letterali sono atomi positivi o negati.

Un **fatto** è una normal rule senza corpo e variabili. 

## 3.1 Examples with variables

![](imgs/Schermata%202022-12-15%20alle%2018.34.35.png)

![](imgs/Schermata%202022-12-15%20alle%2018.39.47.png)

Ciò che cambia, in pratica, è solo applicare le regole per ogni costante presente.


# 4. Recursion

                1) {a}
                2) b :- c.
                3) c :- a.
                4) a :- b.

Le regole non sono in ordine, nella quarta regola, a appare nella testa! 

(2) -> (3) -> (4) -> (2)

Vi è un ciclo. Non vi è modo di definire un ordine

Li consideriamo come una singola regola (2-3-4). Naturalmente, prima dobbiamo applicare (1).

![](imgs/Schermata%202022-12-15%20alle%2018.51.36.png)


Applichiamo il blocco di regole finché vi è un cambiamento (come un operatore di punto fisso).

Di seguito un altro esempio.

![](imgs/Schermata%202022-12-15%20alle%2018.54.28.png)

Che può essere semplificato.

![](imgs/Schermata%202022-12-15%20alle%2018.55.14.png)

## 4.1 Realistic cases

- Traveling:

                start(a).
                road(a,b). road(b,c). road(c,d). road(d,a).

                visit(X) :- start(X).

                visit(Y) :- road(X,Y), visit(X).

Praticamente, è il concetto di raggiungibilità su un grafo a partire da un nodo, indicato da start.

Per definire l'AS partiamo dai fatti.

Dopodiché passiamo alle altre regole. Le regole che definiscono visit sono ricorsive e devono essere applicate più volte, finché otteniamo dei cambiamenti.

Difatti, definiamo visit, per ogni nodo che è raggiungibile a partire da start.


- Numbers

                next(1,2). next(2,3). next(3,4). next(4,5). odd(1).

                even(N) :- odd(M), next(M,N).

                odd(N) :- even(M), next(M,N).

Anche in questo caso si parte dai fatti. even e odd sono ricorsivi, quindi vanno considerati come un blocco unico.


# 5. Recursion and Negation

                {a}
                b :- c.
                c :- not a.
                a :- not b.

Abbiamo un insieme di regole ricorsive, dove la dipendenza ricorsiva presenta una negazione. a compare negato nel corpo e positivo nella testa.

Non scriveremo programmi con questo tipo di caratteristiche. Possiamo sempre riscrivere un programma eliminando la negazione. 

Non è che non possiamo usare la negazione, basta che questa non occorra in relazione a un atomo che poi compare nella testa di una regola che sta nello stesso gruppo di regole ricorsive.

Segue un esempio per chiarire.

![Negazione permessa](imgs/Schermata%202022-12-15%20alle%2019.17.56.png)

# 6. Summary

![](imgs/Schermata%202022-12-15%20alle%2019.19.12.png)