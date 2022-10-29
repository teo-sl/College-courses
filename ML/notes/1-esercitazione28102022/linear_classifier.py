import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import scipy
import sklearn as sk
import sklearn.preprocessing

def linear_classifier(ds,y):
    n,d =ds.shape
    ds = sk.preprocessing.add_dummy_feature(ds)
    A = -1*np.array([y]).T * ds
    B = -1*np.ones(n)
    res = scipy.optimize.linprog(np.zeros(d+1),A_ub = A, b_ub = B, bounds = [None,None])
    return res.x

path_ad='./ML/notes/intro/ds_adolescenti.csv'
path_at='./ML/notes/intro/ds_atleti.csv'

ds_adolscenti = pd.read_csv(path_ad,delimiter=';',header=None)
ds_atleti = pd.read_csv(path_at,delimiter=';',header=None)
i1 = ds_adolscenti[ds_adolscenti[1]<69]
i2 = ds_atleti[ds_atleti[1]>71]
frames = [i1,i2]
ds_tot=pd.concat(frames)
n1 = i1.shape[0]
n2 = i2.shape[0]
ds_tot = sklearn.preprocessing.minmax_scale(ds_tot)

y = np.block([np.ones(n1),-1*np.ones(n2)])

w = linear_classifier(ds_tot,y)
print(w)

