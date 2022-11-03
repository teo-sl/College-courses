import numpy as np
import math 
def convert_image(X):
    converted = []
    k = X.shape[0]
    if(X.shape[0] != X.shape[1]):
        print("dimensioni discordi")
        return None

    tmp = 0
    for i in range(0,k):
        for j in range(0,k):
            for p in range(0,k):
                for r in range(0,k):
                    rett = evaulate_rett([i,j],[p,r],X)
                    if rett is None:
                        continue
                    tmp+=1
                    converted.append(first_mask(rett))
                    converted.append(second_mask(rett))
                    converted.append(third_mask(rett))
                    converted.append(fouth_mask(rett))

    print(tmp)
    
    return np.array(converted)

                    
                    


def evaulate_rett(xs,ys,X):
    x_min=min(xs)
    x_max=max(xs)
    y_min=min(ys)
    y_max=max(ys)

    #if (x_max-x_min)<=1 and (y_max-y_min)<=1:
    #   return None
    
    return X[x_min:x_max+1,y_min:y_max+1]


def first_mask(X):
    s1 = 0
    s2 = 0
    (m,n)=X.shape

    x_med =math.floor(n/2)


    for i in range(0,m):
        for j in range(0,n):
            if(j<=x_med):
                s1+=X[i,j]
            else:
                s2+=X[i,j]
    return s1-s2

def second_mask(X):
    s1 = 0
    s2 = 0
    (m,n)=X.shape

    x_med =math.floor(m/2)


    for i in range(0,m):
        for j in range(0,n):
            if(i<=x_med):
                s1+=X[i,j]
            else:
                s2+=X[i,j]
    return s1-s2


def third_mask(X):
    s1 = 0
    s2 = 0
    (m,n)=X.shape

    x_1 =math.floor(n/3)
    x_2 = x_1*2



    for i in range(0,m):
        for j in range(0,n):
            if(j<=x_1 or j>x_2):
                s1+=X[i,j]
            else:
                s2+=X[i,j]
    return s1-s2


def fouth_mask(X):
    s1 = 0
    s2 = 0
    (m,n)=X.shape

    x_n =math.floor(n/2)
    x_m = math.floor(m/2)


    for i in range(0,m):
        for j in range(0,n):
            if(i<=x_m and j<=x_n or i>x_m and j>x_n):
                s2+=X[i,j]
            else:
                s1+=X[i,j]
    return s1-s2



