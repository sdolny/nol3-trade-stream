package pl.megalobe.libs.bossanol;

import pl.megalobe.libs.bossanol.fixml.FixmlMessage;
import pl.megalobe.libs.bossanol.fixml.FixmlRequest;
import pl.megalobe.libs.bossanol.fixml.FixmlResponse;

class BossaNolSyncChannelImpl implements BossaNolSyncChannel {

  BossaNolSyncChannelImpl(BossaNolConfig config) {

  }

  @Override
  public <T extends FixmlResponse> FixmlMessage<T> exchange(FixmlRequest request, Class<T> responseClazz) {
    return null;
  }
}
