package com.binance.api.client.domain.event;

import com.binance.api.client.domain.account.AssetBalance;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Custom deserializer for an AssetBalance, since the streaming API returns an object in the format {"a":"symbol","f":"free","l":"locked"},
 * which is different than the format used in the REST API.
 */
public class AssetBalanceDeserializer extends JsonDeserializer<AssetBalance> {

  @Override
  public AssetBalance deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
    ObjectCodec oc = jp.getCodec();
    JsonNode node = oc.readTree(jp);
    final String asset = node.get("a").asText();
    final BigDecimal free = new BigDecimal(node.get("f").asText("0"));
    final BigDecimal locked = new BigDecimal(node.get("l").asText("0"));

    AssetBalance assetBalance = new AssetBalance();
    assetBalance.setAsset(asset);
    assetBalance.setFree(free);
    assetBalance.setLocked(locked);
    return assetBalance;
  }
}