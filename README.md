# Whale-Welcome
Small twitch welcome bot for subscriptions

### Current Features:
* Custom message for resubs
* Custom message for first time subscriptions
* Independent toggle of first time and resub messages
* Easy configuration through config file

### Installation
* Grab the latest download from the [release](https://github.com/goldbattle/Whale-Welcome/releases) page.
* Extract the archive to a folder
* Edit the `config.json` with all information needed.
* For *windows* users, double click on the launch batch file
* For other OS run from terminal
* Example command: `java -jar Whale-Welcome.jar`
* You should see the console output, enjoy!

### Configuration
To configure this bot, edit the `config.json` file.  
The default config file looks something like [this](https://github.com/goldbattle/Whale-Welcome/blob/master/default-config.json).

### Configuration Breakdown
* *username* - Your twitch user name the bot will login as
* *password_oauth* - A generated oauth password. Get one from [here](http://twitchapps.com/tmi/).
* *stream_id* - The stream you want to connect to
* *enable_sub_normal* - A boolean value that toggles first time subscription messages
* *enable_sub_resub* - A boolean value that toggles re-subscription messages
* *chat_message_normal* - This field contains the formatting of the message that you want to send for first time subs. The `{username}` field can be used as a formatting element to insert the user that has just subscribed username into the message.
* *chat_message_resub* - This field contains the formatting of the message that you want to send for re-subs. The `{username}` field can be used as a formatting element to insert the user that has just subscribed username into the message. Also the `{months}` field can be used to insert the amount of months that the user has been subscribed.

### Screenshot

![Screenshot](/screenshot.png)