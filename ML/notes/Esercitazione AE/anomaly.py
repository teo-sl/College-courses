# in questo esempio, una rete neurale viene utilizzata per riconoscere
# anomalie. La rete è allenata fornendo come input e output lo stesso 
# elemento x. La rete impara quindi a riconoscere i dati normali. 
# Una volta ricevuto in ingresso un'anomalia, questa produrrà un output
# con un forte errore, non riconoscendo l'anomalia.
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
n = 10000
test_size = 1000

x_train = np.random.multivariate_normal(mean, cov, n)
x_test = np.random.multivariate_normal(mean, cov, test_size-10)
anomalies = np.random.multivariate_normal(5+mean, cov, 10)
x_test = np.concatenate((anomalies,x_test))
y_test = [1]*10+[0]*(test_size-10)


model = Sequential()
model.add(Dense(units=2, activation='relu',input_dim=d))
model.add(Dense(units=d, activation='sigmoid'))

model.compile(optimizer='sgd', loss='mse')

ep = 30
batches = 32
exists = os.path.isfile('anomaly.hist');
if exists:
    with open('anomaly.hist','rb') as hist_file:
        history_dict = pickle.load(hist_file);
        model.load_weights('anomaly')
else:
    history = model.fit(x_train, x_train, epochs=ep, batch_size=batches,
                             validation_data = (x_test, x_test));
    history_dict = history.history;
    with open('anomaly.hist','wb') as hist_file:
        pickle.dump(history_dict, hist_file);
    model.save_weights('anomaly');

outlierness = np.zeros(test_size)

for i in range(test_size):
    outlierness[i] = model.evaluate(x_test[i].reshape((1,d)),x_test[i].reshape((1,d)),verbose=0)

plt.figure(1)
plt.plot(outlierness,'.')
plt.xlabel('test id')
plt.ylabel('outlierness')

fpr, tpr, thresholds = roc_curve(y_test,outlierness)
auc = roc_auc_score(y_test,outlierness)

plt.figure(2)
plt.plot(fpr,tpr)
plt.plot([0, 1], [0, 1], linestyle='--')
plt.title('AUC = '+str(auc))

plt.show()