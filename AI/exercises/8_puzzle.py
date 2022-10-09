import numpy as np
import bisect
from copy import copy, deepcopy

ref = {
    1 : (0,0),
    2 : (0,1),
    3 : (0,2),
    4 : (1,0),
    5 : (1,2),
    6 : (2,0),
    7 : (2,1),
    8 : (2,2)
}


def get_sons(node):
    ret=[]

    (i,j)=find_white(node)
    m,n = 3,3

    #LEFT
    if j>0:
        tmp = deepcopy(node)
        swap(tmp,(i,j),(i,j-1))
        ret.append(tmp)
    #RIGHT:
    if j<n-1:
        tmp = deepcopy(node)
        swap(tmp,(i,j),(i,j+1))
        ret.append(tmp)
    #UP
    if i>0:
        tmp = deepcopy(node)
        swap(tmp,(i,j),(i-1,j))
        ret.append(tmp)
    #DOWN
    if i<m-1:
        tmp = deepcopy(node)
        swap(tmp,(i,j),(i+1,j))
        ret.append(tmp)
    
    return ret
    
def swap(M,white,dst):
    i,j = white[0],white[1]
    k,r = dst[0],dst[1] 
    tmp = M[i][j]
    M[i][j]=M[k][r]
    M[k][r]=tmp

def find_white(M):
    m,n=3,3
    for i in range(0,m):
        for j in range(0,n):
            if M[i][j]==-1:
                return (i,j)

def get_values(sons):
    ret = []
    for s in sons:
        ret.append(get_manhattan(s))
    return ret

def get_manhattan(son):
    m,n=3,3
    ret = 0
    for i in range(0,m):
        for j in range(0,n):
            if son[i][j]==-1:
                continue
            x = son[i][j]
            (r,c) = ref[x]
            ret += abs(i-r)+abs(j-c)

    return ret
    
def goal(node):
    k=1
    m,n = 3,3
    for i in range(0,m):
        for j in range(0,n):
            if i==1 and j==1:
                continue
            if k!=node[i][j]:
                return False
            k+=1
    return True


def best_first(A):

    L=[A]
    while L!=[]:
        n = L.pop(0)
        if goal(n[0]):
            return n
        else:
            sons = get_sons(n[0])
            h = get_values(sons)
            depth = n[1]+1
            sons_ = []
            
            
            for i in range(0,len(sons)):
                sons_.append([sons[i],depth,h[i]+depth])
            for s in sons_:
                bisect.insort(L,s,key=lambda r : r[2])




M=[[
    [5,4,-1],
    [6,1,8],
    [7,3,2]
],0,0]
"""
M =np.array([
    [[1,2,3],
    [6,4,5],
    [-1,7,8]],[0],[]
])
"""
print(best_first(M))








