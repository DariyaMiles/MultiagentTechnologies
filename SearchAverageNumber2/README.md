## SearchAverageNumber2.MultiagentTechnologies 8/12/2017

Программа выполняет поиск среднего арифметического среди чисел агентов с некоторой погрешностью с учетом шума в передаче данных, задержки в передаче данных и обрывов связи между агентами.

### Пример аргументов программы:
-gui -local-port 1111 Agent1:averageNumber.AverageNumberAgent(6,Agent2,Agent3);Agent2:averageNumber.AverageNumberAgent(6,Agent1,Agent4);Agent3:averageNumber.AverageNumberAgent(6,Agent1,Agent4);Agent4:averageNumber.AverageNumberAgent(6,Agent2,Agent3,Agent5);Agent5:averageNumber.AverageNumberAgent(6,Agent4,Agent6);Agent6:averageNumber.AverageNumberAgent(6,Agent5,Agent7,Agent9);Agent7:averageNumber.AverageNumberAgent(6,Agent6,Agent8);Agent8:averageNumber.AverageNumberAgent(6,Agent7,Agent9);Agent9:averageNumber.AverageNumberAgent(6,Agent6,Agent8);