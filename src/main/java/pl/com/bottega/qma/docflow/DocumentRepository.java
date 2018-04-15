package pl.com.bottega.qma.docflow;

public interface DocumentRepository {

  Document get(String number);

  void put(Document document);

}
