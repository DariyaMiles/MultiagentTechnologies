package averageNumber;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;

import java.util.concurrent.TimeUnit;

public class AgentBehaviour extends Behaviour {
    private int iterations = 0;
    private static final int MAX_DELAY = 40;

    public void action() {
        AverageNumberAgent agent = (AverageNumberAgent) myAgent;

        sendMessage(agent);
        receiveMessage(agent);

        iterations++;

    }

    private void sendMessage(AverageNumberAgent agent) {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);

        //добавление в список получателей сообщения агентов, с которыми есть связь
        for (AID name : agent.neighbors) {
            double tmp = Math.random(); //для проверки обрыва связи

            //считаем, что вероятность обрыва связи 0.6
            if (tmp > 0.6) {
                msg.addReceiver(name);
            }
        }

        //ошибка в передаче данных в диапазоне [-40; 40]
        double error = (Math.random() - 0.5) * 80;
        msg.setContent(Double.toString(agent.getMyNumber() + error));

        //задержка в диапазоне [0; 40]
        int delay = (int) (Math.random() * MAX_DELAY);

        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        agent.send(msg);
    }

    private void receiveMessage(AverageNumberAgent agent) {
        ACLMessage aclMessage;

        //обработка почтового ящика агента получателя
        while ((aclMessage = agent.receive()) != null) {
            double neighbourNumber = Double.parseDouble(aclMessage.getContent());

            //полученное сообщение было вида REQUEST
            if (aclMessage.getPerformative() == ACLMessage.REQUEST) {
                double average = (agent.getMyNumber() + neighbourNumber) / 2;

                //отдаем часть своего числа в ответе на REQUEST, только если у агента число больше,
                //чем ему пришло в сообщении(проверка с некоторой погрешностью)
                if (agent.getMyNumber() - neighbourNumber > 0.1) {
                    ACLMessage setReply = aclMessage.createReply();

                    //ошибка в диапазоне [-40; 40]
                    double error = (Math.random() - 0.5) * 80;
                    setReply.setContent(Double.toString(average - neighbourNumber + error));
                    setReply.setPerformative(ACLMessage.INFORM);

                    //задержка в диапазоне [0; 40]
                    int delay = (int) (Math.random() * MAX_DELAY);

                    try {
                        TimeUnit.MILLISECONDS.sleep(delay);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }

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
