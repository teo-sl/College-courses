import sys
sys.path.extend(['/home/luca/Scrivania/PyDir/CNN'])
import tensorflow.keras
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense
import numpy as np
import matplotlib.pylab as plt
import pickle
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'
from sklearn.metrics import roc_curve,roc_auc_score
from sklearn.svm import OneClassSVM


def dataset(dig,size):
  (x_train, y_train), (x_test, y_test) = tensorflow.keras.datasets.mnist.load_data()
  x_train = x_train.astype('float32') / 255.

  #Crea dataset con normal score id
  I_dig = np.where(y_train == dig)[0]
  x_dig = x_train[I_dig]
  y_dig = [0] * x_dig.shape[0]
  x_training = x_dig
  for i in range(10):
    id_anomaly = np.array([])
    if (i != dig):
      jj = np.where(y_train == i)[0]
      id_estratti = np.array([])
      while id_estratti.shape[0] < size:
        new_id = np.random.randint(jj.shape[0])
        if new_id not in id_estratti:
              id_estratti = np.append(id_estratti,new_id)
      id_estratti = id_estratti.astype(int)

      xjj = x_train[jj[id_estratti]]
      id_anomaly = np.concatenate((id_anomaly,id_estratti))
      x_training = np.concatenate((x_training, xjj))

  y_nodig = [1] * 9 * size
  y_training = np.concatenate((y_dig, y_nodig))
  id_anomaly = id_anomaly.astype(int)
  return x_training,y_training, id_anomaly

inlier_digit = 0
size = 10
x_training, y_training, id_anomaly = dataset(inlier_digit,size)

# SIZE OF LAYERS
n = 784
l2 = 128
l3 = 64

# MODEL'S STRUCTURE
input = tensorflow.keras.Input(shape=(n,))
x = tensorflow.keras.layers.Dense(l2, activation ='relu')(input)
x = tensorflow.keras.layers.Dense(l3,activation = 'relu')(x)
encoder = tensorflow.keras.Model(input, x)
x = tensorflow.keras.layers.Dense(l2, activation='relu')(x)
x = tensorflow.keras.layers.Dense(n, activation='sigmoid')(x)
autoencoder = tensorflow.keras.Model(input, x)

autoencoder.compile(optimizer='adam', loss='mse')

ep = 10
batches = 32
x_training = x_training.reshape(x_training.shape[0],784)

exists = os.path.isfile('anomaly3.hist');
if exists:
    with open('anomaly3.hist','rb') as hist_file:
        history_dict = pickle.load(hist_file);
        autoencoder.load_weights('anomaly3')
else:
    history = autoencoder.fit(x_training, x_training, epochs=ep, batch_size=batches);
    history_dict = history.history;
    with open('anomaly3.hist','wb') as hist_file:
        pickle.dump(history_dict, hist_file);
    autoencoder.save_weights('anomaly3');

exists = os.path.isfile('anomaly3out.npy');
if exists:
    outlierness = np.load('anomaly3out.npy')
else:
    outlierness = np.zeros(x_training.shape[0])

    for i in range(x_training.shape[0]):
        outlierness[i] = autoencoder.evaluate(x_training[i].reshape((1, 784)), x_training[i].reshape((1, 784)),
                                              verbose=1)

plt.figure()
plt.plot(outlierness,'.')
plt.xlabel('test id')
plt.ylabel('AE outlierness')

fpr, tpr, thresholds = roc_curve(y_training,outlierness)
auc = roc_auc_score(y_training,outlierness)

plt.figure()
plt.plot(fpr,tpr)
plt.plot([0, 1], [0, 1], linestyle='--')
plt.title('AE AUC = '+str(auc))


clf = OneClassSVM(gamma='auto').fit(x_training)
svm_score = clf.score_samples(x_training)
auc_SVM = roc_auc_score(1-y_training, svm_score)
fpr_SVM, tpr_SVM, thresholds_SVM = roc_curve(1-y_training,svm_score)

plt.figure()
plt.plot(svm_score,'.')
plt.xlabel('test id')
plt.ylabel('1class-SVM outlierness')


plt.figure()
plt.plot(fpr_SVM,tpr_SVM)
plt.plot([0, 1], [0, 1], linestyle='--')
plt.title('1class-SVM AUC = '+str(auc_SVM))


