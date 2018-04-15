package pl.com.bottega.qma.docflow;

import pl.com.bottega.qma.docflow.commands.*;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class Document {

  private final String number;
  private String title;
  private String content;
  private DocumentStatus status;
  private final Long creatorId;
  private Long editorId, verifierId, publisherId, archiverId;
  private Set<String> publishedForDepartments = new HashSet<>();

  public Document(String number, CreateDocumentCommand createDocumentCommand) {
    this.number = number;
    status = DocumentStatus.DRAFT;
    title = "";
    content = "";
    creatorId = createDocumentCommand.creatorId;
  }

  public void edit(EditDocumentCommand editDocumentCommand) {
    checkState(status == DocumentStatus.DRAFT || status == DocumentStatus.VERIFIED,
        "only draft and verified documents can be edited");
    status = DocumentStatus.DRAFT;
    editDocumentCommand.title.ifPresent(title -> this.title = title);
    editDocumentCommand.content.ifPresent(content -> this.content = content);
    editorId = editDocumentCommand.editorId;
  }

  public void verify(VerifyDocumentCommand verifyDocumentCommand) {
    checkState(status == DocumentStatus.DRAFT,
        "only draft documents can be verified");
    checkArgument(!creatorId.equals(verifyDocumentCommand.verifierId), "document creator can't verify it");
    checkArgument(!editorId.equals(verifyDocumentCommand.verifierId), "document editor can't verify it");
    status = DocumentStatus.VERIFIED;
    verifierId = verifyDocumentCommand.verifierId;
  }

  public void publish(PublishDocumentCommand publishDocumentCommand) {
    checkState(status == DocumentStatus.VERIFIED || status == DocumentStatus.PUBLISHED,
        "only verified and published documents can be published");
    status = DocumentStatus.PUBLISHED;
    publisherId = publishDocumentCommand.publisherId;
    publishedForDepartments.addAll(publishDocumentCommand.departmentCodes);
  }

  public void archive(ArchiveDocumentCommand archiveDocumentCommand) {
    status = DocumentStatus.ARCHIVED;
    archiverId = archiveDocumentCommand.archiverId;
  }

  public String number() {
    return number;
  }

  public Long creatorId() {
    return creatorId;
  }

  public String title() {
    return title;
  }

  public String content() {
    return content;
  }

  public Long editorId() {
    return editorId;
  }

  public DocumentStatus status() {
    return status;
  }

  public Long verifierId() {
    return verifierId;
  }

  public Long publisherId() {
    return publisherId;
  }

  public Set<String> publishedFor() {
    return publishedForDepartments;
  }

  public Long archiverId() {
    return archiverId;
  }
}
