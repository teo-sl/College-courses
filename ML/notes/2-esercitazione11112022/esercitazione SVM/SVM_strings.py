import numpy as np
from sklearn.svm import SVC
import matplotlib.pyplot as plt
import SVM_functions

size = 100
X_words = SVM_functions.StringsArray(2*size)

# ESEMPIO BASE

virus_string = 'virus'
for i in range(int(size)):
    pos = np.random.randint(0,len(X_words[i]))
    X_words[i] = X_words[i][:pos]+virus_string+X_words[i][pos+1:]

X_words = np.array(X_words)

a = 60
X_train = np.concatenate([np.array(range(a)),size+np.array(range(a))])
X_train = X_train.reshape(-1,1)
Y_train = [1]*a+[0]*a


test_size = int(size)-a
X_test = np.concatenate([a+np.array(range(test_size)),size+a+np.array(range(test_size))])
X_test = X_test.reshape(-1,1)
Y_test = [1]*test_size+[0]*test_size



# ESEMPIO CON 2 STRINGHE
"""
virus_string1 = 'abcde'
virus_string2 = 'fghil'
for i in range(int(size/2)):
    pos = np.random.randint(0,len(X_words[i]))
    X_words[i] = X_words[i][:pos]+virus_string1+X_words[i][pos+1:]

for i in range(int(size/2)):
    pos = np.random.randint(0,len(X_words[i]))
    X_words[i+int(size/2)] = X_words[i+int(size/2)][:pos]+virus_string2+X_words[i+int(size/2)][pos+1:]

X_words = np.array(X_words)

a = 30
X_train = np.concatenate([np.array(range(a)),int(size/2)+np.array(range(a)),size+np.array(range(2*a))])
X_train = X_train.reshape(-1,1)
Y_train = [1]*2*a+[0]*2*a


test_size = int(size/2)-a
X_test = np.concatenate([a+np.array(range(test_size)),size+a+np.array(range(test_size))])
X_test = X_test.reshape(-1,1)
Y_test = [1]*test_size+[0]*test_size
Y_test=np.array(Y_test)
"""


def CommonSubNumber(X, Y):
    # Create a table to store lengths of
    # longest common suffixes of substrings.
    # Note that LCSuff[i][j] contains the
    # length of longest common suffix of
    # X[0...i-1] and Y[0...j-1]. The first
    # row and first column entries have no
    # logical meaning, they are used only
    # for simplicity of the program.

    # LCSuff is the table with zero
    # value initially in each cell
    m = len(X)
    n = len(Y)

    LCSuff = [[0 for k in range(n + 1)] for l in range(m + 1)]

    # To store the number of common substrings
    result = 0

    # Following steps to build
    # LCSuff[m+1][n+1] in bottom up fashion
    for i in range(m + 1):
        for j in range(n + 1):
            if (i == 0 or j == 0):
                LCSuff[i][j] = 0
            elif (X[i - 1] == Y[j - 1]):
                LCSuff[i][j] = LCSuff[i - 1][j - 1] + 1
                result = result + LCSuff[i][j]
            else:
                LCSuff[i][j] = 0
    return result

# Matrice di Gram dei prodotti scalari

gram = np.zeros((2*size, 2*size))
for row in range(2*size):
    for column in range(2*size):
        gram[row,column] = CommonSubNumber(X_words[row],X_words[column])

# Funzione kernel custom che seleziona il valore desiderato all'interno della matrice di Gram
def compose_kernel(row_idxs, col_idxs):
    row_idxs = np.array(row_idxs).astype(np.int)
    col_idxs = np.array(col_idxs).astype(np.int)
    select_kernel = np.zeros((len(row_idxs), len(col_idxs)))
    for i, row_idx in enumerate(row_idxs):
        for j, col_idx in enumerate(col_idxs):
            select_kernel[i, j] = gram[row_idx, col_idx]
    return select_kernel

clf = SVC(kernel = compose_kernel)
fit = clf.fit(X_train, Y_train)

print(clf.score(X_test,Y_test))


SVM_functions.SVM_plot(X_train,Y_train,clf,'test')
