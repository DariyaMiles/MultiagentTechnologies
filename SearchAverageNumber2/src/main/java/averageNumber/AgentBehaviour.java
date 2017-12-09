package averageNumber;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;

import java.util.concurrent.TimeUnit;

public class AgentBehaviour extends Behaviour {
    private int iterations = 0;

    public void action() {
        AverageNumberAgent agent = (AverageNumberAgent) myAgent;

        sendMessage(agent);
        receiveMessage(agent);

        iterations++;

    }

    private void sendMessage(AverageNumberAgent agent) {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        double error; //ошибка в передаче данных
        double tmp; //для проверки обрыва связи
        int delay; //задержка в передаче данных

        //добавление в список получателей сообщения агентов, с которыми есть связь
        for (AID name : agent.neighbors) {
            tmp = Math.random();

            if (tmp < 0.33) {
                msg.addReceiver(name);
            }
        }

        //ошибка в диапазоне [-40; 40]
        error = (Math.random() - 0.5) * 80;
        msg.setContent(Double.toString(agent.getMyNumber() + error));

        //задержка в диапазоне [0; 40]
        delay = (int) (Math.random() * 40);

        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            System.out.println("Error in try/catch in averageNumber.AgentBehaviour.java");
        }

        agent.send(msg);
    }

    private void receiveMessage(AverageNumberAgent agent) {
        ACLMessage aclMessage;
        double average;
        double error;

        //обработка почтового ящика агента получателя
        while ((aclMessage = agent.receive()) != null) {
            //полученное сообщение было вида REQUEST
            if (aclMessage.getPerformative() == ACLMessage.REQUEST) {
                average = (agent.getMyNumber() + Double.parseDouble(aclMessage.getContent())) / 2;

                //отдаем часть своего числа в ответе на REQUEST, только если у агента число больше,
                //чем ему пришло в сообщении(проверка с некоторой погрешностью)
                if (agent.getMyNumber() - Double.parseDouble(aclMessage.getContent()) > 0.1) {
                    ACLMessage setReply = aclMessage.createReply();
                    error = (Math.random() - 0.5) * 80;
                    setReply.setContent(Double.toString(average - Double.parseDouble(aclMessage.getContent()) + error));
                    setReply.setPerformative(ACLMessage.INFORM);
                    agent.send(setReply);
                    agent.setMyNumber(average);

                }
            //полученное сообщение было ответом на REQUEST
            } else if (aclMessage.getPerformative() == ACLMessage.INFORM) {
                agent.setMyNumber(agent.getMyNumber() + Double.parseDouble(aclMessage.getContent()));

            }
        }

    }

    public boolean done() {
        //считаем, что за 200 итераций агенты обменяются числами достаточное количество раз,
        //чтобы у каждого из них было бы среднее арифметическое, найденное с некоторой погрешностью
        if (iterations == 200) {
            AverageNumberAgent agent = (AverageNumberAgent) myAgent;
            System.out.println(agent.getLocalName() + ": Average number is " + agent.getMyNumber());

        }
        return iterations == 200;
    }
}
