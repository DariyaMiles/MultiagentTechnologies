package averageNumber;

import jade.core.AID;
import jade.core.behaviours.SequentialBehaviour;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AverageNumberAgent extends jade.core.Agent {

    private int diameter;

    int getDiameter() {
        return diameter;
    }

    private double maxNumber;

    double getMaxNumber() {
        return maxNumber;
    }

    void setMaxNumber(double maxNumber) {
        this.maxNumber = maxNumber;
    }

    private Integer myNumber;

    Integer getMyNumber() {
        return myNumber;
    }

    private boolean isMain = true;

    boolean getIsMain() {
        return isMain;
    }

    void setIsMain() {
        this.isMain = false;
    }

    private boolean isPassed = false;

    boolean getIsPassed() {
        return isPassed;
    }

    void setIsPassed() {
        this.isPassed = true;
    }

    ArrayList<AID> neighbors = new ArrayList<AID>();

    protected void setup() {
        maxNumber = Math.random();
        myNumber = (int) (Math.random() * 1000);
        isMain = true;

        System.out.println("I'm " + getAID().getLocalName() + ". My number is " + myNumber);

        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            diameter = Integer.parseInt((String) args[0]);

            for (int i = 1; i < args.length; i++) {
                neighbors.add(new AID((String)args[i], AID.ISLOCALNAME));
            }
        } else {
            doDelete();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            System.out.println("Error in try/catch in averageNumber.AverageNumberAgent.java");
        }

        SequentialBehaviour mainBehaviour = new SequentialBehaviour();
        mainBehaviour.addSubBehaviour(new SearchMainAgentBehaviour());
        mainBehaviour.addSubBehaviour(new AgentBehaviour());
        addBehaviour(mainBehaviour);
    }

    protected void takeDown() {
        System.out.println("AverageNumberAgent " + getAID().getName() + " terminating");
    }
}