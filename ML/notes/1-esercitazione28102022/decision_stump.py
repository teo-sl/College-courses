import numpy as np
import sklearn
import sklearn.base
import sklearn.preprocessing
import matplotlib
import matplotlib.pyplot as plt

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
        (m,d) = X.shape  
        self.classes_ = np.unique(y)
        W0 = sample_weight[np.where(y==self.classes_[0])[0]].sum()  
        minerr = W  
        for j in range(d):
            sorted_id = np.argsort(X[:,j])  
            x = X[sorted_id[0],j] - 0.5  
            err = W0
            for i in range(m):
                if(i<m-1):
                    x = (X[sorted_id[i],j]+X[sorted_id[i+1],j])/2  
                else:
                    x = X[sorted_id[i],j] + 0.5  
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
        i = X[:,self.axis_]<=self.thres_  
        j = X[:,self.axis_]>self.thres_  
        p = self.classes_[0]*np.ones(len(X))  
        if self.direction_==1:
            p[j] = self.classes_[1]  
        else:
            p[i] = self.classes_[1]  
        return p  

 
        