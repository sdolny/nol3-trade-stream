package pl.megalobe.libs.bossanol;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BossaNolConfig {
  String host;
  Integer syncPort;
  Integer asyncPort;
}
