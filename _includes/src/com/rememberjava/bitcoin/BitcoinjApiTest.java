/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
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

  private static final File WALLET_DIR = new File("/tmp");

  private static final String WALLET_PREFIX = "rjtest";

  private NetworkParameters params;

  private WalletAppKit kit;

  private Wallet wallet;

  private volatile boolean receivedCoins;

  private volatile boolean sentCoins;

  /**
   * For all tests, connect to the TestNet3 network.
   */
  @Before
  public void setup() {
    params = TestNet3Params.get();
    kit = new WalletAppKit(params, WALLET_DIR, WALLET_PREFIX);
  }

  /**
   * Connect, sync and wait till done.
   */
  private void startSync() {
    kit.startAsync();
    kit.awaitRunning();
    sleep(1000);

    wallet = kit.wallet();
  }

  /**
   * Disconnect and wait till done.
   */
  @After
  public void teardown() {
    kit.stopAsync();
    kit.awaitTerminated();
  }

  /**
   * Even though it is possible to run without the logger implementation classes
   * in place, it is very confusing and misleading.
   * 
   * The bitcoinj logs output connection and transaction information. Without
   * them, it's difficult to know what is happening, and the application might
   * seem "stuck".
   */
  @Test
  public void checkLogger() {
    StaticLoggerBinder.getSingleton();
  }

  /**
   * Connects to the TestNet3 network and disconnects. If there is no walled
   * stored at the specified location, a new one is created and relevant blocks
   * are downloaded. This can take 1 to 2 minutes. If information is stale, it
   * can also take a minute to update.
   * 
   * The wallet used in this test is separate from the other tests.
   */
  @Test
  public void testSync() {
    params = TestNet3Params.get();
    kit = new WalletAppKit(params, new File("/dev/shm"), "test");

    kit.startAsync();
    kit.awaitRunning();

    kit.stopAsync();
    kit.awaitTerminated();
  }

  /**
   * Connects and prints address information about the test wallet (from the
   * TestNet3 network).
   */
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

  /**
   * Connects and prints wallet information (from the TestNet3 network).
   */
  @Test
  public void printWalletInfo() {
    startSync();

    Coin balance = wallet.getBalance();
    println("Balance satoshis: " + balance.toString());
    println("Balance friendly: " + balance.toFriendlyString());
    println("Version: " + wallet.getVersion());
  }

  /**
   * Waits for coins to be received on the prompted address. This test blocks
   * until the coinsReceived callback is called.
   * 
   * (This test is commented out to avoid blocking the other tests).
   */
  // @Test
  public void disabled_testReceive() {
    startSync();

    wallet.addCoinsReceivedEventListener(this::coinsReceived);

    Address toAdr = wallet.currentReceiveKey().toAddress(params);
    System.out.println("Waiting to receive coins on: " + toAdr);

    while (!receivedCoins) {
      sleep(100);
    }
  }

  /**
   * Callback called when the coins have been received.
   */
  private void coinsReceived(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
    Coin value = tx.getValueSentToMe(wallet);
    System.out.println("Received tx for " + value.toFriendlyString() + ": " + tx);
    receivedCoins = true;
  }

  /**
   * Sends test coins back to the TPFAUCET test network address. This test
   * blocks until the broadcastComplete callback is called.
   * 
   * (This test is commented out to avoid blocking the other test and
   * inadvertently sending away our money.
   */
  // @Test
  public void disabled_testSend() throws InsufficientMoneyException {
    startSync();

    // Adjust how many coins to send. E.g. the minimum; or everything.
    Coin sendValue = Transaction.REFERENCE_DEFAULT_MIN_TX_FEE;
    // Coin sendValue = wallet.getBalance().minus(Transaction.DEFAULT_TX_FEE);
    
    Address sendToAdr = Address.fromBase58(params, TPFAUCET_RETURN_ADR);
    SendRequest request = SendRequest.to(sendToAdr, sendValue);

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
