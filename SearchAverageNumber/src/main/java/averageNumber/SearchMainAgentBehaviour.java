package averageNumber;

import jade.core.behaviours.Behaviour;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.util.concurrent.TimeUnit;

public class SearchMainAgentBehaviour extends Behaviour {
    private int iterations = 0;

    public void action() {
        AverageNumberAgent agent = (AverageNumberAgent) myAgent;
        sendMessage(agent);

        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("Error in try/catch in averageNumber.SearchMainAgentBehaviour.java");
        }

        recieveMessage(agent);
        iterations++;
    }

    private void sendMessage(AverageNumberAgent agent) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

        for (AID name : agent.neighbors) {
            msg.addReceiver(name);
        }

        msg.setContent(Double.toString(agent.getMaxNumber()));
        myAgent.send(msg);
    }

    private void recieveMessage(AverageNumberAgent agent) {
        ACLMessage msg;

        while ((msg = myAgent.receive()) != null) {
            double maxFromNeighbor = Double.parseDouble(msg.getContent());

            if (maxFromNeighbor > agent.getMaxNumber()) {
                agent.setIsMain();
                agent.setMaxNumber(maxFromNeighbor);
            }
        }

    }

    public boolean done() {
        AverageNumberAgent agent = (AverageNumberAgent) myAgent;

        return iterations == (int) (agent.getDiameter());
    }
}
