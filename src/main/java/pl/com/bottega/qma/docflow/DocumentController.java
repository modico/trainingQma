package pl.com.bottega.qma.docflow;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.qma.core.CommandGateway;
import pl.com.bottega.qma.docflow.commands.CreateDocumentCommand;
import pl.com.bottega.qma.docflow.commands.EditDocumentCommand;

@RestController
public class DocumentController {
    private final CommandGateway commandGateway;

    public DocumentController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/documents")
    public String create(@RequestBody CreateDocumentCommand command) {
        return commandGateway.execute(command);
    }

    @PutMapping("/documents/{number}")
    public void edit(@PathVariable String number, @RequestBody EditDocumentCommand command) {
        command.documentNumber = number;
        commandGateway.execute(command);
    }
}
