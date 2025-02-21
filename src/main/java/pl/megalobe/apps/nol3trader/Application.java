package pl.megalobe.apps.nol3trader;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pl.megalobe.libs.bossanol.fixml.FixmlMessage;
import pl.megalobe.libs.bossanol.fixml.FixmlUserRequest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static pl.megalobe.libs.bossanol.fixml.FixmlMessage.createFixmlMessage;

@Slf4j
public class Application {
  @SneakyThrows
  public static void main(String[] args) {
//    unmarshalTest();
    marshallerTest();

//    test();
  }

  @SneakyThrows
  public static void marshallerTest() {
    JAXBContext context = JAXBContext.newInstance(FixmlMessage.class);
    Marshaller marshaller = context.createMarshaller();
    marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

    FixmlUserRequest userRequest = new FixmlUserRequest();
    userRequest.setRequestType(FixmlUserRequest.FixmlUserRequestType.LOGIN);
    userRequest.setUsername("BOS");
    userRequest.setPassword("BOS");

    FixmlMessage fixmlMessage = createFixmlMessage(userRequest);

    StringWriter sw = new StringWriter();
    marshaller.marshal(fixmlMessage, sw);

    String content = sw.toString();
    log.info("{}", content);
  }

  @SneakyThrows
  public static void unmarshalTest() {
    String content = "<FIXML v=\"5.0\" r=\"20080317\" s=\"20080314\"><UserReq UserReqTyp=\"1\" Username=\"BOS\" Password=\"BOS\"/></FIXML>";
    JAXBContext context = JAXBContext.newInstance(FixmlMessage.class);
    Unmarshaller unmarshaller = context.createUnmarshaller();

    StringReader reader = new StringReader(content);
    Object result = unmarshaller.unmarshal(reader);

    log.info("Unmarshalled: {}", result);
  }

  @SneakyThrows
  public static void test() {
    byte[] buffer = new byte[65536];

    try (Socket socket = new Socket("localhost", 24444)) {
      DataOutputStream socketWriter = new DataOutputStream(socket.getOutputStream());
      DataInputStream socketReader = new DataInputStream(socket.getInputStream());

      String message = "<FIXML v=\"5.0\" r=\"20080317\" s=\"20080314\"><UserReq UserReqID=\"0\" UserReqTyp=\"1\" Username=\"BOS\" Password=\"BOS\"/></FIXML>";
      byte[] messageInBytes = message.getBytes(StandardCharsets.US_ASCII);

      socketWriter.write(intToByteArray(messageInBytes.length));
      socketWriter.write(messageInBytes);

      log.info("Waiting for response");

      socketReader.readFully(buffer, 0, 4);
      int length = byteArrayToInt(buffer);

      log.info("Total bytes needs to read: {}", length);

      socketReader.readFully(buffer, 0, length);
      String response = new String(buffer, StandardCharsets.US_ASCII);

      log.info("Received message: {}", response);
    }
  }

  public static int byteArrayToInt(byte[] bytes) {
    return (bytes[3] << 24) | ((bytes[2] & 0xFF) << 16) | ((bytes[1] & 0xFF) << 8) | (bytes[0] & 0xFF);
  }

  public static byte[] intToByteArray(int value) {
    return new byte[] {
      (byte)(value >>> 0),
      (byte)(value >>> 8),
      (byte)(value >>> 16),
      (byte)(value >>> 24)};
  }
}
