import sys
sys.path.extend(['/home/luca/Scrivania/PyDir/CNN'])
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense
import numpy as np
import matplotlib.pylab as plt
import pickle
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'
from sklearn.metrics import roc_curve,roc_auc_score

d = 10
mean = np.zeros(d)
cov = np.eye(d)
n = 500

x_train = np.random.multivariate_normal(mean, cov, n-10)
anomalies = np.random.multivariate_normal(5+mean, cov, 10)
x_train = np.concatenate((anomalies,x_train))
y_train = [1]*10+[0]*(n-10)



model = Sequential()
model.add(Dense(units=2, activation='relu',input_dim=d))
model.add(Dense(units=d, activation='sigmoid'))

model.compile(optimizer='sgd', loss='mse')

ep = 30
batches = 32
exists = os.path.isfile('anomaly2.hist');
if exists:
    with open('anomaly2.hist','rb') as hist_file:
        history_dict = pickle.load(hist_file);
        model.load_weights('anomaly2')
else:
    history = model.fit(x_train, x_train, epochs=ep, batch_size=batches);
    history_dict = history.history;
    with open('anomaly2.hist','wb') as hist_file:
        pickle.dump(history_dict, hist_file);
    model.save_weights('anomaly2');

outlierness = np.zeros(n)

for i in range(n):
    outlierness[i] = model.evaluate(x_train[i].reshape((1,d)),x_train[i].reshape((1,d)),verbose=1)

plt.figure(1)
plt.plot(outlierness,'.')
plt.xlabel('test id')
plt.ylabel('outlierness')

fpr, tpr, thresholds = roc_curve(y_train,outlierness)
auc = roc_auc_score(y_train,outlierness)

plt.figure(2)
plt.plot(fpr,tpr)
plt.plot([0, 1], [0, 1], linestyle='--')
plt.title('AUC = '+str(auc))

plt.show()