
{ tree(X,Y) : edge(X,Y) } = 1 :- node(X) .




% Nodes
node(1..6).

% (Directed) Edges
edge(1,(2;3)).  edge(2,(4;5)).  edge(3,(5;6)).





#show tree/20.