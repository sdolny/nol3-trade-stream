package pl.megalobe.libs.bossanol.fixml;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlRootElement(name = "UserReq")
@XmlAccessorType(XmlAccessType.FIELD)
public class FixmlUserRequest implements FixmlRequest {
  @XmlAttribute(name = "UserReqID")
  private Integer requestId;

  @XmlAttribute(name = "UserReqTyp")
  private FixmlUserRequestType requestType;

  @XmlAttribute(name = "Username")
  private String username;

  @XmlAttribute(name = "Password")
  private String password;

  @XmlType
  @XmlEnum(Integer.class)
  public enum FixmlUserRequestType {
    @XmlEnumValue("1") LOGIN,
    @XmlEnumValue("2") LOGOUT,
    @XmlEnumValue("4") STATUS
  }
}
