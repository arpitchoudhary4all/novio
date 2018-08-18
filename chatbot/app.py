from flask import Flask, render_template, request
from chatterbot import ChatBot
from chatterbot.trainers import ChatterBotCorpusTrainer
from chatterbot.trainers import ListTrainer

app = Flask(__name__)

english_bot = ChatBot("Chatterbot", storage_adapter="chatterbot.storage.SQLStorageAdapter")
english_bot.set_trainer(ChatterBotCorpusTrainer)
english_bot.set_trainer(ListTrainer)
#english_bot.train("chatterbot.corpus.english")
#english_bot.train("bot.yaml")
english_bot.train( ["I am not feeling well",
      "I am Here to help u. feel free to share with me",
      ])
english_bot.train([
        "I am not in a good modd today",
        "I m there to support you. you can share everything with me.tell me what happend"
      ])
english_bot.train([
        "I m quite depressed?","Shall I play music for u  or u want some good music suggestions?",
        "I would go for bollywood?","If we go for some soothing taste then we can play Buddhu Sa Mann or if we go some romantic song then dil di yan galla or if we go for sad song then ek raat is best"
      ])
english_bot.train([
        "I m happy today I achieved my goal","Ohh Wow!That's so good to hear, Shall we have a party tonight?",
        "Yeah sure we can have a party?","Okay, We will meet at 8:00pm and go for a dance party"
      ])
english_bot.train([
        "I m happy today I achieved my goal","Ohh Wow!That's so good to hear, Shall we have a party tonight?",
        "Yeah sure we can have a party?","Okay, We will meet at 8:00pm and go for a dance party"
      ])
      


@app.route("/")
def home():
    return render_template("index.html")

@app.route("/get")
def get_bot_response():
    userText = request.args.get('msg')
    return str(english_bot.get_response(userText))


if __name__ == "__main__":
    app.run(host='192.168.6.251',port='8008')
