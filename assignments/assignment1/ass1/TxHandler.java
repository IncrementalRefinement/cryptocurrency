package ass1;

import java.util.*;

public class TxHandler {

    private UTXOPool currentPool;

    /**
     * Creates a public ledger whose current ass1.UTXOPool (collection of unspent transaction outputs) is
     * {@code utxoPool}. This should make a copy of utxoPool by using the ass1.UTXOPool(ass1.UTXOPool uPool)
     * constructor.
     */
    public TxHandler(UTXOPool utxoPool) {
        // IMPLEMENT THIS
        currentPool = new UTXOPool(utxoPool);
    }

    /**
     * @return true if:
     * (1) all outputs claimed by {@code tx} are in the current ass1.UTXO pool,
     * (2) the signatures on each input of {@code tx} are valid, 
     * (3) no ass1.UTXO is claimed multiple times by {@code tx},
     * (4) all of {@code tx}s output values are non-negative, and
     * (5) the sum of {@code tx}s input values is greater than or equal to the sum of its output
     *     values; and false otherwise.
     */
    public boolean isValidTx(Transaction tx) {
        // IMPLEMENT THIS
        List<Transaction.Input> inputs = tx.getInputs();
        List<Transaction.Output> outputs = tx.getOutputs();
        double totalInputValue = 0;
        double totalOutputValue = 0;
        Set<UTXO> UtxoOfTX = new HashSet<>();

        for (Transaction.Output output : outputs) {
            if (output.value < 0) {
                return false;
            }
            totalOutputValue += output.value;
        }

        for (Transaction.Input input : inputs) {
            UTXO theUTXO = new UTXO(input.prevTxHash, input.outputIndex);
            if (!currentPool.contains(theUTXO)) {
                return false;
            } else {
                Transaction.Output output = currentPool.getTxOutput(theUTXO);
                if (!Crypto.verifySignature(output.address, tx.getRawDataToSign(input.outputIndex), input.signature)) {
                    return false;
                }
                if (UtxoOfTX.contains(theUTXO)) {
                    return false;
                } else {
                    UtxoOfTX.add(theUTXO);
                }
                totalInputValue += output.value;
            }
        }

        // TODO: might have precision bug of float here
        if (totalInputValue != totalOutputValue) {
            return false;
        }

        return true;
    }

    /**
     * Handles each epoch by receiving an unordered array of proposed transactions, checking each
     * transaction for correctness, returning a mutually valid array of accepted transactions, and
     * updating the current ass1.UTXO pool as appropriate.
     */
    public Transaction[] handleTxs(Transaction[] possibleTxs) {
        LinkedList<Transaction> TXs = new LinkedList<>();
        for (Transaction TX : possibleTxs) {
            if (isValidTx(TX)) {
                handleTx(TX);
                TXs.add(TX);
            }
        }
        return TXs.toArray(new Transaction[0]);
    }


    private void handleTx(Transaction TX) {
        // TODO: implement this
    }
}
