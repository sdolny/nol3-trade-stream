package pl.megalobe.libs.bossanol;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;
import pl.megalobe.libs.bossanol.fixml.FixmlMessage;
import pl.megalobe.libs.bossanol.fixml.FixmlUserRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.megalobe.libs.bossanol.fixml.FixmlMessage.createFixmlMessage;

class FixmlSerdeTest {
  @Test
  void should_Serialize_And_Deserialize() throws JAXBException {
    // given
    String expectedXml = """
      <FIXML v="5.0" r="20080317" s="20080314"><UserReq UserReqID="13" UserReqTyp="1" Username="BOS" Password="BOS"/></FIXML>
      """.strip();
    FixmlMessage<?> givenMessage = prepareFixmlMessage();
    byte[] buffer = new byte[65536];
    FixmlSerde fixmlSerde = new FixmlSerde();

    // serialization
    int length = fixmlSerde.serialize(givenMessage, buffer);
    String messageAsXml = new String(buffer, 0, length);

    assertThat(messageAsXml).isEqualTo(expectedXml);

    // deserialization
    FixmlMessage<?> message = fixmlSerde.deserialize(buffer, length);
    assertThat(message).isEqualTo(givenMessage);
  }

  private FixmlMessage<?> prepareFixmlMessage() {
    FixmlUserRequest userRequest = new FixmlUserRequest();
    userRequest.setRequestId(13);
    userRequest.setRequestType(FixmlUserRequest.FixmlUserRequestType.LOGIN);
    userRequest.setUsername("BOS");
    userRequest.setPassword("BOS");

    return createFixmlMessage(userRequest);
  }
}
