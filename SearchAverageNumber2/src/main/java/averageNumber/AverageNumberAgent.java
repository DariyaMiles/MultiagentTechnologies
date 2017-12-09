package averageNumber;

import jade.core.Agent;
import jade.core.AID;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AverageNumberAgent extends Agent {
    private double myNumber;

    double getMyNumber() {
        return myNumber;
    }

    void setMyNumber(double number) {
        this.myNumber = number;
    }

    ArrayList<AID> neighbors = new ArrayList<AID>();

    protected void setup() {
        //генерируем число агента в диапазоне [40; 1000], т.к ошибка в передаче данных будет в диапазоне [-40; 40],
        //чтобы избежать появления отрицательных чисел
        int min = 40;
        int max = 1000;
        max -= min;
        myNumber = (double) (int) (Math.random() * ++max) + min;

        System.out.println(getLocalName() + " number: " + getMyNumber());

        //добавляем "соседей" агента
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                neighbors.add(new AID((String) args[i], AID.ISLOCALNAME));
            }
        } else {
            doDelete();
        }

        //ожидаем запуска всех агентов
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            System.out.println("Error in try/catch in averageNumber.AverageNumberAgent.java");
        }

        addBehaviour(new AgentBehaviour());

    }


    protected void takeDown() {
        System.out.println("AverageNumberAgent " + getLocalName() + " terminating");
    }

}
