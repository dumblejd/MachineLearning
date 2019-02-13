
# coding: utf-8

# In[1]:

from sklearn import preprocessing


# In[2]:

import pandas as pd


# In[3]:

df=pd.read_csv("http://archive.ics.uci.edu/ml/machine-learning-databases/balance-scale/balance-scale.data",names=["Class","Left-weight","Left-distance","Right-weight","Right-distancce"])


# In[4]:

df.head()


# In[5]:

from sklearn.model_selection import train_test_split


# In[6]:

x=df.drop('Class',axis=1)
y=df['Class']


# In[7]:

X_train,X_test,y_train,y_test=train_test_split(x,y,test_size=0.4)


# In[8]:

from sklearn.neural_network import MLPClassifier


# In[9]:

mlp=MLPClassifier(hidden_layer_sizes=(30,30,30),max_iter=1000,activation='relu')


# In[10]:

mlp.fit(X_train,y_train)


# In[11]:

predictions=mlp.predict(X_test)


# In[12]:

from sklearn.metrics import classification_report,confusion_matrix


# In[13]:

print(confusion_matrix(y_test,predictions))


# In[14]:

print(classification_report(y_test,predictions))


# In[ ]:



