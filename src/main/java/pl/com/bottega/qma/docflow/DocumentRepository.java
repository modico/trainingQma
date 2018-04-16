package pl.com.bottega.qma.docflow;

public interface DocumentRepository {

  Document get(String number) throws DocumentNotFoundException;

  void put(Document document);

  class DocumentNotFoundException extends RuntimeException {

    public DocumentNotFoundException(String number) {
      super(String.format("Document with number %s not found", number));
    }

  }

}
