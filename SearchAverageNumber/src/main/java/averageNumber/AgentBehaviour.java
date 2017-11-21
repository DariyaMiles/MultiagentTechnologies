package averageNumber;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.concurrent.TimeUnit;

public class AgentBehaviour extends OneShotBehaviour {
    private static final String MSG_PASSED = "I'm passed";

    public void action() {
        AverageNumberAgent agent = (AverageNumberAgent) myAgent;

        try{
            TimeUnit.MILLISECONDS.sleep(1000);
        }
        catch(InterruptedException e){
            System.out.println("Error in try/catch in averageNumber.AgentBehaviour.java");
        }

        if (agent.getIsMain()) {
            agent.setIsPassed();
            System.out.println(agent.getLocalName() +" is main.");

            AverageNumber averageNumber = new AverageNumber(agent.getMyNumber());

            for (AID name : agent.neighbors) {
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(name);
                msg.setContent(averageNumber.toString());
                agent.send(msg);

                AverageNumber tmp = setReply(agent, name);
                if (tmp != null) {
                    averageNumber = tmp;
                }
            }

            System.out.println("Average is "+ averageNumber.calculate());

        } else {
            while (true) {
                ACLMessage msgRequest = agent.blockingReceive();

                if(!agent.getIsPassed()){
                    agent.setIsPassed();

                    AverageNumber averageNumber = AverageNumber.parseString(msgRequest.getContent());
                    averageNumber.add(agent.getMyNumber());

                    for (AID name : agent.neighbors) {
                        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);

                        if (!name.equals(msgRequest.getSender())) {
                            msg.addReceiver(name);
                        } else {
                            continue;
                        }

                        msg.setContent(averageNumber.toString());
                        agent.send(msg);

                        AverageNumber tmp = setReply(agent, name);
                        if (tmp != null) {
                            averageNumber = tmp;
                        }

                    }

                    ACLMessage msgReply = msgRequest.createReply();
                    msgReply.addReceiver(msgRequest.getSender());
                    msgReply.setContent(averageNumber.toString());
                    agent.send(msgReply);
                }
                else{
                    setReplyPassed(msgRequest);
                }
            }
        }
    }

    private void setReplyPassed(ACLMessage msg) {
        ACLMessage msgAnswer = msg.createReply();
        msgAnswer.setContent(MSG_PASSED);
        myAgent.send(msgAnswer);
    }

    private AverageNumber setReply(jade.core.Agent agent, AID name) {
        while (true) {
            ACLMessage msg = agent.receive();

            if (msg != null) {
                if (name.equals(msg.getSender())) {
                    if (!msg.getContent().equals(MSG_PASSED)) {
                        return AverageNumber.parseString(msg.getContent());
                    } else {
                        return null;
                    }
                } else {
                    setReplyPassed(msg);
                }

            } else {
                block();
            }
        }
    }

}
