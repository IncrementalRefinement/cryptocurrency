package ass2;

import java.util.Set;

public interface Node {

    // NOTE: ass2.Node is an interface and does not have a constructor.
    // However, your ass2.CompliantNode.java class requires a 4 argument
    // constructor as defined in ass2.Simulation.java

    /** {@code followees[i]} is true if and only if this node follows node {@code i} */
    void setFollowees(boolean[] followees);

    /** initialize proposal list of transactions */
    void setPendingTransaction(Set<Transaction> pendingTransactions);

    /**
     * @return proposals to send to my followers. REMEMBER: After final round, behavior of
     *         {@code getProposals} changes and it should return the transactions upon which
     *         consensus has been reached.
     */
    Set<Transaction> sendToFollowers();

    /** receive candidates from other nodes. */
    void receiveFromFollowees(Set<Candidate> candidates);
}