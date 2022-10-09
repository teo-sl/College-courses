


class Node:
    def __init__(self,id,value):
        self.id=id
        self.deep=0
        self.value=value
        self.sons=[]

    
    def set_father(self,father):
        self.deep = father.deep+1
    def set_sons(self,sons):
        self.sons=sons
        for s in sons:
            s.set_father(self)
    def get_value(self):
        return self.value

    def get_deep(self):
        return self.deep
    
    def get_sons(self):
        return self.sons
    def __str__(self):
        return "Id: "+str(self.id)+" . Deep "+str(self.deep)+" . Value: "+str(self.value)



def iterative_deepening(root):
    if root == None: 
        raise ValueError('Root cannot be None')
    c=1
    flag=True
    while True:
        L=[root]
        while L!=[]:
            n=L.pop()
            if n.get_value() == 0:
                return n
            else:
                if n.get_deep() < c:
                    if n.get_sons()==[] and L==[]:
                        flag=False
                    for nn in n.get_sons():
                        L.insert(0,nn)

        if not flag:
            return None
        c+=1

    




root=Node(0,1)
n1 = Node(1,1)
n2 = Node(2,1)
n3 = Node(3,1)
n4 = Node(4,1)
n5 = Node(5,1)
n6 = Node(6,1)
n7 = Node(7,1)

n8 = Node(8,1)
n9 = Node(9,0)
n10 = Node(10,1)

root.set_sons([n1,n4])
n1.set_sons([n2,n3])
n4.set_sons([n5,n6,n7])
n3.set_sons([n10])
n6.set_sons([n8])
n8.set_sons([n9])


print(iterative_deepening(root))