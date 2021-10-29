package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.PermissionException;
import seedu.address.commons.status.ExecutionStatus;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.EzFoodieParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyEzFoodie;
import seedu.address.model.member.Member;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final EzFoodieParser ezFoodieParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        ezFoodieParser = new EzFoodieParser(model);
    }

    /**
     * Constructs a {@code LogicManager} with the given {@code Model}, {@code Storage} and {@code ExecutionStatus}.
     */
    public LogicManager(Model model, Storage storage, ExecutionStatus executionStatus) {
        this.model = model;
        this.storage = storage;
        ezFoodieParser = new EzFoodieParser(model, executionStatus);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException, PermissionException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = ezFoodieParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveEzFoodie(model.getEzFoodie());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyEzFoodie getEzFoodie() {
        return model.getEzFoodie();
    }

    @Override
    public ObservableList<Member> getUpdatedMemberList() {
        return model.getUpdatedMemberList();
    }

    @Override
    public ObservableList<Member> getUpdatedMemberListForView () {
        return model.getUpdatedMemberListForView();
    }

    @Override
    public int getNumberOfMembers() {
        return model.getNumberOfMembers();
    }

    @Override
    public HashMap<String, Integer> getNumberOfMembersByTiers() {
        return model.getNumberOfMembersByTiers();
    }

    @Override
    public int getNumberOfTransactions() {
        return model.getNumberOfTransactions();
    }

    @Override
    public int getNumberOfTransactionsPastMonth() {
        return model.getNumberOfTransactionsPastMonth();
    }

    @Override
    public int getNumberOfTransactionsPastThreeMonth() {
        return model.getNumberOfTransactionsPastThreeMonth();
    }

    @Override
    public int getNumberOfTransactionsPastSixMonths() {
        return model.getNumberOfTransactionsPastSixMonth();
    }

    @Override
    public double getTotalAmountOfTransactions() {
        return model.getTotalAmountOfTransactions();
    }

    @Override
    public double getTotalAmountOfTransactionsPastMonth() {
        return model.getTotalAmountOfTransactionsPastMonth();
    }

    @Override
    public double getTotalAmountOfTransactionsPastThreeMonth() {
        return model.getTotalAmountOfTransactionsPastThreeMonth();
    }

    @Override
    public double getTotalAmountOfTransactionsPastSixMonth() {
        return model.getTotalAmountOfTransactionsPastSixMonth();
    }

    @Override
    public Path getEzFoodieFilePath() {
        return model.getEzFoodieFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
