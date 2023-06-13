//import java.net.InetSocketAddress;
//import java.util.Date;
//
//public class TestAssignmentRefactorProcessMethod {
//
//    // I actually think that it's not only fine, but straight up better to use "magical strings" (not magical numbers
//    // though)
//    // where they're self-describing, like in templates for log messages.
//    private static final String LOG_MESSAGE_TEMPLATE = "send message : {}";
//
//
//    void processOriginal(ChannelHandlerContext channelHandlerContext) {
//        InetSocketAddress inetSocketAddress = new InetSocketAddress(getIpAddress(), getUdpPort());
//
//        for (Command command : getAllCommands()) {
//            if (command.getCommandType() == CommandType.REBOOT_CHANNEL) {
//                if (!command.isAttemptsNumberExhausted()) {
//                    if (command.isTimeToSend()) {
//                        sendCommandToContext(channelHandlerContext, inetSocketAddress, command.getCommandText());
//
//                        try {
//                            AdminController.getInstance()
//                                    .processUssdMessage(new DblIncomeUssdMessage(
//                                            inetSocketAddress,
//                                            EnumGoip.getByModel(getGoipModel()),
//                                            command.getCommandText()
//                                    ), false);
//                        } catch (Exception ignored) {
//                        }
//
//                        command.setSendDate(new Date());
//
//                        logger.write("send message : {}", command.getCommandText());
//
//                        command.incSendCounter();
//                    }
//                } else {
//                    deleteCommand(command.getCommandType());
//                }
//            } else {
//                if (!currentCommand.isAttemptsNumberExhausted()) {
//                    sendCommandToContext(channelHandlerContext, inetSocketAddress, command.getCommandText());
//
//
//                    try {
//                        AdminController.getInstance()
//                                .processUssdMessage(new DblIncomeUssdMessage(
//                                        inetSocketAddress,
//                                        EnumGoip.getByModel(getGoipModel()),
//                                        command.getCommandText()
//                                ), false);
//                    } catch (Exception ignored) {
//                    }
//
//                    logger.write("send message : {}", command.getCommandText());
//
//
//                    command.setSendDate(new Date());
//                    command.incSendCounter();
//                } else {
//                    CommandType typeToRemove = command.getCommandType();
//                    deleteCommand(typeToRemove);
//                }
//            }
//        }
//
//        sendKeepAliveOkAndFlush(channelHandlerContext);
//    }
//
//    void processRefactored(ChannelHandlerContext channelHandlerContext) {
//        // Seems like it's always initialized with same parameters,
//        // I'd move inetSocketAddress to the class level fields, bc opening it each time must be expensive
//        InetSocketAddress inetSocketAddress = new InetSocketAddress(getIpAddress(), getUdpPort());
//
//        for (Command command : getAllCommands()) {
//            if (command.isAttemptsNumberExhausted()) {
//                deleteCommand(command.getCommandType());
//                continue;
//            }
//
//            if (command.getCommandType() == CommandType.REBOOT_CHANNEL && !command.isTimeToSend()) {
//                continue;
//            }
//
//            sendCommandToContext(channelHandlerContext, inetSocketAddress, command.getCommandText());
//            sendMessage(inetSocketAddress, command.getCommandText());
//
//            // I suppose that logging level is defined somewhere for this logger,
//            // but I'd use something with explicit logging level
//            // like logger.info or logger.write(LogLevel.INFO, message);
//            logger.write(LOG_MESSAGE_TEMPLATE, command.getCommandText());
//
//            // the old Date/Calendar are deprecated, I'd use classes from java.time + Instance,
//            // maybe except cases where we have to use some legacy stuff
//            command.setSendDate(new Date());
//            command.incSendCounter();
//        }
//
//        sendKeepAliveOkAndFlush(channelHandlerContext);
//    }
//
//    // IMHO in this exact case streams make it harder to read code and I wouldn't use it on actual project,
//    // so I made this just as an example of how it could've been done with streams
//    void processStream(ChannelHandlerContext channelHandlerContext) {
//        InetSocketAddress inetSocketAddress = new InetSocketAddress(getIpAddress(), getUdpPort());
//
//        var commands = getAllCommands();
//
//        // could be done in a single stream, like peek -> delete -> send null down the chain -> filter non-nulls
//        // but that'd be ugly
//        commands.stream()
//                .filter(c -> c.isAttemptsNumberExhausted())
//                .map(Command::getCommandType)
//                .forEach(this::deleteCommand);
//
//        commands.stream()
//                .filter(command -> !command.isAttemptsNumberExhausted())
//                .filter(command ->
//                        command.getCommandType() == CommandType.REBOOT_CHANNEL && command.isTimeToSend()
//                                || command.getCommandType() != CommandType.REBOOT_CHANNEL)
//                .forEach(this::processCommand);
//
//        sendKeepAliveOkAndFlush(channelHandlerContext);
//    }
//
//    // I've moved this logic into separate method, to make stream chain look better,
//    // but I think that in the original method, it's better to leave logic inside the for loop,
//    // bc the logic is coherent, and you don't have jump anywhere
//    private void processCommand(Command command) {
//        sendCommandToContext(channelHandlerContext, inetSocketAddress, command.getCommandText());
//        sendMessage(inetSocketAddress, command.getCommandText());
//
//        loggerASDF.write(LOG_MESSAGE_TEMPLATE, command.getCommandText());
//
//        command.setSendDate(new Date());
//        command.incSendCounter();
//    }
//
//    private void sendMessage(InetSocketAddress inetSocketAddress, String text) {
//        try {
//            AdminController.getInstance()
//                    .processUssdMessage(new DblIncomeUssdMessage(
//                            inetSocketAddress,
//                            EnumGoip.getByModel(getGoipModel()),
//                            text
//                    ), false);
//        } catch (Exception ignored) {
//        }
//    }
//}
