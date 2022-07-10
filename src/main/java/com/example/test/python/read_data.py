# -*- coding: utf-8 -*-
import pandas as pd
import numpy as np
import csv
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
from sklearn import svm
#from sklearn.ensemble import gradient_boosting
from sklearn.tree import DecisionTreeClassifier
from sklearn.linear_model import LogisticRegression
from sklearn.linear_model import LogisticRegression
from sklearn.naive_bayes import MultinomialNB,GaussianNB
from sklearn.neighbors import KNeighborsClassifier

pd.set_option('display.max_columns', None)
pd.set_option('display.max_rows', None)

def evluation(y, y_pred):
    acc = 0
    for i in range(len(y)):
        for j in range(len(y[i])):

            if int(y[i][j]) == y_pred[i][j]:
                acc += 1

    return acc / (len(y) * len(y[0]))

def getdata():
    x = []
    y = []
    with open('D:\Smartheal\后端\harmony-osapp-server\src\main\java\com\example\\test\python\pregnant_data.csv', encoding='utf-8') as f:
        csv_file = csv.reader(f)
        lines = 0
        for line in csv_file:
            lines += 1
            if lines == 215:
                break
            temp_x = []
            temp_y = []
            for i in range(0,27):
                # 序号, 编号, 姓名, 孕周, 年龄, 身高, 孕前体重, 体重, 增长速度, 24hUCrE,
                # 孕前BMI, 孕前BMI评价, 体脂百分比, 去脂体重, 肌肉含量, 蛋白质, 总能量推荐, 总食物交换份, 主食, 蔬菜,
                # 水果, 肉蛋, 豆, 奶, 坚果, 油, 盐
                if i not in [0,1,2,16,17,18,19,20,21,22,23,24,25,26]:
                    if (line[i].strip() == ''): # | (line[i] == '无'):
                        temp_x.append(0)
                        continue
                    try:
                        temp_x.append(int(line[i].replace('>', '').replace('<', '')))
                    except:
                        temp_x.append(float(line[i].replace('>', '').replace('<', '')))
            for i in range(16,27):
                if line[i].strip() == '':
                    temp_y.append(0)
                else:
                    temp_y.append(int(line[i].replace('>', '').replace('<', '')))
            x.append(temp_x)
            y.append(temp_y)
    return x, y

def part_per_day(patient_x, x, y):
    model = RandomForestClassifier(max_depth=25, min_samples_split=13, min_samples_leaf=5)
    result_train = []
    result_test = []
    for i in range(20):
        train_x, valid_x, train_y, valid_y = train_test_split(x, y , train_size=0.75)
        model.fit(train_x, train_y)
        result_train.append(evluation(train_y, model.predict(train_x)))
        result_test.append(evluation(valid_y, model.predict(valid_x)))
    return model.predict(patient_x)[0]

# print("Training set:")
# print(sum(result_train)/ len(result_train))
# print("Validation set:")
# print(sum(result_test) / len(result_test))

# [孕周，年龄，身高，孕前体重，体重，增长速度，UCrE，BMI，体型，体脂百分比，去脂体重，肌肉含量，蛋白质]
# test_x = [[12,30,1.62,64,65.27,1,1.46732,24.39,3,0.3107,44.99,42.53,9.36],
#           [9,31,1.6,67,63.91,0,1.44556,26.17,3,0.3216,43.36,40.97,9.01],
#           [14,32,1.6,48,48.72,0,1.20252,18.75,2,0.1855,39.68,36.91,8.12],
#           [17,32,1.67,51,52.9,1,1.2694,18.29,1,0.2036,42.12,38.75,8.53]]

#test_x = x[5:10]
# print(part_per_day(test_x, x, y))

if __name__ == "__main__":
    import sys
    import json
    """input = [12,30,1.62,64,65.27,1,1.46732,24.39,3,0.3107,44.99,42.53,9.36]"""
    x, y = getdata()
    input = []
    for i in range(1,14):
        input.append(float(sys.argv[i]))
    output = part_per_day([input],x,y)
    temp = {
        "totalEnergy":str(output[0]),
        "totalFood":str(output[1]),
        "mainFood":str(output[2]),
        "vegetables":str(output[3]),
        "fruit":str(output[4]),
        "meatAndEgg":str(output[5]),
        "bean":str(output[6]),
        "milk":str(output[7]),
        "nut":str(output[8]),
        "oil":str(output[9]),
        "salt":str(output[10])
    }
    print(json.dumps(temp))


