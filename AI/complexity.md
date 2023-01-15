|Algoritmo     |  Temporale | Spaziale      | Ottimo |
|:------------:|:----------:|:-------------:|:------:|
| Depth first  | O($b^d$)   |  O($b\cdot d$)| NO     |
| Breadth first| O($b^d$)   | O($b^d$)      | SI     |
| ID           | O($b^d$)   | O($b\cdot d$) | SI     |
| IB           | O($b^d$)   | O($b^d$)      | NO     |
| Best First   | O($b^d$)   | O($b^d$)      | NO     |
| HC           | O($b^d$)    | O($b\cdot d$) | NO     |
| HC-SA        | O($b^d$)   | O($b\cdot d$) | NO     |
| $A^*$        | O($b^d$)   | O($b^d$)      | SI     |
| $IDA^*$      | O($b^d)    | O($b\cdot d$) | SI     |
---



|Concetto      |  $\in$         | Non-emptyness  | 
|:------------:|:--------------:|:--------------:|
| Core         |  ?             | $CO-NP$        |
| BS           |  $\Pi_p^2$     |    \           |
| Kernel       |  $\Delta_p^2$  |    \           |
| Nucleolo     |  $\Delta_p^2$  |    \           |
| SS           |   ? | ? |
| Shapley      |  $NP$          |                |
---

- il core può essere vuoto
- per lo SS:
  - $\forall S,T \subseteq N$ se S e T sono SS $\implies S \subsetneq T$
  - se il core è stabile, il core è l'unico SS
  - il core è contenuto nell'intersezione di tutti gli SS
- $C_g \subseteq K_g \subseteq B_g$
- BS e K sono sempre non vuoti, anche il Nucleolo
- Vuoti sono solo gli SS e Core


L'aumento della taglia da strips a SAT è dovuta al frame problem. Il fatto che ciò che non è indicato è falso in strips, risolve automaticamente il frame problem, mentre sussistono gli altri due


 Strips non è un linguaggio logico è orientato agli insiemi, i simboli logici non hanno una semantica, quel segnetto non è un not è solo un segnetto, questo per STRIPS è un vantaggio e anche uno svantaggio: la semantica è molto semplice, non devo tener conto di una semantica che potrebbe mandare in crash il sistema {50:00}, ma quando vado ad accoppiare strips con altri sistemi basati su logica ho un Impedance Mismatch. La comunicazione tra Strips e un database è difficoltosa.


 planning si lega a degug & fix). Abbiamo bisogno di tanta conoscenza.
Debug & fix: se trovo la strada chiusa quindi rilevo l’errore (piano scelto sulla base di info non complete) e quindi dobbiamo debuggare
Acquisizione incrementale di conoscenza: mentre eseguo il piano non mi baso solo sulla conoscenza di partenza, ma acquisisco conoscenza e questo mi porta eventualmente a modificare il piano, non solo ai fini del debug, ma anche ai fini della sua ottimizzazione



Ricostruzione delle caratteristiche 3D - distanze, posizione relativa, disambiguazione delle sovrapposizioni, orientamento delle regioni (anche relativo)