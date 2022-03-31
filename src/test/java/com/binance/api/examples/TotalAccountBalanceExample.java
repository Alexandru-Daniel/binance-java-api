package com.binance.api.examples;

import java.math.BigDecimal;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.constant.Util;
import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.account.AssetBalance;

/**
 * Example how to get total of balances on your account
 */
public class TotalAccountBalanceExample {


    public static void main(String[] args) {
//        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("YOUR_API_KEY", "YOUR_SECRET");
//        BinanceApiRestClient client = factory.newRestClient();
//
//        // Get account balances
//        Account account = client.getAccount(60_000L, System.currentTimeMillis());
//
//        // Get total account balance in BTC (spot only)
//        TotalAccountBalanceExample accountBalance = new TotalAccountBalanceExample();
//        double totalBalanceInBTC = accountBalance.getTotalAccountBalance(client,account);
//        System.out.println(totalBalanceInBTC);
//        // Get total account balance in USDT (spot only)
//        double totalBalanceInUSDT = totalBalanceInBTC * Double.parseDouble(client.getPrice("BTCUSDT").getPrice());
//        System.out.println(totalBalanceInUSDT);




    }

    // Get total account balance in BTC (spot only)
    public BigDecimal getTotalAccountBalance(BinanceApiRestClient client, Account account) {
    	BigDecimal totalBalance = BigDecimal.ZERO;
        for (AssetBalance balance : account.getBalances()) {
            BigDecimal free = balance.getFree();
            BigDecimal locked = balance.getLocked();
            String ticker = balance.getAsset() + Util.BTC_TICKER;
            String tickerReverse = Util.BTC_TICKER + balance.getAsset();
            if (free.add(locked).compareTo(BigDecimal.ZERO) != 0) {
                if (Util.isFiatCurrency(balance.getAsset())) {
                	BigDecimal price = client.getPrice(tickerReverse).getPrice();
                	BigDecimal amount = (free.add(locked)).divide(price);
                    totalBalance = totalBalance.add(amount);
                } else {
                	BigDecimal price = client.getPrice(ticker).getPrice();
                	BigDecimal amount = price.multiply(free.add(locked));
                    totalBalance = totalBalance.add(amount);
                }

            }
        }

        return totalBalance;

    }



}
