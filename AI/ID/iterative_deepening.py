# define a node class to simulate the tree search
class Node():
    def __init__(self,is_goal,id,h):
        self.children = []
        self.goal = is_goal
        self.depth = 0
        self.id = id
        self.h=h

    def add_child(self,child):
        child.set_depth(self.depth+1)
        self.children.append(child)
    
    def add_children(self,children):
        for c in children:
            self.add_child(c)
        
    def get_children(self):
        return self.children
    def is_goal(self):
        return self.goal
    def get_depth(self):
        return self.depth
    def set_depth(self,depth):
        self.depth=depth
    def get_id(self):
        return self.id
    def get_heuristic(self):
        return self.get_depth()+self.h
    
'''
PSEUDO CODE
[iterative deepening with no goal check]

1) c = 1
2) L = <R>, flag = False
3) if L == <> and !flag: FAIL
4) if L == <>: c++; goTo(2)
5) n = L.pop()
6) if is_goal(n): return n+path(n)
7) for each n' in children(n):
        if depth(n') <= c:
            push(L,n')
        else:
            flag = True
8) goto(3)
'''
INFTY = float('+inf')

def iterative_deepening(root : Node):
    c = 1
    while True:
        L = [root]
        flag = False
        while True:
            if L == [] and not flag:
                return None
            if L == []:
                c += 1
                break
            n = L.pop()
            if n.is_goal():
                return n
            for x in n.get_children():
                if x.get_depth() <= c:
                    L.append(x)
                else:
                    flag = True


'''
IDA*

1) c=1
2) L=<R>, c'=+infty
3) if L==<> and c'=+infty: FAIL
4) if L==<>: c=c', goto(2)
5) n = pop(L)
6) if goal(n): return n+path(n)
7) for n' in children(n):
    if depth(n')<=c:
        ordered_insert(L,n')
    else:
        c' = min(c',h'(n')) 
        # h' è l'euristica con la profondità
8) goto(3)
'''

def ida_star(root : Node):
    c = 1
    while True:
        L = [root]
        c_prime = INFTY
        while True:
            if L == [] and c_prime == INFTY:
                return None
            if L == []:
                c = c_prime
                break    
            n = L.pop()
            if n.is_goal():
                return n
            for m in n.get_children():
                if m.get_depth()<=c:
                    L=ordered_insert(L,m)
                else:
                    c_prime = min(c_prime,m.get_depth())

def ordered_insert(L,n):
    L.append(n)
    return sorted(L,key=lambda x : x.get_heuristic())

def main():
    root = Node(False,0,3)
    n1 = Node(False,1,2)
    n2 = Node(False,2,100)
    n3 = Node(False,3,2)
    n4 = Node(False,4,0)

    root.add_child(n1)
    root.add_child(n2)

    n1.add_child(n3)

    n3.add_child(n4)

    result = ida_star(root)
    if result is None:
        print("No goal")
    else:
        print(result.get_id())

main()


'''
            root
        n1          n2
    n3  
n4
'''