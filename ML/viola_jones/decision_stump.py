import numpy as np
import sklearn
import math
import sklearn.base
import sklearn.preprocessing

class DecisionStump(sklearn.base.BaseEstimator):
    def __init__(self):
        self.axis_ = 0  
        self.thres_ = 0.5  
        self.direction_ = 1  
        self.classes_ = np.array([-1, 1])  
        self.n_classes_ = 2  

    def fit(self, X, y, sample_weight=None):
        if sample_weight is None:
            sample_weight = np.ones((len(X),1))
        W = sample_weight.sum()  
        m = X.shape[0] # number of rows
        d = 24**2 * 4 # number of dimension
        self.classes_ = np.unique(y) # numero di classi, per il classificatore binario sono due
        W0 = sample_weight[np.where(y==self.classes_[0])[0]].sum()  
        minerr = W  
        for j in np.random.randint(size=d,low=0,high=24**4*4):
            values = project_samples(X,j)
            sorted_id = np.argsort(values)  
            x = values[sorted_id[0]] - 0.5  
            err = W0
            for i in range(m):
                if(i<m-1):
                    x = (values[sorted_id[i]]+values[sorted_id[i+1]])/2  
                else:
                    x = values[sorted_id[i]] + 0.5  
                if y[sorted_id[i]]==self.classes_[0]:
                    err = err-sample_weight[sorted_id[i]]
                else:
                    err = err+sample_weight[sorted_id[i]]
                if err<minerr:
                    self.axis_ = j
                    self.thres_ = x  
                    self.direction_ = 1
                    minerr = err
                if W-err<minerr:
                    self.axis_ = j
                    self.thres_ = x  
                    self.direction_ = -1
                    minerr = W-err
        return self
    def predict(self, X):
        values = project_samples(X,self.axis_)
        i = values[:]<=self.thres_  
        j = values[:]>self.thres_  
        p = self.classes_[0]*np.ones(len(X))  
        if self.direction_==1:
            p[j] = self.classes_[1]  
        else:
            p[i] = self.classes_[1]  
        return p  

def project_samples(X,d):
    dim = 24
    r = (d//4)%dim
    p = (d//(dim*4))%dim
    j = (d//(dim**2*4))%dim
    i = (d//(dim**3*4))%dim
    filter = d%4

    ret=[]

    for x in X:
        rettangolo = evaulate_rett([i,j],[p,r],x)

        if(filter==0):
            ret.append(first_mask(rettangolo))
        elif(filter==1):
            ret.append(second_mask(rettangolo))
        elif(filter==2):
            ret.append(third_mask(rettangolo))
        elif(filter==3):
            ret.append(fourth_mask(rettangolo))
    
    return np.array(ret)


def evaulate_rett(xs,ys,X):
    x_min=min(xs)
    x_max=max(xs)
    y_min=min(ys)
    y_max=max(ys)

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


def fourth_mask(X):
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


clf = DecisionStump()

X = np.random.randint(size=(500,24,24),low=0,high=256)

y = np.concatenate((np.ones(250),np.full(250,-1)),axis=0)

clf.fit(X,y)

