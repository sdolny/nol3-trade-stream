package pl.megalobe.libs.bossanol.fixml;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

import static pl.megalobe.libs.bossanol.fixml.FixmlConstants.*;

@Data
@XmlRootElement(name = "FIXML", namespace = "")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({FixmlUserRequest.class})
public class FixmlMessage<T> {
  @XmlAttribute(name = "v")
  private String version;

  @XmlAttribute(name = "r")
  private String releaseDate;

  @XmlAttribute(name = "s")
  private String schemaDate;

  @XmlAnyElement(lax = true)
  private T value;

  public static <T> FixmlMessage createFixmlMessage(T request) {
    FixmlMessage<T> fixml = new FixmlMessage<T>();
    fixml.setVersion(FIXML_VERSION);
    fixml.setReleaseDate(FIXML_RELEASE_DATE);
    fixml.setSchemaDate(FIXML_SCHEMA_DATE);
    fixml.setValue(request);

    return fixml;
  }
}
