##SearchAverageNumber.MultiagentTechnologies 21/11/2017

Программа выполняет поиск среднего арифметического среди чисел агентов.

###Оценка алгоритма:
Память: O(N);
Кол-во обращений в центр: 1;
Кол-во обменов сообщениями между агентами: 2D*M+2M;
Кол-во тактов: 3D,

где D — диаметр графа, N — количество агентов, M — количество связей

###Пример аргументов программы:
-gui
-local-port
1111
Agent1:averageNumber.AverageNumberAgent(6,Agent2,Agent3);Agent2:averageNumber.AverageNumberAgent(6,Agent1,Agent4);Agent3:averageNumber.AverageNumberAgent(6,Agent1,Agent4);Agent4:averageNumber.AverageNumberAgent(6,Agent2,Agent3,Agent5);Agent5:averageNumber.AverageNumberAgent(6,Agent4,Agent6);Agent6:averageNumber.AverageNumberAgent(6,Agent5,Agent7,Agent9);Agent7:averageNumber.AverageNumberAgent(6,Agent6,Agent8);Agent8:averageNumber.AverageNumberAgent(6,Agent7,Agent9);Agent9:averageNumber.AverageNumberAgent(6,Agent6,Agent8);