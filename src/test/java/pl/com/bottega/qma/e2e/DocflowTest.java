package pl.com.bottega.qma.e2e;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.com.bottega.qma.core.CommandGateway;
import pl.com.bottega.qma.docflow.Document;
import pl.com.bottega.qma.docflow.DocumentRepository;
import pl.com.bottega.qma.docflow.commands.CreateDocumentCommand;
import pl.com.bottega.qma.docflow.commands.EditDocumentCommand;
import pl.com.bottega.qma.docflow.commands.VerifyDocumentCommand;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DocflowTest {

    private String documentNumber;

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private DocumentRepository documentRepository;

    @BeforeEach
    public void createDocument() {
        CreateDocumentCommand createDocumentCommand = new CreateDocumentCommand();
        createDocumentCommand.creatorId = 1L;
        documentNumber = commandGateway.execute(createDocumentCommand);
    }

    @Test
    public void createsDocument() {
        Document document = documentRepository.get(documentNumber);
        assertThat(document).isNotNull();
        assertThat(document.number()).isEqualTo(documentNumber);
    }

    @Test
    public void editsDocument() {
        EditDocumentCommand editDocumentCommand = createEditDocumentCommand();

        commandGateway.execute(editDocumentCommand);

        Document document = documentRepository.get(documentNumber);
        assertThat(document).isNotNull();
        assertThat(document.title()).isEqualTo("test title");
        assertThat(document.content()).isEqualTo("test content");
    }

    private EditDocumentCommand createEditDocumentCommand() {
        EditDocumentCommand editDocumentCommand = new EditDocumentCommand();
        editDocumentCommand.editorId = 2L;
        editDocumentCommand.documentNumber = documentNumber;
        editDocumentCommand.title = Optional.of("test title");
        editDocumentCommand.content = Optional.of("test content");
        return editDocumentCommand;
    }

    @Test
    public void verifiesDocument() {
        VerifyDocumentCommand verifyDocumentCommand = new VerifyDocumentCommand();
        verifyDocumentCommand.verifierId = 3L;
        verifyDocumentCommand.documentNumber = documentNumber;

        commandGateway.execute(createEditDocumentCommand());
        commandGateway.execute(verifyDocumentCommand);

        Document document = documentRepository.get(documentNumber);
        assertThat(document).isNotNull();
        assertThat(document.verifierId()).isEqualTo(3L);
    }

}
