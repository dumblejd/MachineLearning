
# coding: utf-8

# In[1]:

from sklearn.neural_network import MLPClassifier


# In[2]:

import numpy as np
x=np.array([0,1,0,0,1,0,1,1]).reshape(4,2)
y=np.array([1,0,1,0]).reshape(4,)
clf=MLPClassifier(hidden_layer_sizes=(4,2),activation='relu', max_iter=10000)#it con't get the wright answer with iter=1000
clf.fit(x,y)


# In[3]:

clf.predict([[1,0],[0,1],[1,1],[0,0]])


# In[5]:

clf.score(x,y)


# In[7]:

clf.coefs_[0]


# In[8]:

clf.intercepts_[0]


# In[ ]:



