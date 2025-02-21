package pl.megalobe.libs.bossanol.fixml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "UserReq")
@XmlAccessorType(XmlAccessType.FIELD)
public class FixmlUserResponse implements FixmlResponse {

}
