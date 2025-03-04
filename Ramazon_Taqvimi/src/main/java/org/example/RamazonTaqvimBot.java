package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

public class RamazonTaqvimBot extends TelegramLongPollingBot {
    private final Map<Long, ProfileData> userRegionMap = new HashMap<>();
    private static final ArrayList<Long> users = new ArrayList<>();


    private final String[][] toshkentTimes = {
            {"05:40", "18:17"}, {"05:38", "18:18"}, {"05:37", "18:19"}, {"05:35", "18:20"}, {"05:33", "18:21"},
            {"05:32", "18:22"}, {"05:30", "18:24"}, {"05:29", "18:25"}, {"05:27", "18:26"}, {"05:25", "18:27"},
            {"05:24", "18:28"}, {"05:22", "18:29"}, {"05:20", "18:30"}, {"05:18", "18:32"}, {"05:17", "18:33"},
            {"05:15", "18:34"}, {"05:13", "18:35"}, {"05:11", "18:36"}, {"05:10", "18:37"}, {"05:08", "18:38"},
            {"05:06", "18:39"}, {"05:04", "18:40"}, {"05:02", "18:41"}, {"05:01", "18:42"}, {"04:59", "18:44"},
            {"04:57", "18:45"}, {"04:55", "18:46"}, {"04:53", "18:47"}, {"04:52", "18:48"}, {"04:50", "18:49"}
    };

    private final String[][] andijonTimes = {
            {"05:28", "18:05"}, {"05:26", "18:06"}, {"05:25", "18:07"}, {"05:23", "18:08"}, {"05:21", "18:09"},
            {"05:20", "18:10"}, {"05:18", "18:12"}, {"05:17", "18:13"}, {"05:15", "18:14"}, {"05:13", "18:15"},
            {"05:12", "18:16"}, {"05:10", "18:17"}, {"05:08", "18:18"}, {"05:06", "18:20"}, {"05:05", "18:21"},
            {"05:03", "18:22"}, {"05:01", "18:23"}, {"04:59", "18:24"}, {"04:58", "18:25"}, {"04:56", "18:26"},
            {"04:54", "18:27"}, {"04:52", "18:28"}, {"04:50", "18:29"}, {"04:49", "18:30"}, {"04:47", "18:32"},
            {"04:45", "18:33"}, {"04:43", "18:34"}, {"04:41", "18:35"}, {"04:40", "18:36"}, {"04:38", "18:37"}
    };

    private final String[][] namanganTimes = {
            {"05:30", "18:07"}, {"05:28", "18:08"}, {"05:27", "18:09"}, {"05:25", "18:10"}, {"05:23", "18:11"},
            {"05:22", "18:12"}, {"05:20", "18:14"}, {"05:19", "18:15"}, {"05:17", "18:16"}, {"05:15", "18:17"},
            {"05:14", "18:18"}, {"05:12", "18:19"}, {"05:10", "18:20"}, {"05:08", "18:22"}, {"05:07", "18:23"},
            {"05:05", "18:24"}, {"05:03", "18:25"}, {"05:01", "18:26"}, {"05:00", "18:27"}, {"04:58", "18:28"},
            {"04:56", "18:29"}, {"04:54", "18:30"}, {"04:52", "18:31"}, {"04:51", "18:32"}, {"04:49", "18:34"},
            {"04:47", "18:35"}, {"04:45", "18:36"}, {"04:43", "18:37"}, {"04:42", "18:38"}, {"04:40", "18:39"}
    };

    private final String[][] fargonaTimes = {
            {"05:30", "18:07"}, {"05:28", "18:08"}, {"05:27", "18:09"}, {"05:25", "18:10"}, {"05:23", "18:11"},
            {"05:22", "18:12"}, {"05:20", "18:14"}, {"05:19", "18:15"}, {"05:17", "18:16"}, {"05:15", "18:17"},
            {"05:14", "18:18"}, {"05:12", "18:19"}, {"05:10", "18:20"}, {"05:08", "18:22"}, {"05:07", "18:23"},
            {"05:05", "18:24"}, {"05:03", "18:25"}, {"05:01", "18:26"}, {"05:00", "18:27"}, {"04:58", "18:28"},
            {"04:56", "18:29"}, {"04:54", "18:30"}, {"04:52", "18:31"}, {"04:51", "18:32"}, {"04:49", "18:34"},
            {"04:47", "18:35"}, {"04:45", "18:36"}, {"04:43", "18:37"}, {"04:42", "18:38"}, {"04:40", "18:39"}
    };

    private final String[][] samarkandTimes = {
            {"05:49", "18:24"}, {"05:47", "18:26"}, {"05:46", "18:27"}, {"05:44", "18:28"}, {"05:42", "18:29"},
            {"05:41", "18:30"}, {"05:39", "18:32"}, {"05:38", "18:33"}, {"05:36", "18:34"}, {"05:34", "18:35"},
            {"05:33", "18:36"}, {"05:31", "18:37"}, {"05:29", "18:38"}, {"05:27", "18:40"}, {"05:26", "18:41"},
            {"05:24", "18:42"}, {"05:22", "18:43"}, {"05:20", "18:44"}, {"05:19", "18:45"}, {"05:17", "18:46"},
            {"05:15", "18:47"}, {"05:13", "18:48"}, {"05:11", "18:49"}, {"05:10", "18:50"}, {"05:08", "18:52"},
            {"05:06", "18:53"}, {"05:04", "18:54"}, {"05:02", "18:55"}, {"05:01", "18:56"}, {"04:59", "18:57"}
    };

    private final String[][] bukharaTimes = {
            {"05:59", "18:35"}, {"05:57", "18:37"}, {"05:56", "18:38"}, {"05:54", "18:39"}, {"05:52", "18:40"},
            {"05:51", "18:41"}, {"05:49", "18:42"}, {"05:48", "18:44"}, {"05:46", "18:45"}, {"05:45", "18:46"},
            {"05:44", "18:47"}, {"05:42", "18:48"}, {"05:40", "18:49"}, {"05:38", "18:50"}, {"05:37", "18:51"},
            {"05:35", "18:52"}, {"05:33", "18:54"}, {"05:31", "18:55"}, {"05:30", "18:56"}, {"05:30", "18:56"},
            {"05:28", "18:57"}, {"05:26", "18:58"}, {"05:24", "18:59"}, {"05:23", "19:00"}, {"05:21", "19:01"},
            {"05:19", "19:02"}, {"05:17", "19:03"}, {"05:15", "19:05"}, {"05:14", "19:06"}, {"05:12", "19:06"}
    };

    private final String[][] khorasmTimes = {
            {"06:16", "18:51"}, {"06:14", "18:53"}, {"06:13", "18:54"}, {"06:11", "18:55"}, {"06:09", "18:56"},
            {"06:08", "18:57"}, {"06:06", "18:59"}, {"06:05", "19:00"}, {"06:03", "19:01"}, {"06:01", "19:02"},
            {"06:00", "19:03"}, {"05:58", "19:04"}, {"05:56", "19:05"}, {"05:54", "19:07"}, {"05:53", "19:08"},
            {"05:51", "19:09"}, {"05:49", "19:10"}, {"05:47", "19:11"}, {"05:46", "19:12"}, {"05:44", "19:13"},
            {"05:42", "19:14"}, {"05:40", "19:15"}, {"05:38", "19:16"}, {"05:37", "19:17"}, {"05:35", "19:19"},
            {"05:33", "19:20"}, {"05:31", "19:21"}, {"05:29", "19:22"}, {"05:28", "19:23"}, {"05:26", "19:24"}
    };

    private final String[][] navoiTimes = {
            {"05:56", "18:31"}, {"05:54", "18:33"}, {"05:53", "18:34"}, {"05:51", "18:35"}, {"05:49", "18:36"},
            {"05:48", "18:37"}, {"05:46", "18:39"}, {"05:45", "18:40"}, {"05:43", "18:41"}, {"05:41", "18:42"},
            {"05:40", "18:43"}, {"05:38", "18:44"}, {"05:36", "18:45"}, {"05:34", "18:47"}, {"05:33", "18:48"},
            {"05:31", "18:49"}, {"05:29", "18:50"}, {"05:27", "18:51"}, {"05:26", "18:52"}, {"05:24", "18:53"},
            {"05:22", "18:54"}, {"05:20", "18:55"}, {"05:18", "18:56"}, {"05:17", "18:57"}, {"05:15", "18:59"},
            {"05:13", "19:00"}, {"05:11", "19:01"}, {"05:09", "19:02"}, {"05:08", "19:03"}, {"05:06", "19:04"}
    };

    private final String[][] kashkadaryoTimes = {
            {"05:54", "18:29"}, {"05:52", "18:31"}, {"05:51", "18:32"}, {"05:49", "18:33"}, {"05:47", "18:34"},
            {"05:46", "18:35"}, {"05:44", "18:37"}, {"05:43", "18:38"}, {"05:41", "18:39"}, {"05:39", "18:40"},
            {"05:38", "18:41"}, {"05:36", "18:42"}, {"05:34", "18:43"}, {"05:32", "18:45"}, {"05:31", "18:46"},
            {"05:29", "18:47"}, {"05:27", "18:48"}, {"05:25", "18:49"}, {"05:24", "18:50"}, {"05:22", "18:51"},
            {"05:20", "18:52"}, {"05:18", "18:53"}, {"05:16", "18:54"}, {"05:15", "18:55"}, {"05:13", "18:57"},
            {"05:11", "18:58"}, {"05:09", "18:59"}, {"05:07", "19:00"}, {"05:06", "19:01"}, {"05:04", "19:02"}
    };

    private final String[][] surkhandaryoTimes = {
            {"05:46", "18:21"}, {"05:44", "18:23"}, {"05:43", "18:24"}, {"05:41", "18:25"}, {"05:39", "18:26"},
            {"05:38", "18:27"}, {"05:36", "18:29"}, {"05:35", "18:30"}, {"05:33", "18:31"}, {"05:31", "18:32"},
            {"05:30", "18:33"}, {"05:28", "18:34"}, {"05:26", "18:35"}, {"05:24", "18:37"}, {"05:23", "18:38"},
            {"05:21", "18:39"}, {"05:19", "18:40"}, {"05:17", "18:41"}, {"05:16", "18:42"}, {"05:14", "18:43"},
            {"05:12", "18:44"}, {"05:10", "18:45"}, {"05:08", "18:46"}, {"05:07", "18:47"}, {"05:05", "18:49"},
            {"05:03", "18:50"}, {"05:01", "18:51"}, {"04:59", "18:52"}, {"04:58", "18:53"}, {"04:56", "18:54"}
    };

    private final String[][] djizzakhTimes = {
            {"05:46", "18:21"}, {"05:44", "18:23"}, {"05:43", "18:24"}, {"05:41", "18:25"}, {"05:39", "18:26"},
            {"05:38", "18:27"}, {"05:36", "18:29"}, {"05:35", "18:30"}, {"05:33", "18:31"}, {"05:31", "18:32"},
            {"05:30", "18:33"}, {"05:28", "18:34"}, {"05:26", "18:35"}, {"05:24", "18:37"}, {"05:23", "18:38"},
            {"05:21", "18:39"}, {"05:19", "18:40"}, {"05:17", "18:41"}, {"05:16", "18:42"}, {"05:14", "18:43"},
            {"05:12", "18:44"}, {"05:10", "18:45"}, {"05:08", "18:46"}, {"05:07", "18:47"}, {"05:05", "18:49"},
            {"05:03", "18:50"}, {"05:01", "18:51"}, {"04:59", "18:52"}, {"04:58", "18:53"}, {"04:56", "18:54"}
    };

    private final String[][] sirdaryaTimes = {
            {"05:42", "18:17"}, {"05:40", "18:19"}, {"05:39", "18:20"}, {"05:37", "18:21"}, {"05:35", "18:22"},
            {"05:34", "18:23"}, {"05:32", "18:25"}, {"05:31", "18:26"}, {"05:29", "18:27"}, {"05:27", "18:28"},
            {"05:26", "18:29"}, {"05:24", "18:30"}, {"05:22", "18:31"}, {"05:20", "18:33"}, {"05:19", "18:34"},
            {"05:17", "18:35"}, {"05:15", "18:36"}, {"05:13", "18:37"}, {"05:12", "18:38"}, {"05:10", "18:39"},
            {"05:08", "18:40"}, {"05:06", "18:41"}, {"05:04", "18:42"}, {"05:03", "18:43"}, {"05:01", "18:45"},
            {"04:59", "18:46"}, {"04:57", "18:47"}, {"04:55", "18:48"}, {"04:54", "18:49"}, {"04:52", "18:50"}
    };

    private final String[][] karakalpagistanTimes = {
            {"06:19", "18:54"}, {"06:17", "18:56"}, {"06:16", "18:57"}, {"06:14", "18:58"}, {"06:12", "18:59"},
            {"06:11", "19:00"}, {"06:09", "19:02"}, {"06:08", "19:03"}, {"06:06", "19:04"}, {"06:04", "19:05"},
            {"06:03", "19:06"}, {"06:01", "19:07"}, {"05:59", "19:08"}, {"05:57", "19:10"}, {"05:56", "19:11"},
            {"05:54", "19:12"}, {"05:52", "19:13"}, {"05:50", "19:14"}, {"05:49", "19:15"}, {"05:47", "19:16"},
            {"05:45", "19:17"}, {"05:43", "19:18"}, {"05:41", "19:19"}, {"05:40", "19:20"}, {"05:38", "19:22"},
            {"05:36", "19:23"}, {"05:34", "19:24"}, {"05:32", "19:25"}, {"05:31", "19:26"}, {"05:29", "19:27"}
    };

    private final String[][] toshkentTimesV = {
            {"05:37", "18:12"}, {"05:35", "18:14"}, {"05:34", "18:15"}, {"05:32", "18:16"}, {"05:30", "18:17"},
            {"05:29", "18:18"}, {"05:27", "18:20"}, {"05:26", "18:21"}, {"05:24", "18:22"}, {"05:22", "18:23"},
            {"05:21", "18:24"}, {"05:19", "18:25"}, {"05:17", "18:26"}, {"05:15", "18:28"}, {"05:14", "18:29"},
            {"05:12", "18:30"}, {"05:10", "18:31"}, {"05:08", "18:32"}, {"05:07", "18:33"}, {"05:05", "18:34"},
            {"05:03", "18:35"}, {"05:01", "18:36"}, {"04:59", "18:37"}, {"04:58", "18:38"}, {"04:56", "18:40"},
            {"04:54", "18:41"}, {"04:52", "18:42"}, {"04:50", "18:43"}, {"04:49", "18:44"}, {"04:47", "18:45"}
    };


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (!users.contains(chatId)) {
                users.add(chatId);
                SendMessage send = new SendMessage();
                send.setText("number of users:  " + users.size());
                send.setChatId(6290351864L);
                try {
                    execute(send);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (messageText.equals("/start")) {

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("Assalomu alaykum🖐.\nRamadan taqvimi botimizga xush kelibsiz😊");

                //sendTextMessage(6290351864L,chatId +" start bosdi"); // to admin
                KeyboardRow row_one = new KeyboardRow();
                KeyboardRow row_two = new KeyboardRow();
                KeyboardButton sbtn = new KeyboardButton("Saharlik duosi");
                KeyboardButton ibtn = new KeyboardButton("Iftorlik duosi");
                KeyboardButton ortga = new KeyboardButton("⬅\uFE0F Ortga");
                row_one.add(sbtn);
                row_one.add(ibtn);
                row_two.add(ortga);
                List<KeyboardRow> buttonList = new LinkedList<>();
                buttonList.add(row_one);
                buttonList.add(row_two);

                ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
                replyMarkup.setKeyboard(buttonList);
                replyMarkup.setResizeKeyboard(true);

                sendMessage.setReplyMarkup(replyMarkup);

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                sendTextMessage(chatId, "Qaysi viloyatning taqvimi kerak?", createRegionButtons());
            } else if (messageText.equals("Saharlik duosi")) {
                sendTextMessage(chatId, "\uD83C\uDF19 Saxarlik duosi:\n\n نَوَيْتُ أَنْ أَصُومَ صَوْمَ شَهْرِ رَمَضَانَ مِنَ الْفَجْرِ إِلَى الْمَغْرِبِ، خَالِصًا لِلَّهِ تَعَالَى. اللَّهُ أَكْبَرُ.ُ.ُ" +
                        "\n\nTarjimasi ->  Navaytu an asuma sovma shahri Ramazona minal fajri ilal mag‘ribi, xolisan lillahi ta’alaa. Allohu akbar." +
                        "\n\nMa'nosi: Allohim, men Sening roziliging uchun ro‘za tutdim, Senga iymon keltirdim, Senga tavakkul qildim va Sen ato etgan rizq bilan saxarlik qildim." +
                        "\n\n\n\uD83D\uDDD3 @Ramazon_taqvimi_2025_bot_bot");
            } else if (messageText.equals("Iftorlik duosi")) {
                sendTextMessage(chatId, "\uD83C\uDF05 Iftorlik duosi:\n\n" + "اللَّهُمَّ لَكَ صُمْتُ وَبِكَ آمَنْتُ وَعَلَيْكَ تَوَكَّلْتُ وَعَلَى رِزْقِكَ أَفْطَرْتُ، فَاغْفِرْ لِي ذُنُوبِي يَا غَفَّارُ مَا قَدَّمْتُ وَمَا أَخَّرْتُ." +
                        "\n\nTarjimasi ->  Allohumma laka sumtu va bika aamantu va ’alayka tavakkaltu va ’alaa rizqika aftortu, fag‘firliy zunubiy yaa G‘offaru maa qoddamtu va maa axxortu. " +
                        "\n\nMa'nosi: Allohim, men Sening roziliging uchun ro‘za tutdim, Senga iymon keltirdim, Senga tavakkul qildim va Sen ato etgan rizq bilan iftorlik qildim. Mening oldingi va keyingi gunohlarimni kechir." +
                        "\n\n\n\uD83D\uDDD3 @Ramazon_taqvimi_2025_bot_bot");
            } else if (messageText.equals("⬅️ Ortga")) {
                sendDeleteMessage(chatId, userRegionMap.get(chatId).getSelectedDateMessageId());
                sendTextMessage(chatId, "Qaysi viloyatning taqvimi kerak?", createRegionButtons());
            } else {
                sendTextMessage(chatId, "Bu botga faqat tugma orqali murojaat qilinadiku \uD83E\uDD26");
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
            if (!users.contains(chatId)) {
                users.add(chatId);
                SendMessage send = new SendMessage();
                send.setText("number of users:  " + users.size());
                send.setChatId(6290351864L);
                try {
                    execute(send);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            if (isRegion(callbackData)) {
                userRegionMap.put(chatId, new ProfileData(callbackData));
                sendEditMessage(chatId, "\uD83D\uDCCD " + callbackData + "  taqvimini tanladingiz 😊", messageId);
                sendTextMessage(chatId, "1-30 mart orasida sanani tanlang:", createDateButtons());
            } else if (callbackData.matches("\\d+-mart")) {
                int day = Integer.parseInt(callbackData.replace("-mart", "")) - 1;
                String userRegion = userRegionMap.get(chatId).getUserRegion();
                userRegionMap.get(chatId).setSelectedDateMessageId(messageId);

                int actualDay = day + 1;
                String weekdayName = getUzbekWeekdayName(actualDay);

                String[][] regionTimes = getTimesByRegion(userRegion);
                String saharlik = regionTimes[day][0];
                String iftorlik = regionTimes[day][1];

                String message = String.format("< %d-mart, %s > Ramadan Taqvimi\uD83D\uDD4B \n%sda Saxarlik \uD83C\uDF19     %s  \n%sda Iftorlik    \uD83C\uDF05     %s  " +
                                "\n\n\n\uD83D\uDDD3  @Ramazon_taqvimi_2025_bot_bot",
                        actualDay,
                        weekdayName,
                        userRegion,
                        saharlik,
                        userRegion,
                        iftorlik);

                sendTextMessage(chatId, message);
            }

        }
    }

    private InlineKeyboardMarkup createRegionButtons() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        String[] regions = {
                "Toshkent shahri", "Andijon viloyati", "Namangan viloyati", "Farg'ona viloyati",
                "Samarqand viloyati", "Buxoro viloyati", "Xorazm viloyati", "Navoiy viloyati",
                "Qashqadaryo viloyati", "Surxondaryo viloyati", "Jizzax viloyati", "Sirdaryo viloyati",
                "Qoraqalpog'iston Respublikasi", "Toshkent viloyati"
        };

        for (int i = 0; i < regions.length; i += 3) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int j = i; j < i + 3 && j < regions.length; j++) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(regions[j]);
                button.setCallbackData(regions[j]);
                row.add(button);
            }
            rows.add(row);
        }
        markup.setKeyboard(rows);
        return markup;
    }

    private InlineKeyboardMarkup createDuaButtons() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        InlineKeyboardButton saharlikButton = new InlineKeyboardButton();
        saharlikButton.setText("Saxarlik duosi");
        saharlikButton.setCallbackData("saharlik_duosi");

        InlineKeyboardButton iftorlikButton = new InlineKeyboardButton();
        iftorlikButton.setText("Iftorlik duosi");
        iftorlikButton.setCallbackData("iftorlik_duosi");

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(saharlikButton);
        row.add(iftorlikButton);

        rows.add(row);

        markup.setKeyboard(rows);
        return markup;
    }

    private InlineKeyboardMarkup createDateButtons() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (int i = 1; i <= 30; i += 5) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int j = i; j < i + 5 && j <= 30; j++) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(j + "-mart");
                button.setCallbackData(j + "-mart");
                row.add(button);
            }
            rows.add(row);
        }
        markup.setKeyboard(rows);
        return markup;
    }


    private boolean isRegion(String data) {
        return data.equals("Toshkent shahri") || data.equals("Andijon viloyati") || data.equals("Namangan viloyati") ||
                data.equals("Farg'ona viloyati") || data.equals("Samarqand viloyati") || data.equals("Buxoro viloyati") ||
                data.equals("Xorazm viloyati") || data.equals("Navoiy viloyati") || data.equals("Qashqadaryo viloyati") ||
                data.equals("Surxondaryo viloyati") || data.equals("Jizzax viloyati") || data.equals("Sirdaryo viloyati") ||
                data.equals("Qoraqalpog'iston Respublikasi") || data.equals("Toshkent viloyati");
    }


    private String[][] getTimesByRegion(String region) {
        switch (region) {
            case "Toshkent shahri":
                return toshkentTimes;
            case "Andijon viloyati":
                return andijonTimes;
            case "Namangan viloyati":
                return namanganTimes;
            case "Farg'ona viloyati":
                return fargonaTimes;
            case "Samarqand viloyati":
                return samarkandTimes;
            case "Buxoro viloyati":
                return bukharaTimes;
            case "Xorazm viloyati":
                return khorasmTimes;
            case "Navoiy viloyati":
                return navoiTimes;
            case "Qashqadaryo viloyati":
                return kashkadaryoTimes;
            case "Surxondaryo viloyati":
                return surkhandaryoTimes;
            case "Jizzax viloyati":
                return djizzakhTimes;
            case "Sirdaryo viloyati":
                return sirdaryaTimes;
            case "Qoraqalpog'iston Respublikasi":
                return karakalpagistanTimes;
            case "Toshkent viloyati":
                return toshkentTimesV;
            default:
                return toshkentTimes;
        }
    }


    private String getUzbekWeekdayName(int dayOfMarch) {
        java.time.LocalDate date = java.time.LocalDate.of(2025, 3, dayOfMarch);
        java.time.DayOfWeek dayOfWeek = date.getDayOfWeek();
        switch (dayOfWeek) {
            case MONDAY:
                return "Dushanba";
            case TUESDAY:
                return "Seshanba";
            case WEDNESDAY:
                return "Chorshanba";
            case THURSDAY:
                return "Payshanba";
            case FRIDAY:
                return "Juma";
            case SATURDAY:
                return "Shanba";
            case SUNDAY:
                return "Yakshanba";
            default:
                return "";
        }
    }


    private void sendTextMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendEditMessage(long chatId, String text, Integer oldMessageId) {
        EditMessageText message = new EditMessageText();
        message.setChatId(chatId);
        message.setText(text);
        message.setMessageId(oldMessageId);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendDeleteMessage(long chatId, Integer oldMessageId) {
        DeleteMessage message = new DeleteMessage();
        message.setChatId(chatId);
        message.setMessageId(oldMessageId);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private void sendTextMessage(long chatId, String text, InlineKeyboardMarkup keyboard) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setReplyMarkup(keyboard);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "Ramazon_taqvimi_2025_bot_bot";
    }

    @Override
    public String getBotToken() {
        return "7164667847:AAHqyTQlR1Icvfyp0yiY2ZgnyJbzCkvCEc0";
    }
}
