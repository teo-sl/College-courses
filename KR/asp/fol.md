”Ogni giocatore di tennis  ́e amico di almeno un giocatore di tennis che ha partecipato a ”Wimbledon”, solo se ha partecipato al ”Roland Garros”

($\forall x. tennis(x)\wedge partecipa(x,"Garros") \rightarrow (\exist y.tennis(y) \wedge x\ne y \wedge partecipa(y,"Wimbledon")))$



Almeno un collega di Luca non ha giocato almeno una partita di calcio allo stadio Lorenzon con un cugino di Luca”


$\exist x,z,y. collega(x,"Luca") \wedge partita(z) \wedge tenuta(z,"Lorenzon") \wedge cugino(y,"Luca") \wedge partecipa(y,z) \wedge \neg partecipa(x,z)$



”Ogni telecronista  ́e collega di almeno un telecronista che ha commentato una partita dell’ ”Inter”, solo se ha commentato una partita del ”Milan”


$\forall x. telecronista(x) \wedge (\exist p. partita(p) \wedge squadra(p,"Milan") \wedge telecronaca(x,p)) \rightarrow (\exist y. telecronista(y) \wedge x\neq y \wedge collega(x,y) \wedge (\exist p. partita(p) \wedge squadra(p,"Inter") \wedge telecronaca(x,p)))$


“Se Giuseppe e suo zio sono usciti insieme allora hanno almeno un amico in comune”.


