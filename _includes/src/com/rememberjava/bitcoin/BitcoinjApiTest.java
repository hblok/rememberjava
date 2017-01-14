package com.rememberjava.bitcoin;

import java.io.File;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.Wallet.SendResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.impl.StaticLoggerBinder;

import com.google.common.util.concurrent.MoreExecutors;

/**
 * Multiple large file and network I/O tests for the Testnet3 block chain.
 * Comment in and out @Test annotations as need.
 * 
 * For more info:<br>
 * https://en.bitcoin.it/wiki/Testnet<br>
 * 
 * For test coins: <br>
 * http://faucet.xeno-genesis.com <br>
 * http://tpfaucet.appspot.com <br>
 * 
 * For test block explorer: <br>
 * https://testnet.blockexplorer.com
 *
 */
public class BitcoinjApiTest {

  /**
   * http://tpfaucet.appspot.com
   */
  private static final String TPFAUCET_RETURN_ADR = "n2eMqTT929pb1RDNuqEnxdaLau1rxy3efi";

  private static final File WALLET_DIR = new File("/dev/shm");

  private static final String WALLET_PREFIX = "rjtest";

  private NetworkParameters params;

  private WalletAppKit kit;

  private Wallet wallet;

  private volatile boolean receivedCoins;

  private volatile boolean sentCoins;

  @Before
  public void setup() {
    params = TestNet3Params.get();
    kit = new WalletAppKit(params, WALLET_DIR, WALLET_PREFIX);
  }

  private void startSync() {
    kit.startAsync();
    kit.awaitRunning();
    sleep(1000);

    wallet = kit.wallet();
  }

  @After
  public void teardown() {
    kit.stopAsync();
    kit.awaitTerminated();
  }

  @Test
  public void checkLogger() {
    StaticLoggerBinder.getSingleton();
  }

  @Test
  public void testSync() {
    params = TestNet3Params.get();
    kit = new WalletAppKit(params, new File("/dev/shm"), "test");

    kit.startAsync();
    kit.awaitRunning();

    kit.stopAsync();
    kit.awaitTerminated();
  }

  @Test
  public void printAddresses() {
    startSync();

    System.out.println("Receive Addresses:");
    wallet.getIssuedReceiveAddresses().stream().forEach(this::println);

    System.out.println("Watched Addresses:");
    wallet.getWatchedAddresses().stream().forEach(this::println);

    System.out.println("Current change address:");
    System.out.println(wallet.currentChangeAddress());
  }

  @Test
  public void printWalletInfo() {
    startSync();

    Coin balance = wallet.getBalance();
    println("Balance satoshis: " + balance.toString());
    println("Balance friendly: " + balance.toFriendlyString());
    println("Version: " + wallet.getVersion());
  }

  // @Test
  public void testReceive() {
    startSync();

    wallet.addCoinsReceivedEventListener(this::coinsReceived);

    Address toAdr = wallet.currentReceiveKey().toAddress(params);
    System.out.println("Waiting to receive coins on: " + toAdr);

    while (!receivedCoins) {
      sleep(100);
    }
  }

  private void coinsReceived(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
    Coin value = tx.getValueSentToMe(wallet);
    System.out.println("Received tx for " + value.toFriendlyString() + ": " + tx);
    receivedCoins = true;
  }

  // @Test
  public void testSend() throws InsufficientMoneyException {
    startSync();

    Address returnAdr = Address.fromBase58(params, TPFAUCET_RETURN_ADR);
    Coin sendValue = Transaction.REFERENCE_DEFAULT_MIN_TX_FEE;
    // Coin sendValue = wallet.getBalance().minus(Transaction.DEFAULT_TX_FEE);
    SendRequest request = SendRequest.to(returnAdr, sendValue);

    SendResult result = wallet.sendCoins(request);
    result.broadcastComplete.addListener(() -> {
      println("Coins were sent. Transaction hash: " + result.tx.getHashAsString());
      sentCoins = true;
    }, MoreExecutors.sameThreadExecutor());

    while (!sentCoins) {
      sleep(100);
    }
  }

  private void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void println(Object str) {
    System.out.println(str);
  }
}
