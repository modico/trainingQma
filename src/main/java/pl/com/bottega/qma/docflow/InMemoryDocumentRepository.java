package pl.com.bottega.qma.docflow;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDocumentRepository implements DocumentRepository {

  private Map<String, Document> db = new HashMap<>();

  @Override
  public Document get(String number) {
    return db.get(number);
  }

  @Override
  public void put(Document document) {
    db.put(document.number(), document);
  }
}
