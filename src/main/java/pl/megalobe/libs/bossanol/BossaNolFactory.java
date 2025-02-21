package pl.megalobe.libs.bossanol;

public class BossaNolFactory {
  public static BossaNolSyncChannel createAsyncChannel(BossaNolConfig config) {
    return new BossaNolSyncChannelImpl(config);
  }
}
