import numpy as np
import matplotlib
import matplotlib.pyplot as plt

import sklearn as sk
import sklearn.tree as skt
import sklearn.ensemble as ske

import decision_stump

np.random.seed(24) 
x = np.random.random((30000,2)) 
r = np.sqrt(1/(4*np.pi)) 
d = np.sqrt(np.sum((x-0.5)**2, axis=1)) 

y = 2*(d<=r)-1 
i = np.where(y==1)[0] 
j = np.where(y==-1)[0] 
plt.figure(1) 
plt.plot(x[i,0],x[i,1],'g.',x[j,0],x[j,1],'y.') 
plt.ion() 
plt.show() 
plt.pause(1.0001) 

for i in [3,5,10,50,75,100,125,200]:
    ne = i  #5*i+1 
    print(i) 
    ada_clf = ske.AdaBoostClassifier(decision_stump.DecisionStump(),n_estimators=ne, algorithm='SAMME')                                                                                                           
    #ada_clf = ske.AdaBoostClassifier(skt.DecisionTreeClassifier(max_depth=1),n_estimators=ne, algorithm='SAMME')                                                                                                           
    ada_clf.fit(x,y) 
    yt = ada_clf.predict(x) 
    it = (yt>0) 
    jt = (yt<0) 
    plt.figure(2) 
    plt.plot(x[it,0],x[it,1],'g.',x[jt,0],x[jt,1],'y.') 
    plt.ion() 
    plt.show() 
    plt.pause(1.0001) 

input("Press [Enter] to close\n") 