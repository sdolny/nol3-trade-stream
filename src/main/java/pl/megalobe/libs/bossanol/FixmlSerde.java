package pl.megalobe.libs.bossanol;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pl.megalobe.libs.bossanol.fixml.FixmlMessage;

import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
class FixmlSerde {
  private final Marshaller marshaller;
  private final Unmarshaller unmarshaller;

  @SneakyThrows
  FixmlSerde() {
    JAXBContext context = JAXBContext.newInstance(FixmlMessage.class);
    this.marshaller = context.createMarshaller();
    this.marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
    this.unmarshaller = context.createUnmarshaller();
  }

  int serialize(FixmlMessage<?> message, byte[] dst) throws JAXBException {
    ExternalBufferOutputStream os = new ExternalBufferOutputStream(dst);
    marshaller.marshal(message, os);

    return os.getLength();
  }

  FixmlMessage<?> deserialize(byte[] dst, int length) throws JAXBException {
    ExternalBufferInputStream is = new ExternalBufferInputStream(dst, length);
    return (FixmlMessage<?>) unmarshaller.unmarshal(is);
  }

  private static class ExternalBufferInputStream extends InputStream {
    private final byte[] buf;
    private final int length;
    private int pos;

    ExternalBufferInputStream(byte[] buf, int length) {
      this.buf = buf;
      this.length = length;
      this.pos = 0;
    }

    @Override
    public int read() {
      if (pos >= length)
        return -1;

      return buf[pos++] & 0xff;
    }
  }

  private static class ExternalBufferOutputStream extends OutputStream {
    private final byte[] buf;
    private int position;

    ExternalBufferOutputStream(byte[] buf) {
      this.buf = buf;
      this.position = 0;
    }

    @Override
    public void write(int b) {
      buf[position++] = (byte) b;
    }

    int getLength() {
      return position;
    }
  }
}
