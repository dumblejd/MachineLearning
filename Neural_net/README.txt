you should use the function as follow:
Util_neural_two u=new Util_neural_two();
u.train("/Users/dijin/eclipse-workspace/Neural_net/adult.data.txt", 0.8, 1000, 3,3,4,5);

file path,rate,iteration,hidden layer number,node number for each hidden layer(separately) 

if you want to see the weight:
u.print_net();

if you want to see the accuracy:
u.accuracy_for_one_output(u.data_train);
u.accuracy_for_one_output(u.data_test);

if you want to save the standardized data:
set save path with: u.savepath=".../save.txt"  (you should create a txt file for it)