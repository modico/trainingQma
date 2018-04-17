package pl.com.bottega.qma.docflow;

import pl.com.bottega.qma.core.events.EventPublisher;
import pl.com.bottega.qma.docflow.commands.*;
import pl.com.bottega.qma.docflow.events.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;

public class Document {

  private final List<DocumentEvent> events = new ArrayList<>();
  private final EventPublisher eventPublisher;

  public Document(String number, CreateDocumentCommand createDocumentCommand, EventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
    events.add(new DocumentCreated(number, createDocumentCommand.creatorId, LocalDateTime.now()));
  }

  public void edit(EditDocumentCommand editDocumentCommand) {
    status().checkOperationPermited(DocumentEdited.class);
    events.add(new DocumentEdited(number(), editDocumentCommand.editorId,
        LocalDateTime.now(), editDocumentCommand.title, editDocumentCommand.content));
  }

  public void verify(VerifyDocumentCommand verifyDocumentCommand) {
    status().checkOperationPermited(DocumentVerified.class);
    checkDocumentOperationt(!creatorId().equals(verifyDocumentCommand.verifierId), "document creator can't verify it");
    checkDocumentOperationt(!editorId().equals(verifyDocumentCommand.verifierId), "document editor can't verify it");
    events.add(new DocumentVerified(number(), verifyDocumentCommand.verifierId, LocalDateTime.now()));
  }

  private void checkDocumentOperationt(boolean condition, String errorMsg) {
    if (!condition) {
      throw new IllegalDocumentOperation(errorMsg);
    }
  }

  public void publish(PublishDocumentCommand publishDocumentCommand) {
    status().checkOperationPermited(DocumentPublished.class);
    var event = new DocumentPublished(number(), publishDocumentCommand.publisherId, LocalDateTime.now(),
        publishDocumentCommand.departmentCodes);
    events.add(event);
    eventPublisher.publish(event);
  }

  public void archive(ArchiveDocumentCommand archiveDocumentCommand) {
    events.add(new DocumentArchived(number(), archiveDocumentCommand.archiverId, LocalDateTime.now()));
  }

  public String number() {
    return events.get(0).number;
  }

  public Long creatorId() {
    return events.get(0).employeeId;
  }

  public String title() {
    List<DocumentEdited> titleEdits = documentEvents(DocumentEdited.class).
        filter(documentEdited -> documentEdited.title.isPresent()).collect(Collectors.toList());
    if (titleEdits.size() == 0) {
      return "";
    }
    return titleEdits.get(titleEdits.size() - 1).title.get();
  }


  private <EventT extends DocumentEvent> Stream<EventT> documentEvents(Class<EventT> klass) {
    return events.stream().
        filter(documentEvent -> documentEvent.getClass().equals(klass)).
        map(documentEvent -> klass.cast(documentEvent));
  }

  public String content() {
    List<DocumentEdited> contentEdits = documentEvents(DocumentEdited.class).
        filter(documentEdited -> documentEdited.content.isPresent()).
        collect(Collectors.toList());
    if (contentEdits.size() == 0) {
      return "";
    }
    return contentEdits.get(contentEdits.size() - 1).content.get();
  }

  public Long editorId() {
    return lastEmployee(DocumentEdited.class);
  }

  private <EventT extends DocumentEvent> Long lastEmployee(Class<EventT> klass) {
    EventT lastEvent = lastEvent(klass);
    if (lastEvent == null) {
      return null;
    }
    return lastEvent.employeeId;
  }

  private <EventT extends DocumentEvent> EventT lastEvent(Class<EventT> klass) {
    return documentEvents(klass).reduce((first, second) -> second).orElse(null);
  }

  public DocumentStatus status() {
    return events.get(events.size() - 1).targetStatus();
  }

  public Long verifierId() {
    return lastEmployee(DocumentVerified.class);
  }

  public Long publisherId() {
    return lastEmployee(DocumentPublished.class);
  }

  public Set<String> publishedFor() {
    return documentEvents(DocumentPublished.class).flatMap(documentPublished -> documentPublished.departments.stream()).collect(Collectors.toSet());
  }

  public Long archiverId() {
    return lastEmployee(DocumentArchived.class);
  }
}
