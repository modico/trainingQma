package pl.com.bottega.qma.docflow;

import org.junit.jupiter.api.Test;
import pl.com.bottega.qma.docflow.commands.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class DocumentTest {

  private final String number = "test";
  private final Long employeeId = 1L;
  private final Long managerId = 2L;
  private final String newTitle = "new title";
  private final String newContent = "new content";
  private final Long uberManagerId = 3L;
  private Collection<String> departments = List.of("DEP1", "DEP2", "DEP3");
  private Collection<String> otherDepartments  = List.of("DEP4", "DEP5");

  @Test
  public void createsDocument() {
    var document = draftDocument();

    assertThat(document.number()).isEqualTo(number);
    assertThat(document.creatorId()).isEqualTo(employeeId);
    assertThat(document.title()).isEmpty();
    assertThat(document.content()).isEmpty();
    assertThat(document.status()).isEqualTo(DocumentStatus.DRAFT);
  }

  @Test
  public void updatesDocumentTitle() {
    var document = draftDocument();

    editDocument(document, Optional.of(newTitle), Optional.empty());

    assertThat(document.title()).isEqualTo(newTitle);
    assertThat(document.editorId()).isEqualTo(employeeId);
  }

  @Test
  public void updatesDocumentTitleAndContent() {
    var document = draftDocument();

    editDocument(document, Optional.of(newTitle), Optional.of(newContent));

    assertThat(document.title()).isEqualTo(newTitle);
    assertThat(document.content()).isEqualTo(newContent);
  }

  @Test
  public void verifiesDocument() {
    Document document = verifiedDocument();

    assertThat(document.status()).isEqualTo(DocumentStatus.VERIFIED);
    assertThat(document.verifierId()).isEqualTo(managerId);
  }

  @Test
  public void publishesDocument() {
    Document document = verifiedDocument();

    publishDocument(document);

    assertThat(document.status()).isEqualTo(DocumentStatus.PUBLISHED);
    assertThat(document.publisherId()).isEqualTo(uberManagerId);
    assertThat(document.publishedFor()).containsAll(departments);
  }

  @Test
  public void pusblishesDocumentForMoreDepartments() {
    Document document = publishedDocument();

    publishDocument(document, otherDepartments);

    assertThat(document.publishedFor()).containsAll(otherDepartments);
    assertThat(document.publishedFor()).containsAll(departments);
  }

  @Test
  public void documentCreatorCantVerifyIt() {
    var document = draftDocument();
    editDocument(document, Optional.of(newTitle), Optional.of(newContent));

    assertThatThrownBy(() -> verifyDocument(document, employeeId)).
        isInstanceOf(IllegalArgumentException.class).
        hasMessage("document creator can't verify it");
  }

  @Test
  public void documentEditorCantVerifyIt() {
    var document = draftDocument();
    editDocument(document, Optional.of(newTitle), Optional.of(newContent), managerId);

    assertThatThrownBy(() -> verifyDocument(document)).
        isInstanceOf(IllegalArgumentException.class).
        hasMessage("document editor can't verify it");
  }

  @Test
  public void documentEditionChangesStateBackToDraft() {
    Document document = verifiedDocument();

    editDocument(document, Optional.of("other title"), Optional.empty());

    assertThat(document.status()).isEqualTo(DocumentStatus.DRAFT);
  }

  @Test
  public void cantPublishDraftDocument() {
    var document = draftDocument();

    assertThatThrownBy(() -> publishDocument(document)).
        isInstanceOf(IllegalStateException.class).
        hasMessage("only verified and published documents can be published");
  }

  @Test
  public void cantEditPublishedDocument() {
    Document document = publishedDocument();

    assertThatThrownBy(() -> editDocument(document, Optional.of("other"), Optional.empty())).
        isInstanceOf(IllegalStateException.class).
        hasMessage("only draft and verified documents can be edited");
  }

  @Test
  public void cantVerifyPublishedDocument() {
    Document document = publishedDocument();

    assertThatThrownBy(() -> verifyDocument(document)).
        isInstanceOf(IllegalStateException.class).
        hasMessage("only draft documents can be verified");
  }

  @Test
  public void archivesDocumentsInAnyState() {
    var docs = List.of(draftDocument(), verifiedDocument(), publishedDocument());

    ArchiveDocumentCommand archiveDocumentCommand = new ArchiveDocumentCommand();
    archiveDocumentCommand.archiverId = uberManagerId;
    docs.forEach(document -> document.archive(archiveDocumentCommand));

    docs.forEach(document -> {
      assertThat(document.status()).isEqualTo(DocumentStatus.ARCHIVED);
      assertThat(document.archiverId()).isEqualTo(uberManagerId);
    });
  }

  private Document draftDocument() {
    var cmd = new CreateDocumentCommand();
    cmd.creatorId = employeeId;

    return new Document(number, cmd);
  }

  private void editDocument(Document document, Optional<String> newTitle, Optional<String> newContent) {
    editDocument(document, newTitle, newContent, employeeId);
  }

  private void editDocument(Document document, Optional<String> newTitle, Optional<String> newContent, Long editorId) {
    var editDocumentCommand = new EditDocumentCommand();
    editDocumentCommand.documentNumber = document.number();
    editDocumentCommand.editorId = editorId;
    editDocumentCommand.title = newTitle;
    editDocumentCommand.content = newContent;
    document.edit(editDocumentCommand);
  }

  private void verifyDocument(Document document, Long verifierId) {
    var verifyDocumentCommand = new VerifyDocumentCommand();
    verifyDocumentCommand.verifierId = verifierId;
    verifyDocumentCommand.documentNumber = document.number();
    document.verify(verifyDocumentCommand);
  }

  private void verifyDocument(Document document) {
    verifyDocument(document, managerId);
  }

  private void publishDocument(Document document) {
    publishDocument(document, departments);
  }

  private void publishDocument(Document document, Collection<String> departments) {
    var publishDocumentCommand = new PublishDocumentCommand();
    publishDocumentCommand.publisherId = uberManagerId;
    publishDocumentCommand.documentNumber = document.number();
    publishDocumentCommand.departmentCodes.addAll(departments);

    document.publish(publishDocumentCommand);
  }

  private Document verifiedDocument() {
    var document = draftDocument();
    editDocument(document, Optional.of(newTitle), Optional.of(newContent));
    verifyDocument(document);
    return document;
  }


  private Document publishedDocument() {
    var document = verifiedDocument();
    publishDocument(document);
    return document;
  }
}
